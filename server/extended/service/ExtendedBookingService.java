package lk.hotel.extended.service;

import java.time.ZonedDateTime;
import java.util.List;

import lk.hotel.extended.service.dto.ExtendedBookingDTO;
import lk.hotel.service.dto.BookingDTO;

/**
 * Service Interface for managing Booking.
 */
public interface ExtendedBookingService {

    List<ExtendedBookingDTO> findUnavailableRooms();

	List<BookingDTO> findGuestBooking(Long guestId);

	List<ExtendedBookingDTO> findBookingByDateRange(ZonedDateTime checkIn, ZonedDateTime checkOut);
}