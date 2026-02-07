package com.toursandtravels.controller;

import com.toursandtravels.dao.UserDao;
import com.toursandtravels.dao.AdminDao;
import com.toursandtravels.daoimpl.UserDaoImpl;
import com.toursandtravels.daoimpl.AdminDaoImpl;
import com.toursandtravels.dto.User;
import com.toursandtravels.dto.Admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    UserDao userDao = new UserDaoImpl();
    AdminDao adminDao = new AdminDaoImpl();

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String role = req.getParameter("role");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();

        if ("user".equals(role)) {
            User user = userDao.loginUser(email, password);
            if (user != null) {
                session.setAttribute("user", user);
                res.sendRedirect("/OnlineToursTravels/user/dashboard.html");
                return;
            }
        }

        if ("admin".equals(role)) {
            Admin admin = adminDao.loginAdmin(email, password);
            if (admin != null) {
                session.setAttribute("admin", admin);
                res.sendRedirect("/OnlineToursTravels/admin/dashboard.html");
                return;
            }
        }

        res.getWriter().println("Invalid Login Credentials");
    }
}
