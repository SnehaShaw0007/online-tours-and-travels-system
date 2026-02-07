package com.toursandtravels.controller;

import com.toursandtravels.dto.User;
import com.toursandtravels.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookingHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("/OnlineToursTravels/user/login.html");
            return;
        }

        User user = (User) session.getAttribute("user");

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Your Bookings</title>");
        out.println("<link rel='stylesheet' href='/OnlineToursTravels/css/style.css'>");
        out.println("</head>");
        out.println("<body>");

        out.println("<h1>Your Bookings</h1>");

        try {
            Connection con = DBConnection.getConnection();

            /* ================= BUS BOOKINGS ================= */
            out.println("<h2>🚌 Bus Bookings</h2>");

            String busSql =
                "SELECT bb.BOOKING_ID, b.BUS_NAME, bb.NAME, bb.PHONE_NO, " +
                "bb.EMAIL, bb.SEAT_NO, bb.JOURNEY_DATE " +
                "FROM BUS_BOOKING bb JOIN BUS b ON bb.BUS_ID=b.BUS_ID " +
                "WHERE bb.USER_ID=?";

            PreparedStatement psBus = con.prepareStatement(busSql);
            psBus.setInt(1, user.getUserId());
            ResultSet rsBus = psBus.executeQuery();

            boolean busFound = false;

            while (rsBus.next()) {
                busFound = true;

                out.println("<div class='booking-card'>");
                out.println("<b>Bus:</b> " + rsBus.getString("BUS_NAME") + "<br>");
                out.println("<b>Name:</b> " + rsBus.getString("NAME") + "<br>");
                out.println("<b>Phone:</b> " + rsBus.getString("PHONE_NO") + "<br>");
                out.println("<b>Email:</b> " + rsBus.getString("EMAIL") + "<br>");
                out.println("<b>Seat:</b> " + rsBus.getInt("SEAT_NO") + "<br>");
                out.println("<b>Date:</b> " + rsBus.getDate("JOURNEY_DATE") + "<br><br>");

                out.println("<form method='post' action='/OnlineToursTravels/CancelBookingServlet'>");
                out.println("<input type='hidden' name='type' value='bus'>");
                out.println("<input type='hidden' name='bookingId' value='" +
                            rsBus.getInt("BOOKING_ID") + "'>");
                out.println("<button type='submit' class='cancel-btn'>Cancel Booking</button>");
                out.println("</form>");
                out.println("</div>");
            }

            if (!busFound) {
                out.println("<p>No bus bookings found</p>");
            }

            /* ================= FLIGHT BOOKINGS ================= */
            out.println("<h2>✈️ Flight Bookings</h2>");

            String flightSql =
                "SELECT fb.BOOKING_ID, f.FLIGHT_NAME, fb.NAME, fb.PHONE_NO, " +
                "fb.EMAIL, fb.SEAT_NO, fb.JOURNEY_DATE " +
                "FROM FLIGHT_BOOKING fb JOIN FLIGHT f ON fb.FLIGHT_ID=f.FLIGHT_ID " +
                "WHERE fb.USER_ID=?";

            PreparedStatement psFlight = con.prepareStatement(flightSql);
            psFlight.setInt(1, user.getUserId());
            ResultSet rsFlight = psFlight.executeQuery();

            boolean flightFound = false;

            while (rsFlight.next()) {
                flightFound = true;

                out.println("<div class='booking-card'>");
                out.println("<b>Flight:</b> " + rsFlight.getString("FLIGHT_NAME") + "<br>");
                out.println("<b>Name:</b> " + rsFlight.getString("NAME") + "<br>");
                out.println("<b>Phone:</b> " + rsFlight.getString("PHONE_NO") + "<br>");
                out.println("<b>Email:</b> " + rsFlight.getString("EMAIL") + "<br>");
                out.println("<b>Seat:</b> " + rsFlight.getInt("SEAT_NO") + "<br>");
                out.println("<b>Date:</b> " + rsFlight.getDate("JOURNEY_DATE") + "<br><br>");

                out.println("<form method='post' action='/OnlineToursTravels/CancelBookingServlet'>");
                out.println("<input type='hidden' name='type' value='flight'>");
                out.println("<input type='hidden' name='bookingId' value='" +
                            rsFlight.getInt("BOOKING_ID") + "'>");
                out.println("<button type='submit' class='cancel-btn'>Cancel Booking</button>");
                out.println("</form>");
                out.println("</div>");
            }

            if (!flightFound) {
                out.println("<p>No flight bookings found</p>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        out.println("</body></html>");
    }
}
