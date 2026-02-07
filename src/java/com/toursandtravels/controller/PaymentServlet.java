package com.toursandtravels.controller;

import com.toursandtravels.dto.User;
import com.toursandtravels.util.DBConnection;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class PaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");




        // 🔹 booking details (hidden fields se aayenge)
        String bookingType = req.getParameter("bookingType"); // BUS / FLIGHT
        int bookingId = Integer.parseInt(req.getParameter("bookingId"));

        
        try {
            Connection con = DBConnection.getConnection();

            String sql =
"INSERT INTO PAYMENT (PAYMENT_ID, USER_ID, BOOKING_TYPE, BOOKING_ID) " +
"VALUES (PAYMENT_SEQ.NEXTVAL, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getUserId());
            ps.setString(2, bookingType);
            ps.setInt(3, bookingId);

            ps.executeUpdate();
            

            // ✅ PAYMENT SUCCESS PAGE
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Payment Success</title>");
            out.println("<link rel='stylesheet' href='/OnlineToursTravels/css/style.css'>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class='payment-container'>");
            out.println("<h2>✅ Payment Successful</h2>");
            out.println("<p>Booking Confirmed</p>");
            out.println("<a href='/OnlineToursTravels/user/dashboard.html'>Go to Dashboard</a>");
            out.println("</div>");

            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>❌ Payment Failed</h3>");
        }
    }
}
