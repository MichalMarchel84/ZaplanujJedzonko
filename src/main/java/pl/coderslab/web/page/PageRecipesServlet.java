package pl.coderslab.web.page;

import pl.coderslab.dao.RecipeDAO;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.Search;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "PageRecipesServlet", value = "/recipes")
public class PageRecipesServlet extends HttpServlet {

    private static final int resultsPerPage = 20;
    private final RecipeDAO dao;

    public PageRecipesServlet() throws NoSuchMethodException {
        dao = new RecipeDAO();
    }

    private List<Recipe> prepareList(HttpServletRequest request, int pageNo){

        int records = dao.numberOfRecipes();
        if(records > resultsPerPage){
            int pages = (int)(Math.ceil((double) records/resultsPerPage));
            if(pages > 10) pages = 10;
            request.setAttribute("pagesCount", pages);
            request.setAttribute("actualPage", pageNo);
        }
        List<Recipe> list = dao.getWithLimit(resultsPerPage, (pageNo - 1) * resultsPerPage);
        request.setAttribute("name", "checked");
        request.setAttribute("description", "checked");
        request.setAttribute("ingredients", "checked");
        request.setAttribute("preparation", "checked");
        return list;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Recipe> list;
        if(request.getParameterMap().isEmpty()){
            list = prepareList(request, 1);
        }
        else if(request.getParameter("page") != null){
            list = prepareList(request, Integer.parseInt(request.getParameter("page")));
        }
        else {
            Search<Recipe> search = new Search<>(Recipe.class, "recipe");
            if(request.getParameter("columns") != null) {
                list = search.inColumns(request.getParameter("searchTxt"), request.getParameterMap().get("columns"));
                for (String key : request.getParameterMap().get("columns")) {
                    request.setAttribute(key, "checked");
                }
            }
            else list = new ArrayList<>();
            request.setAttribute("queryTxt", request.getParameter("searchTxt"));
        }
        if(request.getQueryString() != null) request.setAttribute("origin", "&" + request.getQueryString());
        request.setAttribute("results", list);
        getServletContext().getRequestDispatcher("/recipes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
