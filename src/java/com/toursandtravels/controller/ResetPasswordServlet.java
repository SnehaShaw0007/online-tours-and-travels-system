package com.toursandtravels.controller;

import com.toursandtravels.dao.UserDao;
import com.toursandtravels.daoimpl.UserDaoImpl;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class ResetPasswordServlet extends HttpServlet {

    UserDao userDao = new UserDaoImpl();

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String answer = req.getParameter("answer");
        String newPassword = req.getParameter("newPassword");

        boolean success = userDao.resetPassword(email, answer, newPassword);

        if (success) {
            res.sendRedirect("/OnlineToursTravels/user/login.html");
        } else {
            res.getWriter().println("Invalid security answer");
        }
    }
}
