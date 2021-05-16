package pl.coderslab.web.app.recipe;

import pl.coderslab.dao.RecipeDAO;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.RecipePlan;
import pl.coderslab.utils.DbUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DeleteRecipeServlet", value = "/app/recipe/delete")
public class DeleteRecipeServlet extends HttpServlet {

    private final RecipeDAO recipeDAO = DbUtil.getRecipeDAO();
    private final RecipePlanDao recipePlanDao = DbUtil.getRecipePlanDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        if (request.getParameter("confirm")==null) {

            recipeDAO.read(id).ifPresent(recipe -> request.setAttribute("message", recipe.getName()));
            request.setAttribute("okAction", "/app/recipe/delete?id=" + id + "&confirm=1");
            request.setAttribute("cancelAction", "/app/recipe/list");
            request.setAttribute("component", "/app/question.jsp");
            getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);

        } else if (request.getParameter("confirm").equals("1")) {

            List<RecipePlan> list = recipePlanDao.getForRecipe(id);
            if(!list.isEmpty()){
                if(list.size() == 1) request.setAttribute("message", "Przepis zostanie usunięty z 1 planu");
                else request.setAttribute("message", "Przepis zostanie usunięty z " + list.size() +  " planów");
                request.setAttribute("okAction", "/app/recipe/delete?id=" + id + "&confirm=2");
                request.setAttribute("cancelAction", "/app/recipe/list");
                request.setAttribute("component", "/app/question.jsp");
                getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);
            }
            else {
                recipeDAO.delete(id);
                response.sendRedirect("/app/recipe/list");
            }

        } else if (request.getParameter("confirm").equals("2")){

            recipeDAO.delete(id);
            response.sendRedirect("/app/recipe/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
