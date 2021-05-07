package pl.coderslab.web.page;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;
import pl.coderslab.utils.DbUtil;
import pl.coderslab.utils.Hashing;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final AdminDao adminDao = DbUtil.getAdminDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String pass = req.getParameter("password");

        Optional<Admin> checkAdmin = adminDao.findByEmail(email);

        if (email.equals("")) {

            String errorMsg = "Proszę podać wszystkie dane";
            req.setAttribute("errorMsg", errorMsg);
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);

        } else if (!checkAdmin.isPresent()) {

            String errorMsg = "Użytkownik nie istnieje. Spróbuj ponownie";
            req.setAttribute("errorMsg1", errorMsg);
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);

        } else if (!Hashing.checkPassword(pass, checkAdmin.get().getPassword())) {

            String errorMsg = "Niepoprawne hasło. Spróbuj ponownie";
            req.setAttribute("errorMsg2", errorMsg);
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);

        } else if (checkAdmin.get().getEnable() == 0) {

            String errorMsg = "Administrator zablokował Twoje konto.";
            req.setAttribute("errorMsg", errorMsg);
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);

        }
        else {

            HttpSession session = req.getSession();
            Admin admin = checkAdmin.get();
            session.setAttribute("adminId", admin.getId());
            session.setAttribute("adminName", admin.getFirstName());
            session.setAttribute("enable", admin.getEnable());
            if (admin.getSuperadmin()==1)
            {
                session.setAttribute("superAdmin", admin.getSuperadmin());
            }
            resp.sendRedirect("/app/dashboard");
        }
    }
}
