package pl.coderslab.web.page;

import pl.coderslab.dao.RecipeDAO;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PageRecipeDetailsServlet", value = "/details")
public class PageRecipeDetailsServlet extends HttpServlet {

    private final RecipeDAO recipeDAO = DbUtil.getRecipeDAO();

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
