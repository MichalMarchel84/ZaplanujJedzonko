package pl.coderslab.web.page;

import pl.coderslab.dao.RecipeDAO;
import pl.coderslab.model.Recipe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "PageRecipeDetailsServlet", value = "/details")
public class PageRecipeDetailsServlet extends HttpServlet {

    private final RecipeDAO recipeDAO;

    public PageRecipeDetailsServlet() throws NoSuchMethodException {
        recipeDAO = new RecipeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        recipeDAO.read(Integer.parseInt(request.getParameter("id")))
                .ifPresent(recipe -> request.setAttribute("recipe", recipe));
        String[] list = ((Recipe) request.getAttribute("recipe")).getIngredients().split("\n");
        request.setAttribute("ingredients", list);
        String origin = request.getQueryString().replaceFirst("id=[0-9]*", "");
        if(origin.length() > 0) request.setAttribute("origin", origin.replaceFirst("&", "?"));
        getServletContext().getRequestDispatcher("/details.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
