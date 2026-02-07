package com.toursandtravels.daoimpl;

import com.toursandtravels.dao.BusDao;
import com.toursandtravels.dto.Bus;
import com.toursandtravels.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusDaoImpl implements BusDao {

    @Override
    public boolean addBus(Bus bus) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO BUS (BUS_NAME, SOURCE, DESTINATION, JOURNEY_DATE, TOTAL_SEATS, PHONE_NO) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, bus.getBusName());
            ps.setString(2, bus.getSource());
            ps.setString(3, bus.getDestination());
            ps.setDate(4, Date.valueOf(bus.getJourneyDate()));
            ps.setInt(5, bus.getTotalSeats());
            ps.setString(6, bus.getPhoneNo());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
public List<String> getUserBookings(int userId) {
    List<String> list = new ArrayList<>();
    try {
        Connection con = DBConnection.getConnection();
        String sql =
  "SELECT bb.BOOKING_ID, b.BUS_NAME, bb.NAME, bb.PHONE_NO,\n" +
"       bb.EMAIL, bb.SEAT_NO, bb.JOURNEY_DATE\n" +
"FROM BUS_BOOKING bb\n" +
"JOIN BUS b ON bb.BUS_ID=b.BUS_ID\n" +
"WHERE bb.USER_ID=?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(
    "<b>Bus:</b> " + rs.getString("BUS_NAME") + "<br>" +
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

@Override
public boolean cancelBooking(int bookingId, int userId) {
    try {
        Connection con = DBConnection.getConnection();
        String sql =
            "DELETE FROM BUS_BOOKING WHERE BOOKING_ID=? AND USER_ID=?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, bookingId);
        ps.setInt(2, userId);

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

    @Override
    public List<Bus> searchBus(String source, String destination, String journeyDate) {
        List<Bus> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM BUS WHERE SOURCE=? AND DESTINATION=? AND JOURNEY_DATE=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, source);
            ps.setString(2, destination);
            ps.setDate(3, Date.valueOf(journeyDate));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bus bus = new Bus();
                bus.setBusId(rs.getInt("BUS_ID"));
                bus.setBusName(rs.getString("BUS_NAME"));
                bus.setTotalSeats(rs.getInt("TOTAL_SEATS"));
                list.add(bus);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Integer> getBookedSeats(int busId, String journeyDate) {
        List<Integer> seats = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT SEAT_NO FROM BUS_BOOKING WHERE BUS_ID=? AND JOURNEY_DATE=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, busId);
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

    @Override
public boolean bookSeat(int userId, String name, String phone,
                        String email, int busId, int seatNo, String journeyDate) {

    try {
        Connection con = DBConnection.getConnection();

        String sql =
            "INSERT INTO BUS_BOOKING " +
            "(USER_ID, NAME, PHONE_NO, EMAIL, BUS_ID, SEAT_NO, JOURNEY_DATE) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, userId);
        ps.setString(2, name);
        ps.setString(3, phone);
        ps.setString(4, email);
        ps.setInt(5, busId);
        ps.setInt(6, seatNo);
        ps.setDate(7, Date.valueOf(journeyDate));

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace(); // duplicate seat handled by UNIQUE constraint
    }
    return false;
}

}
