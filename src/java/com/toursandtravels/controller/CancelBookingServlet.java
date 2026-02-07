package com.toursandtravels.controller;

import com.toursandtravels.dao.BusDao;
import com.toursandtravels.daoimpl.BusDaoImpl;
import com.toursandtravels.dto.User;
import com.toursandtravels.util.DBConnection;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CancelBookingServlet extends HttpServlet {

    BusDao busDao = new BusDaoImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("/OnlineToursTravels/user/login.html");
            return;
        }

        User user = (User) session.getAttribute("user");

        int bookingId = Integer.parseInt(req.getParameter("bookingId"));
        String type = req.getParameter("type"); // bus or flight

        try {
            if ("bus".equals(type)) {

                // ✅ BUS CANCEL (already working)
                busDao.cancelBooking(bookingId, user.getUserId());

            } else if ("flight".equals(type)) {

                // ✅ FLIGHT CANCEL
                Connection con = DBConnection.getConnection();
                String sql =
                    "DELETE FROM FLIGHT_BOOKING WHERE BOOKING_ID=? AND USER_ID=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, bookingId);
                ps.setInt(2, user.getUserId());
                ps.executeUpdate();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 🔁 back to booking history
        res.sendRedirect("/OnlineToursTravels/BookingHistoryServlet");
    }
}
