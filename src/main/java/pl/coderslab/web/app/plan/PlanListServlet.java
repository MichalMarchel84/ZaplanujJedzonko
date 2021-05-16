package pl.coderslab.web.app.plan;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;
import pl.coderslab.utils.DbUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "ListPlanServlet", value = "/app/plan/list")
public class PlanListServlet extends HttpServlet {

    private final PlanDao planDao = DbUtil.getPlanDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Plan> list = planDao.getByAdminId((Integer)session.getAttribute("adminId"));
        list.sort(Comparator.comparing(Plan :: getCreated).reversed());
        request.setAttribute("plans", list);
        request.setAttribute("component", "/app/plan/list.jsp");
        getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}