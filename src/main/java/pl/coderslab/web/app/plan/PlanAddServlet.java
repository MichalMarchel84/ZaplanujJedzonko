package pl.coderslab.web.app.plan;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;
import pl.coderslab.service.AddPlanService;
import pl.coderslab.utils.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "addPlanServlet", value = "/app/plan/add")
public class PlanAddServlet extends HttpServlet {

    private final PlanDao planDao = DbUtil.getPlanDao();
    private final AddPlanService service = new AddPlanService();
    private final DayNameDao dayNameDao = DbUtil.getDayNameDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("days", dayNameDao.findAll());
        request.setAttribute("component", "/app/plan/add.jsp");
        getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Plan plan = new Plan();
        plan.setAdminId((Integer) request.getSession().getAttribute("adminId"));
        plan.setName(request.getParameter("name"));
        plan.setDescription(request.getParameter("description"));
        plan.setId(planDao.createPlan(plan));
        if(request.getParameter("useTemplate") != null){
            service.createSchedule(request.getParameterMap(), plan);
            response.sendRedirect("/app/plan/edit?id=" + plan.getId());
        }
        else response.sendRedirect("/app/plan/list");
    }
}