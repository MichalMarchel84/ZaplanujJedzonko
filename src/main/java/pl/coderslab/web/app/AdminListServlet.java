package pl.coderslab.web.app;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;
import pl.coderslab.utils.Search;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@WebServlet(name = "AdminListServlet", value = "/app/super-admin-users")
public class AdminListServlet extends HttpServlet {

    private final AdminDao adminDao;

    public AdminListServlet() throws NoSuchMethodException {
        adminDao = new AdminDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        List<Admin> admins;

        if(request.getParameterMap().isEmpty()) {
            admins = adminDao.findAll();
            request.setAttribute("first_name", "checked");
            request.setAttribute("last_name", "checked");
            request.setAttribute("email", "checked");
        }
        else {
            Search<Admin> search = new Search<>(Admin.class, "admins");
            if(request.getParameter("columns") != null) {
                admins = search.inColumns(request.getParameter("searchTxt"), request.getParameterMap().get("columns"), false);
                for (String key : request.getParameterMap().get("columns")) {
                    request.setAttribute(key, "checked");
                }
            }
            else admins = new ArrayList<>();
            request.setAttribute("queryTxt", request.getParameter("searchTxt"));
        }

        HashMap<Admin, Integer> adminMap = new HashMap<Admin, Integer>();


        for (Admin admin : admins) {
            Integer status;
            if (admin.getSuperadmin() == 1) {
                status = 3;
            } else {
                status = admin.getEnable();
            }
            adminMap.put(admin, status);
        }

        TreeMap<Admin, Integer> sortedAdmins = new TreeMap<>(adminMap);


        request.setAttribute("component", "/app/admin/admins.jsp");
        request.setAttribute("adminMap", sortedAdmins);
        getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
