package pl.coderslab.web.app.recipe;

import pl.coderslab.dao.RecipeDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RecipeDetailsServlet", value = "/app/recipe/details")
public class RecipeDetailsServlet extends HttpServlet {

    private final RecipeDAO recipeDAO;

    public RecipeDetailsServlet() throws NoSuchMethodException {
        recipeDAO = new RecipeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setAttribute("component", "/app/recipe/details.jsp");
        int id = Integer.parseInt(request.getParameter("id"));

        recipeDAO.read(id).ifPresent(recipe -> request.setAttribute("recipe", recipe));
        getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
