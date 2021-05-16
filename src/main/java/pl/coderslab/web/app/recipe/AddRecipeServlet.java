package pl.coderslab.web.app.recipe;

import pl.coderslab.dao.RecipeDAO;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DbUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddRecipeServlet", value = "/app/recipe/add")
public class AddRecipeServlet extends HttpServlet {

    private final RecipeDAO recipeDAO = DbUtil.getRecipeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("component", "/app/recipe/add.jsp");
        getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Recipe recipe = new Recipe();

        recipe.setName(request.getParameter("name"));
        recipe.setDescription(request.getParameter("description"));
        recipe.setPreparationTime(Integer.parseInt(request.getParameter("preparationTime")));
        recipe.setPreparation(request.getParameter("preparation"));
        recipe.setIngredients(request.getParameter("ingredients"));
        recipe.setAdminId( (Integer) session.getAttribute("adminId"));

        recipeDAO.create(recipe);
        response.sendRedirect("/app/recipe/list");
    }
}
