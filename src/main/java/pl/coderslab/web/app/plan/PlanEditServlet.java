package pl.coderslab.web.app.plan;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDAO;
import pl.coderslab.model.Plan;
import pl.coderslab.service.EditPlanService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "PlanEditServlet", value = "/app/plan/edit")
public class PlanEditServlet extends HttpServlet {

    private final PlanDao planDao;
    private final RecipeDAO recipeDAO;
    private final EditPlanService service;

    public PlanEditServlet() throws NoSuchMethodException {
        planDao = new PlanDao();
        recipeDAO = new RecipeDAO();
        service = new EditPlanService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        planDao.getById(Integer.parseInt(request.getParameter("id"))).ifPresent(plan -> {
            request.setAttribute("plan", plan);
            request.setAttribute("details", plan.getDetails());
            request.setAttribute("recipes", recipeDAO.findAllByAdmin((Integer) request.getSession().getAttribute("adminId")));
        });
        request.setAttribute("component", "/app/plan/edit.jsp");
        getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        planDao.getById(id).ifPresent(plan -> {
            plan.setName(request.getParameter("name"));
            plan.setDescription(request.getParameter("description"));
            service.updatePlanDetails(id, request.getParameterMap());
            planDao.updatePlan(plan);
        });
        response.sendRedirect("/app/plan/list");
    }
}
