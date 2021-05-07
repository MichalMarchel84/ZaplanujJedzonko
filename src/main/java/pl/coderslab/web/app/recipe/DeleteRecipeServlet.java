package pl.coderslab.web.app.recipe;

import pl.coderslab.dao.RecipeDAO;
import pl.coderslab.utils.DbUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeleteRecipeServlet", value = "/app/recipe/delete")
public class DeleteRecipeServlet extends HttpServlet {

    private final RecipeDAO recipeDAO = DbUtil.getRecipeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("confirm")==null) {
            int id = Integer.parseInt(request.getParameter("id"));

            recipeDAO.read(id).ifPresent(recipe -> request.setAttribute("message", recipe.getName()));
            request.setAttribute("okAction", "/app/recipe/delete?id=" + id + "&confirm=1");
            request.setAttribute("cancelAction", "/app/recipe/list");
            request.setAttribute("component", "/app/question.jsp");
            getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);
        } else if (request.getParameter("confirm").equals("1")) {
            int id = Integer.parseInt(request.getParameter("id"));
            recipeDAO.delete(id);
            response.sendRedirect("/app/recipe/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
