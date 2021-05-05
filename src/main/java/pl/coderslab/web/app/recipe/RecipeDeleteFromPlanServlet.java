package pl.coderslab.web.app.recipe;

import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/recipe/deleteFromPlan")
public class RecipeDeleteFromPlanServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("confirm") == null) {

            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("message", req.getParameter("name"));
            String plan = req.getParameter("plan");
            req.setAttribute("okAction", "/app/recipe/deleteFromPlan?id=" + id + "&confirm=1&plan=" + plan);
            req.setAttribute("cancelAction", "/app/plan/details?id=" + plan);
            req.setAttribute("component", "/app/question.jsp");
            getServletContext().getRequestDispatcher("/app/frame.jsp").forward(req, resp);

        }else if (req.getParameter("confirm").equals("1")) {

            int id = Integer.parseInt(req.getParameter("id"));
            (new RecipePlanDao()).delete(id);
            resp.sendRedirect("/app/plan/details?id=" + req.getParameter("plan") );
        }
    }
}
