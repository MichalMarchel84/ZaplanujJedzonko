package pl.coderslab.utils;

import com.mysql.cj.jdbc.MysqlDataSource;
import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDAO;
import pl.coderslab.model.Admin;
import pl.coderslab.model.DayName;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class DbContentGenerator {

    private static final String uri = "src/main/resources/mock";
    private List<Recipe> recipes = getRecipes();
    private List<Admin> admins;

    public void populate(int userLimit) throws NoSuchMethodException {
        DbUtil.setDataSource(getDataSource());
        admins = generateAdmins(userLimit);
        Random random = new Random();
        RecipeDAO dao = new RecipeDAO();
        List<DayName> days = (new DayNameDao()).findAll();
        for (Admin admin : admins) {
            System.out.println("Populating " + admin.getFirstName() + " " + admin.getLastName());
            HashMap<String, Recipe> map = new HashMap<>();
            for (int i = 0; i < (random.nextInt(15) + 5); i++) {
                Recipe recipe = recipes.get(random.nextInt(recipes.size()));
                recipe.setAdminId(admin.getId());
                if(!map.containsKey(recipe.getName())) map.put(recipe.getName(), recipe);
            }
            for (Map.Entry<String, Recipe> entry : map.entrySet()){
                dao.create(entry.getValue());
            }
            String[] meals = {"Śniadanie", "Obiad", "Kolacja"};
            Plan plan = new Plan();
            plan.setName("Test");
            plan.setDescription("Test desc");
            plan.setAdminId(admin.getId());
            plan.setId((new PlanDao()).createPlan(plan));
            List<Recipe> recList = dao.findAllByAdmin(admin.getId());
            for (DayName day : days){
                for (int i = 0; i < meals.length; i++) {
                    addRecipeToPlan(
                            recList.get(random.nextInt(recList.size())).getId(),
                            meals[i],
                            i,
                            day.getId(),
                            plan.getId());
                }
            }
        }
        System.out.println("Finished populating");
    }

    private List<Admin> generateAdmins(int limit) throws NoSuchMethodException {
        System.out.print("Generating admins");
        List<String[]> names = getUserNames();
        List<Admin> list = new ArrayList<>();
        AdminDao dao = new AdminDao();
        int counter = 0;
        for (String[] name : names) {
            counter++;
            if (counter > limit) break;
            Admin admin = new Admin();
            admin.setEmail(name[0].toLowerCase() + "." + name[1].toLowerCase() + "@gmail.com");
            Optional<Admin> checkAdmin = dao.findByEmail(admin.getEmail());
            if (checkAdmin.isPresent()) admin = checkAdmin.get();
            else {
                admin.setFirstName(name[0]);
                admin.setLastName(name[1]);
                admin.setPassword("1234");
                admin.setSuperadmin(0);
                admin.setEnable(1);
                admin.setId(dao.createAdmin(admin));
            }
            list.add(admin);
            System.out.print(".");
        }
        System.out.println("finished");
        return list;
    }

    private List<String[]> getUserNames() {
        List<String[]> res = null;
        try {
            List<String> list = Files.readAllLines(Paths.get(uri + "/userName"));
            res = list.stream()
                    .map(s -> s.replaceAll("[0-9]", ""))
                    .map(s -> s.trim())
                    .map(s -> s.split(" "))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    private List<Recipe> getRecipes() {
        List<Recipe> list = new ArrayList<>();
        try (Scanner scan = new Scanner(new File(uri + "/recipes"))) {
            if (scan.hasNextLine()) scan.nextLine();
            while (scan.hasNextLine()) {
                if (scan.nextLine().equals("#recipe name:")) {
                    Recipe recipe = new Recipe();
                    String val = "";
                    while (scan.hasNextLine()) {
                        String line = scan.nextLine();
                        if (line.equals("#description:")) break;
                        val += line + "\n";
                    }
                    recipe.setName(val);
                    val = "";
                    while (scan.hasNextLine()) {
                        String line = scan.nextLine();
                        if (line.equals("#ingredients:")) break;
                        val += line + "\n";
                    }
                    recipe.setDescription(val);
                    val = "";
                    while (scan.hasNextLine()) {
                        String line = scan.nextLine();
                        if (line.equals("#preparation:")) break;
                        val += line + "\n";
                    }
                    recipe.setIngredients(val);
                    val = "";
                    while (scan.hasNextLine()) {
                        String line = scan.nextLine();
                        if (line.equals("#end")) break;
                        val += line + "\n";
                    }
                    recipe.setPreparation(val);
                    recipe.setPreparationTime((int) (Math.random() * 120 + 30));
                    list.add(recipe);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addRecipeToPlan(int recipe_id, String meal_name, int display_order, int day_name_id, int plan_id) {

        String sql = "INSERT INTO recipe_plan(recipe_id, meal_name, display_order, day_name_id, plan_id) VALUES (?,?,?, ?, ?);";
        try (PreparedStatement insertStm = DbUtil.getConnection().prepareStatement(sql)) {

            insertStm.setInt(1, recipe_id);
            insertStm.setString(2, meal_name);
            insertStm.setInt(3, display_order);
            insertStm.setInt(4, day_name_id);
            insertStm.setInt(5, plan_id);

            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private DataSource getDataSource() {
        MysqlDataSource source = new MysqlDataSource();
        source.setUrl("jdbc:mysql://localhost:3306/scrumlab");
        source.setUser("root");
        source.setPassword("coderslab");
        try {
            source.setConnectionAttributes("useUnicode=yes;characterEncoding=utf8;useSSL=false");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return source;
    }

    public static void main(String[] args) throws NoSuchMethodException {
        DbContentGenerator gen = new DbContentGenerator();
        gen.populate(10);
    }
}
