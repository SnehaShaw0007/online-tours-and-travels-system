package com.toursandtravels.dao;

import com.toursandtravels.dto.Flight;
import java.util.List;

public interface FlightDao {

    boolean addFlight(Flight flight);          // admin

    List<Flight> searchFlight(String source, String destination, String date);

    List<Integer> getBookedSeats(int flightId, String date);

    boolean bookSeat(int userId, String name, String phone,
                     String email, int flightId,
                     int seatNo, String journeyDate);

    List<String> getUserBookings(int userId);

    boolean cancelBooking(int bookingId, int userId);
}
