package com.toursandtravels.controller;

import com.toursandtravels.dao.UserDao;
import com.toursandtravels.daoimpl.UserDaoImpl;
import com.toursandtravels.dto.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class UserRegisterServlet extends HttpServlet {

    UserDao userDao = new UserDaoImpl();

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        User user = new User();
        user.setName(req.getParameter("name"));
        user.setPhoneNo(req.getParameter("phone"));
        user.setEmail(req.getParameter("email"));
        user.setPassword(req.getParameter("password"));
        user.setSecurityQuestion(req.getParameter("question"));
        user.setSecurityAnswer(req.getParameter("answer"));

        boolean success = userDao.registerUser(user);

        if (success)
            res.sendRedirect("/OnlineToursTravels/user/login.html");
        else
            res.getWriter().println("User Registration Failed");
    }
}
