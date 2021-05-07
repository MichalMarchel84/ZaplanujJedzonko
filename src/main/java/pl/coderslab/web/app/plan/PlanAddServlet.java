package pl.coderslab.web.app.plan;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "addPlanServlet", value = "/app/plan/add")
public class PlanAddServlet extends HttpServlet {

    private final PlanDao planDao;

    public PlanAddServlet() throws NoSuchMethodException {
        planDao = new PlanDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("days", (new DayNameDao()).findAll());
        request.setAttribute("component", "/app/plan/add.jsp");
        getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Plan plan = new Plan();
        plan.setAdminId((Integer) request.getSession().getAttribute("adminId"));
        plan.setName(request.getParameter("name"));
        plan.setDescription(request.getParameter("description"));
        if(planDao.createPlan(plan) > 0) response.sendRedirect("/app/plan/list");
        else response.getWriter().append("Something went wrong...");
    }
}