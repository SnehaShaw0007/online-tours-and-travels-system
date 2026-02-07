package com.toursandtravels.daoimpl;

import com.toursandtravels.dao.FlightDao;
import com.toursandtravels.dto.Flight;
import com.toursandtravels.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightDaoImpl implements FlightDao {

    // ================= ADMIN → ADD FLIGHT =================
    @Override
public boolean addFlight(Flight flight) {
    try {
        Connection con = DBConnection.getConnection();

        String sql =
            "INSERT INTO FLIGHT (FLIGHT_NAME, SOURCE, DESTINATION, TOTAL_SEATS) " +
            "VALUES (?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, flight.getFlightName());
        ps.setString(2, flight.getSource());
        ps.setString(3, flight.getDestination());
        ps.setInt(4, flight.getTotalSeats());

       return ps.executeUpdate() > 0;


    } catch (Exception e) {
        e.printStackTrace(); // ❗ console dekho
    }
    return false;
}


    // ================= USER → SEARCH FLIGHT =================
    @Override
    public List<Flight> searchFlight(String source, String destination, String journeyDate) {
        List<Flight> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                "SELECT * FROM FLIGHT WHERE SOURCE=? AND DESTINATION=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, source);
            ps.setString(2, destination);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Flight flight = new Flight();
                flight.setFlightId(rs.getInt("FLIGHT_ID"));
                flight.setFlightName(rs.getString("FLIGHT_NAME"));
                flight.setTotalSeats(rs.getInt("TOTAL_SEATS"));
                list.add(flight);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================= GET BOOKED SEATS =================
    @Override
    public List<Integer> getBookedSeats(int flightId, String journeyDate) {
        List<Integer> seats = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                "SELECT SEAT_NO FROM FLIGHT_BOOKING WHERE FLIGHT_ID=? AND JOURNEY_DATE=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, flightId);
            ps.setDate(2, Date.valueOf(journeyDate));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                seats.add(rs.getInt("SEAT_NO"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return seats;
    }

    // ================= USER → BOOK FLIGHT =================
    @Override
    public boolean bookSeat(int userId, String name, String phone,
                            String email, int flightId,
                            int seatNo, String journeyDate) {

        try {
            Connection con = DBConnection.getConnection();

            String sql =
"INSERT INTO FLIGHT_BOOKING " +
"(BOOKING_ID, USER_ID, NAME, PHONE_NO, EMAIL, FLIGHT_ID, SEAT_NO, JOURNEY_DATE) " +
"VALUES (FLIGHT_BOOK_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";

PreparedStatement ps = con.prepareStatement(sql);

ps.setInt(1, userId);
ps.setString(2, name);
ps.setString(3, phone);
ps.setString(4, email);
ps.setInt(5, flightId);
ps.setInt(6, seatNo);
ps.setDate(7, Date.valueOf(journeyDate));


            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace(); // seat already booked / constraint issue
        }
        return false;
    }

    // ================= USER → BOOKING HISTORY =================
   // @Override
    public List<String> getUserBookings(int userId) {
        List<String> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                "SELECT fb.BOOKING_ID, f.FLIGHT_NAME, fb.NAME, fb.PHONE_NO, " +
                "fb.EMAIL, fb.SEAT_NO, fb.JOURNEY_DATE " +
                "FROM FLIGHT_BOOKING fb " +
                "JOIN FLIGHT f ON fb.FLIGHT_ID=f.FLIGHT_ID " +
                "WHERE fb.USER_ID=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(
                    "<b>Flight:</b> " + rs.getString("FLIGHT_NAME") + "<br>" +
                    "<b>Name:</b> " + rs.getString("NAME") + "<br>" +
                    "<b>Phone:</b> " + rs.getString("PHONE_NO") + "<br>" +
                    "<b>Email:</b> " + rs.getString("EMAIL") + "<br>" +
                    "<b>Seat:</b> " + rs.getInt("SEAT_NO") + "<br>" +
                    "<b>Date:</b> " + rs.getDate("JOURNEY_DATE")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================= USER → CANCEL BOOKING =================
    @Override
    public boolean cancelBooking(int bookingId, int userId) {
        try {
            Connection con = DBConnection.getConnection();

            String sql =
                "DELETE FROM FLIGHT_BOOKING WHERE BOOKING_ID=? AND USER_ID=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookingId);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
