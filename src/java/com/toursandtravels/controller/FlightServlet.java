package com.toursandtravels.controller;

import com.toursandtravels.dao.FlightDao;
import com.toursandtravels.daoimpl.FlightDaoImpl;
import com.toursandtravels.dto.Flight;
import com.toursandtravels.dto.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FlightServlet extends HttpServlet {

    FlightDao flightDao = new FlightDaoImpl();

    // ================= ADMIN → ADD FLIGHT =================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        // 🔐 ADD FLIGHT (ADMIN)
        if ("addFlight".equals(action)) {

            Flight flight = new Flight();
            flight.setFlightName(req.getParameter("flightName"));
            flight.setSource(req.getParameter("source"));
            flight.setDestination(req.getParameter("destination"));
            flight.setTotalSeats(
                    Integer.parseInt(req.getParameter("seats"))
            );

            flightDao.addFlight(flight);
            res.sendRedirect("/OnlineToursTravels/admin/dashboard.html");
            return;
        }

        // ================= USER → BOOK FLIGHT =================
        if ("bookSeat".equals(action)) {

            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                res.getWriter().println("Session expired. Please login again.");
                return;
            }

            User user = (User) session.getAttribute("user");

            int flightId = Integer.parseInt(req.getParameter("flightId"));
            int seatNo = Integer.parseInt(req.getParameter("seatNo"));
            String date = req.getParameter("date");

            String name = req.getParameter("name");
            String phone = req.getParameter("phone");
            String email = req.getParameter("email");

            boolean booked = flightDao.bookSeat(
                    user.getUserId(),
                    name,
                    phone,
                    email,
                    flightId,
                    seatNo,
                    date
            );

            res.setContentType("text/html");
            PrintWriter out = res.getWriter();

            if (booked) {
                res.sendRedirect("/OnlineToursTravels/user/payment.html");
                
            } else {
                out.println("<h3>❌ Seat Already Booked</h3>");
            }
        }
    }

    // ================= USER → SEARCH FLIGHT =================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Available Flights</title>");
        out.println("<link rel='stylesheet' href='/OnlineToursTravels/css/style.css'>");
        out.println("</head>");
        out.println("<body>");

        String source = req.getParameter("source");
        String destination = req.getParameter("destination");
        String date = req.getParameter("date");

        List<Flight> flights =
                flightDao.searchFlight(source, destination, date);

        if (flights.isEmpty()) {
            out.println("<h3>No flights available</h3>");
            return;
        }

        for (Flight flight : flights) {

            out.println("<h2>" + flight.getFlightName() + "</h2>");
            out.println("<p><b>Route:</b> " + source + " → " + destination + "</p>");
            out.println("<p><b>Date:</b> " + date + "</p>");

            List<Integer> bookedSeats =
                    flightDao.getBookedSeats(flight.getFlightId(), date);

            out.println("<form method='post' action='/OnlineToursTravels/FlightServlet'>");

            out.println("<input type='hidden' name='action' value='bookSeat'>");
            out.println("<input type='hidden' name='flightId' value='" + flight.getFlightId() + "'>");
            out.println("<input type='hidden' name='date' value='" + date + "'>");

            out.println("<label>Name:</label>");
            out.println("<input type='text' name='name' required><br>");

            out.println("<label>Phone:</label>");
            out.println("<input type='text' name='phone' required><br>");

            out.println("<label>Email:</label>");
            out.println("<input type='email' name='email' required><br>");

            out.println("<label>Select Seat:</label>");
            out.println("<select name='seatNo' required>");

            for (int seat = 1; seat <= flight.getTotalSeats(); seat++) {
                if (!bookedSeats.contains(seat)) {
                    out.println("<option value='" + seat + "'>Seat " + seat + "</option>");
                }
            }

            out.println("</select><br>");
            out.println("<button type='submit'>Book</button>");
            out.println("</form><hr>");
        }

        out.println("</body></html>");
    }
}
