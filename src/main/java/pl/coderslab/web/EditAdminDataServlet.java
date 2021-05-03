package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "EditUserDataServlet", value = "/app/edit-admin-data")
public class EditAdminDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setAttribute("component", "/app/admin/editadmindata.jsp");
        int id = ((Integer) session.getAttribute("adminId"));
        AdminDao adminDao = new AdminDao();
        Admin admin = adminDao.findById(id);
        request.setAttribute("admin", admin);
        getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AdminDao adminDao = new AdminDao();
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        Admin checkAdmin = adminDao.findByEmail(email);

        if ((checkAdmin.getId()>0) && (checkAdmin.getId() != (Integer) session.getAttribute("adminId"))) {

            int id = ((Integer) session.getAttribute("adminId"));
            Admin admin = adminDao.findById(id);
            request.setAttribute("admin", admin);
            String errorMsg = "Użytkownik o podanym mailu już istnieje";
            request.setAttribute("errorMsg", errorMsg);
            request.setAttribute("component", "/app/admin/editadmindata.jsp");
            getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);

        } else {

        Admin admin = adminDao.findById((Integer) session.getAttribute("adminId"));
        admin.setFirstName(request.getParameter("firstName"));
        admin.setLastName(request.getParameter("lastName"));
        admin.setEmail(request.getParameter("email"));
        adminDao.updateAdmin(admin);
        session.setAttribute("adminName", request.getParameter("firstName"));
        response.sendRedirect("/app/dashboard");
        }
    }
}