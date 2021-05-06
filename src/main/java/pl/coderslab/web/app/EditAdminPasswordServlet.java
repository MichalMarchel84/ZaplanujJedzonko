package pl.coderslab.web.app;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;
import pl.coderslab.utils.Hashing;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "EditAdminServlet", value = "/app/edit-password")
public class EditAdminPasswordServlet extends HttpServlet {

    private final AdminDao adminDao;

    public EditAdminPasswordServlet() throws NoSuchMethodException {
        adminDao = new AdminDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("component", "/app/admin/editadminpassword.jsp");
        getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        HttpSession session = request.getSession();
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        if (!password.equals(repassword)) {
            String errorMsg = "Podane hasła nie są identyczne";
            request.setAttribute("errorMsg", errorMsg);
            request.setAttribute("component", "/app/admin/editadminpassword.jsp");
            getServletContext().getRequestDispatcher("/app/frame.jsp").forward(request, response);
        } else {
            int id = (Integer) session.getAttribute("adminId");
            adminDao.findById(id).ifPresent(admin -> {
            admin.setPassword(Hashing.hashPassword(password));
            adminDao.updateAdmin(admin);
            });
            response.sendRedirect("/app/dashboard");
        }


    }
}
