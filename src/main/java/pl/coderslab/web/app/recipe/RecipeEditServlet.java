package pl.coderslab.web.app.recipe;

import pl.coderslab.dao.RecipeDAO;
import pl.coderslab.model.Recipe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RecipeEditServlet", value = "/app/recipe/edit")
public class RecipeEditServlet extends HttpServlet {

    private final RecipeDAO recipeDAO;

    public RecipeEditServlet() throws NoSuchMethodException {
        recipeDAO = new RecipeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setAttribute("component", "/app/recipe/edit.jsp");
        int id = Integer.parseInt(request.getParameter("id"));

        recipeDAO.read(id).ifPresent(recipe -> request.setAttribute("recipe",recipe));
        getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Recipe recipe = new Recipe();

        recipe.setId(Integer.parseInt(request.getParameter("id")));
        recipe.setName(request.getParameter("name"));
        recipe.setDescription(request.getParameter("description"));
        recipe.setPreparationTime(Integer.parseInt(request.getParameter("preparationTime")));
        recipe.setPreparation(request.getParameter("preparation"));
        recipe.setIngredients(request.getParameter("ingredients"));
        recipe.setAdminId( (Integer) session.getAttribute("adminId"));

        recipeDAO.update(recipe);
        response.sendRedirect("/app/recipe/list");
    }
}