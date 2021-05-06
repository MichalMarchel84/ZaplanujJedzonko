package pl.coderslab.web.app.plan;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PlanDetailsServlet", value = "/app/plan/details")
public class PlanDetailsServlet extends HttpServlet {

    private final PlanDao planDao;

    public PlanDetailsServlet() throws NoSuchMethodException {
        planDao = new PlanDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        planDao.getById(Integer.parseInt(request.getParameter("id"))).ifPresent(plan -> {
            request.setAttribute("plan", plan);
            request.setAttribute("details", plan.getDetails());
        });
        request.setAttribute("component", "/app/plan/details.jsp");
        getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}