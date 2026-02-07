package com.toursandtravels.controller;

import com.toursandtravels.dao.AdminDao;
import com.toursandtravels.daoimpl.AdminDaoImpl;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class AdminForgotPasswordServlet extends HttpServlet {

    AdminDao adminDao = new AdminDaoImpl();

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("<html>");
out.println("<head>");
out.println("<title>Your Bookings</title>");
out.println("<link rel='stylesheet' href='/OnlineToursTravels/css/style.css'>");
out.println("</head>");
out.println("<body>");

        String email = req.getParameter("email");
        String question = adminDao.getSecurityQuestion(email);

        if (question == null) {
            out.println("<h3>Email not found</h3>");
            return;
        }

        out.println("<h3>Security Question</h3>");
        out.println("<form action='/OnlineToursTravels/AdminResetPasswordServlet' method='post'>");
        out.println("<input type='hidden' name='email' value='" + email + "'>");
        out.println("<p>" + question + "</p>");
        out.println("Answer: <input name='answer' required><br><br>");
        out.println("New Password: <input type='password' name='newPassword' required><br><br>");
        out.println("<button type='submit'>Reset Password</button>");
        out.println("</form>");
    }
}
