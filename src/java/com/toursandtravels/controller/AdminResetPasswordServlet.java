package com.toursandtravels.controller;

import com.toursandtravels.dao.AdminDao;
import com.toursandtravels.daoimpl.AdminDaoImpl;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class AdminResetPasswordServlet extends HttpServlet {

    AdminDao adminDao = new AdminDaoImpl();

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String answer = req.getParameter("answer");
        String newPassword = req.getParameter("newPassword");

        boolean success = adminDao.resetPassword(email, answer, newPassword);

        if (success) {
            res.sendRedirect("/OnlineToursTravels/admin/login.html");
        } else {
            res.getWriter().println("Invalid security answer");
        }
    }
}
