package pl.coderslab.web.app.recipe;

import pl.coderslab.dao.RecipeDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeleteRecipeServlet", value = "/app/recipe/delete")
public class DeleteRecipeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("confirm")==null) {
            int id = Integer.parseInt(request.getParameter("id"));
            RecipeDAO recipeDAO = new RecipeDAO();
            request.setAttribute("message", recipeDAO.read(id).getName());
            request.setAttribute("okAction", "/app/recipe/delete?id=" + id + "&confirm=1");
            request.setAttribute("cancelAction", "/app/recipe/list");
            request.setAttribute("component", "/app/question.jsp");
            getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);
        } else if (request.getParameter("confirm").equals("1")) {
            int id = Integer.parseInt(request.getParameter("id"));
            RecipeDAO recipeDAO = new RecipeDAO();
            recipeDAO.delete(id);
            response.sendRedirect("/app/recipe/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
