package pl.coderslab.web.app;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDAO;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/app/dashboard")
public class DashboardServlet extends HttpServlet {

    private final PlanDao planDao;
    private final RecipeDAO recipeDAO;

    public DashboardServlet() throws NoSuchMethodException {
        planDao = new PlanDao();
        recipeDAO = new RecipeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        int id = (Integer) session.getAttribute("adminId");
        Optional<Plan> lastPlan = planDao.getLastPlan(id);
        request.setAttribute("numberAddedPlans", planDao.numberOfPlansByAdminId(id));
        request.setAttribute("numberAddedRecipes", recipeDAO.numberOfRecipesByAdminId(id));
        if (!lastPlan.isPresent()) {
            request.setAttribute("errorMsg", "Na razie nie masz dodanych żadnych planów");
        } else {
            request.setAttribute("plan", lastPlan.get());
            request.setAttribute("details", lastPlan.get().getDetails());
        }
        request.setAttribute("component", "/app/dashboard.jsp");
        getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}