package pl.coderslab.web.app.recipe;

import pl.coderslab.dao.RecipeDAO;
import pl.coderslab.model.Recipe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "RecipeServlet", value = "/app/recipe/list")
public class RecipeServlet extends HttpServlet {

    private final RecipeDAO recipeDAO;

    public RecipeServlet() throws NoSuchMethodException {
        recipeDAO = new RecipeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        request.setAttribute("component", "/app/recipe/recipelist.jsp");

        List<Recipe> recipes = recipeDAO.findAllByAdmin((Integer) session.getAttribute("adminId"));
        recipes.sort(Comparator.comparing(Recipe::getCreated).reversed());
        request.setAttribute("recipes", recipes);
        getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
