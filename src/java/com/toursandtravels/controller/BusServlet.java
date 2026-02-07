package com.toursandtravels.controller;

import com.toursandtravels.dao.BusDao;
import com.toursandtravels.daoimpl.BusDaoImpl;
import com.toursandtravels.dto.Bus;
import com.toursandtravels.dto.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class BusServlet extends HttpServlet {

    BusDao busDao = new BusDaoImpl();

    // Admin → Add Bus
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        // ADD BUS (ADMIN)
        if ("addBus".equals(action)) {
            Bus bus = new Bus();
            bus.setBusName(req.getParameter("busName"));
            bus.setSource(req.getParameter("source"));
            bus.setDestination(req.getParameter("destination"));
            bus.setJourneyDate(req.getParameter("date"));
            bus.setTotalSeats(Integer.parseInt(req.getParameter("seats")));
            bus.setPhoneNo(req.getParameter("phone"));

            busDao.addBus(bus);
            res.sendRedirect("/OnlineToursTravels/admin/dashboard.html");
        }

        // BOOK SEAT (USER)
        if ("bookSeat".equals(action)) {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");

            int busId = Integer.parseInt(req.getParameter("busId"));
            int seatNo = Integer.parseInt(req.getParameter("seatNo"));
            String date = req.getParameter("date");

 String name = req.getParameter("name");
String phone = req.getParameter("phone");
String email = req.getParameter("email");

boolean booked = busDao.bookSeat(
    user.getUserId(),   // logged-in user
    name,
    phone,
    email,
    busId,
    seatNo,
    date
);



            if (booked)
               res.sendRedirect("/OnlineToursTravels/user/payment.html");
            else
                res.getWriter().println("Seat Already Booked");
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
    out.println("<html>");
out.println("<head>");
out.println("<title>Your Bookings</title>");
out.println("<link rel='stylesheet' href='/OnlineToursTravels/css/style.css'>");
out.println("</head>");
out.println("<body>");

    String source = req.getParameter("source");
    String destination = req.getParameter("destination");
    String date = req.getParameter("date");

    List<Bus> buses = busDao.searchBus(source, destination, date);

    if (buses.isEmpty()) {
        out.println("<h3>No buses available</h3>");
        return;
    }

    for (Bus bus : buses) {

        // 🔹 Bus details
        out.println("<h2>" + bus.getBusName() + "</h2>");
        out.println("<p><b>Route:</b> " + source + " → " + destination + "</p>");
        out.println("<p><b>Date:</b> " + date + "</p>");

        // 🔹 Get booked seats
        List<Integer> bookedSeats =
                busDao.getBookedSeats(bus.getBusId(), date);

        out.println("<form method='post' action='/OnlineToursTravels/BusServlet'>");

out.println("<input type='hidden' name='action' value='bookSeat'>");
out.println("<input type='hidden' name='busId' value='" + bus.getBusId() + "'>");
out.println("<input type='hidden' name='date' value='" + date + "'>");

out.println("<label>Name:</label>");
out.println("<input type='text' name='name' required><br>");

out.println("<label>Phone:</label>");
out.println("<input type='text' name='phone' required><br>");

out.println("<label>Email:</label>");
out.println("<input type='email' name='email' required><br>");

out.println("<label>Select Seat:</label>");
out.println("<select name='seatNo' required>");

for (int seat = 1; seat <= bus.getTotalSeats(); seat++) {
    if (!bookedSeats.contains(seat)) {
        out.println("<option value='" + seat + "'>Seat " + seat + "</option>");
    }
}

out.println("</select><br>");
out.println("<button type='submit'>Book</button>");
out.println("</form><hr>");

    }
}


}
