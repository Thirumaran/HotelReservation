package lk.hotel.extended.web;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import lk.hotel.extended.service.ExtendedBookingService;
import lk.hotel.extended.service.dto.ExtendedBookingDTO;
import lk.hotel.service.dto.BookingDTO;

/**
 * REST controller for managing Booking.
 */
@RestController
@RequestMapping("/api")
public class ExtendedBookingResource {

    private final Logger log = LoggerFactory.getLogger(ExtendedBookingResource.class);
        
    @Inject
    private ExtendedBookingService bookingService;

    @GetMapping("/unavailable_rooms")
    @Timed
    public ResponseEntity<List<ExtendedBookingDTO>> getUnavailableRooms() {
        
    	List<ExtendedBookingDTO> bookings = bookingService.findUnavailableRooms();
    	//int size = bookings != null ? bookings.size() : 0;
    	
        return Optional.ofNullable(bookings)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
        
    @GetMapping("/guest_booking/{guestId}")
    @Timed
    public ResponseEntity<List<BookingDTO>> getActiveBooking(@PathVariable Long guestId) {
        
    	List<BookingDTO> guestBookings = bookingService.findGuestBooking(guestId);
    	//int size = bookings != null ? bookings.size() : 0;
    	
        return Optional.ofNullable(guestBookings)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    
    @GetMapping("/booking_report/{fromDate}/{toDate}")
    @Timed
    public ResponseEntity<List<ExtendedBookingDTO>> getActiveBooking(@PathVariable String fromDate, @PathVariable String toDate) {
        
    	ZonedDateTime checkInDate = ZonedDateTime.parse(fromDate,DateTimeFormatter.RFC_1123_DATE_TIME);
    	ZonedDateTime checkOutDate = ZonedDateTime.parse(toDate,DateTimeFormatter.RFC_1123_DATE_TIME);
    	
    	List<ExtendedBookingDTO> guestBookings = bookingService.findBookingByDateRange(checkInDate,checkOutDate);
    	//int size = bookings != null ? bookings.size() : 0;
    	
        return Optional.ofNullable(guestBookings)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
