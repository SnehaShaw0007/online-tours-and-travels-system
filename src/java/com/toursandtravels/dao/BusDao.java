package com.toursandtravels.dao;

import java.util.List;
import com.toursandtravels.dto.Bus;

public interface BusDao {

    boolean addBus(Bus bus);

    List<Bus> searchBus(String source, String destination, String journeyDate);

   boolean bookSeat(
    int userId,
    String name,
    String phone,
    String email,
    int busId,
    int seatNo,
    String journeyDate
);

   boolean cancelBooking(int bookingId, int userId);


    List<Integer> getBookedSeats(int busId, String journeyDate);
    List<String> getUserBookings(int userId);

}
