package pl.coderslab.web.app.plan;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "PlanDeleteServlet", value = "/app/plan/delete")
public class PlanDeleteServlet extends HttpServlet {

    private final PlanDao dao;

    public PlanDeleteServlet() throws NoSuchMethodException {
        dao = new PlanDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("confirm")==null) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.getById(id).ifPresent(plan -> request.setAttribute("message", plan.getName()));
            request.setAttribute("okAction", "/app/plan/delete?id=" + id + "&confirm=1");
            request.setAttribute("cancelAction", "/app/plan/list");
            request.setAttribute("component", "/app/question.jsp");
            getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);
        } else if (request.getParameter("confirm").equals("1")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.deletePlan(id);
            response.sendRedirect("/app/plan/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
