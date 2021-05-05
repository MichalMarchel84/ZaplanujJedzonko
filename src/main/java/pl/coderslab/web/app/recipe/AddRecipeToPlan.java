package pl.coderslab.web.app.recipe;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDAO;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.DayName;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;
import pl.coderslab.model.RecipePlan;
import pl.coderslab.utils.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;


@WebServlet("/app/recipe/plan/add")
public class AddRecipeToPlan extends HttpServlet {

    private static final String ADD_RECIPE_TO_PLAN = "INSERT INTO recipe_plan(recipe_id, meal_name, display_order, day_name_id, plan_id) VALUES (?,?,?, ?, ?);";

    public void addrecipetoplan(int recipe_id, String meal_name, int display_order, int day_name_id, int plan_id) {

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(ADD_RECIPE_TO_PLAN, PreparedStatement.RETURN_GENERATED_KEYS)) {

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int id = (Integer) session.getAttribute("adminId");

        PlanDao planDao = new PlanDao();
        RecipeDAO recipeDAO = new RecipeDAO();
        DayNameDao dayNameDao = new DayNameDao();


        List<Plan> plan = planDao.getByAdminId(id);
        request.setAttribute("plan", plan);

        List<Recipe> recipe = recipeDAO.findAllByAdmin(id);
        request.setAttribute("recipe", recipe);

        List<DayName> dayName = dayNameDao.findAll();
        request.setAttribute("dayName", dayName);

        request.setAttribute("component", "/app/recipe/plan/add.jsp");

        getServletContext().getRequestDispatcher("/app/frame.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecipePlan entry = new RecipePlan();
        entry.setPlanId(Integer.parseInt(request.getParameter("plan")));
        entry.setRecipeId(Integer.parseInt(request.getParameter("recipe")));
        entry.setMealName(request.getParameter("mealname"));
        entry.setDayNameId(Integer.parseInt(request.getParameter("day")));
        entry.setDisplayOrder(Integer.parseInt(request.getParameter("displayorder")));

        (new RecipePlanDao()).create(entry);
        response.sendRedirect("/app/recipe/plan/add");
    }


}
