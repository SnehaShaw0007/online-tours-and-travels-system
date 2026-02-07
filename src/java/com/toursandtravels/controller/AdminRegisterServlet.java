package com.toursandtravels.controller;

import com.toursandtravels.dao.AdminDao;
import com.toursandtravels.daoimpl.AdminDaoImpl;
import com.toursandtravels.dto.Admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class AdminRegisterServlet extends HttpServlet {

    AdminDao adminDao = new AdminDaoImpl();

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Admin admin = new Admin();
        admin.setName(req.getParameter("name"));
        admin.setPhoneNo(req.getParameter("phone"));
        admin.setEmail(req.getParameter("email"));
        admin.setPassword(req.getParameter("password"));
        admin.setSecurityQuestion(req.getParameter("question"));
        admin.setSecurityAnswer(req.getParameter("answer"));

        boolean success = adminDao.registerAdmin(admin);

        if (success)
            res.sendRedirect("/OnlineToursTravels/admin/login.html");
        else
            res.getWriter().println("Admin Registration Failed");
    }
}
