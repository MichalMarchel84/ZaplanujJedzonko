package pl.coderslab.web.app.plan;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "PlanEditServlet", value = "/app/plan/edit")
public class PlanEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Plan plan = (new PlanDao()).getById(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("plan", plan);
        request.setAttribute("component", "/app/plan/edit.jsp");
        getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Plan plan = (new PlanDao()).getById(Integer.parseInt(request.getParameter("id")));
        plan.setName(request.getParameter("name"));
        plan.setDescription(request.getParameter("description"));
        if((new PlanDao()).updatePlan(plan) > 0) response.sendRedirect("/app/plan/list");
        else response.getWriter().append("Something went wrong...");
    }
}
