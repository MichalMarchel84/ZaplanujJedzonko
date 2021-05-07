package pl.coderslab.web.app.recipe;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDAO;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.DayName;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;
import pl.coderslab.model.RecipePlan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet("/app/recipe/plan/add")
public class AddRecipeToPlan extends HttpServlet {

    private final PlanDao planDao;
    private final RecipeDAO recipeDAO;
    private final RecipePlanDao recipePlanDao;

    public AddRecipeToPlan() throws NoSuchMethodException {
        planDao = new PlanDao();
        recipeDAO = new RecipeDAO();
        recipePlanDao = new RecipePlanDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int id = (Integer) session.getAttribute("adminId");

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

        recipePlanDao.create(entry);
        response.sendRedirect("/app/recipe/plan/add");
    }


}
