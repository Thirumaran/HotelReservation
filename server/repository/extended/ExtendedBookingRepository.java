package lk.hotel.repository.extended;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.hotel.domain.Booking;
import lk.hotel.domain.BookingStatus;

public interface ExtendedBookingRepository extends JpaRepository<Booking,Long> {
	
	List<Booking> findByBookingStatusIn(Collection<BookingStatus> bookingStatus);
	List<Booking> findByGuestId(Long guestId);
	List<Booking> findByGuestIdAndCheckInGreaterThan(Long guestId,ZonedDateTime checkIn);
	List<Booking> findByCheckOutGreaterThanAndCheckOutLessThan(ZonedDateTime fromDate, ZonedDateTime toDate);
}
