package pl.coderslab.model;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Meal implements Comparable<Meal>{
    private int id;
    private int recipeId;
    private String recipeName;
    private String mealName;
    private int displayOrder;

    private Meal() {
    }

    public int getId() {
        return id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getMealName() {
        return mealName;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public static TreeMap<DayName, List<Meal>> getForPlan(int planId){
        Map<Integer, DayName> days = (new DayNameDao()).findAll().stream().collect(Collectors.toMap(DayName::getId, Function.identity()));
        String sql ="SELECT rp.id, rp.recipe_id, r.name, rp.meal_name, rp.display_order, rp.day_name_id\n" +
                "FROM recipe_plan rp JOIN recipe r ON (r.id = rp.recipe_id) \n" +
                "WHERE rp.plan_id = ?";
        TreeMap<DayName, List<Meal>> map = new TreeMap<>();

        try(Connection conn = DbUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, planId);
            ResultSet set = stmt.executeQuery();
            while (set.next()){
                Meal meal = new Meal();
                meal.id = set.getInt("id");
                meal.recipeId = set.getInt("recipe_id");
                meal.recipeName = set.getString("name");
                meal.mealName = set.getString("meal_name");
                meal.displayOrder = set.getInt("display_order");
                DayName day = days.get(set.getInt("day_name_id"));
                if(map.containsKey(day)) map.get(day).add(meal);
                else {
                    ArrayList<Meal> list = new ArrayList<>();
                    list.add(meal);
                    map.put(day, list);
                }
            }
            for(List<Meal> list : map.values()){
                Collections.sort(list);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return map;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Meal meal) {
        return this.displayOrder - meal.displayOrder;
    }
}
