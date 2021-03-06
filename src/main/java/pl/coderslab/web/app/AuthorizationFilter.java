package pl.coderslab.web.app;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/app/*")
public class AuthorizationFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpSession session = ((HttpServletRequest)request).getSession();
        Object adminId = session.getAttribute("adminId");

        if (adminId==null) {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect("/login");
        } else {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            request.setAttribute("adminId", session.getAttribute("adminId"));
            request.setAttribute("adminName", session.getAttribute("adminName"));
            request.setAttribute("enable", session.getAttribute("enable"));
            if (session.getAttribute("superAdmin")!=null) {
                request.setAttribute("superAdmin", session.getAttribute("superAdmin"));
            }
            chain.doFilter(request, response);
        }
    }
}