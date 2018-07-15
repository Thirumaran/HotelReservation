package lk.hotel.extended.service.impl;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lk.hotel.domain.Booking;
import lk.hotel.domain.BookingStatus;
import lk.hotel.domain.Guest;
import lk.hotel.domain.Room;
import lk.hotel.extended.service.ExtendedBookingService;
import lk.hotel.extended.service.dto.ExtendedBookingDTO;
import lk.hotel.repository.BookingStatusRepository;
import lk.hotel.repository.GuestRepository;
import lk.hotel.repository.extended.ExtendedBookingRepository;
import lk.hotel.service.dto.BookingDTO;
import lk.hotel.service.dto.RoomDTO;
import lk.hotel.service.mapper.BookingMapper;
import lk.hotel.service.mapper.RoomMapper;

@Service
@Transactional
public class ExtendedBookingServiceImpl implements ExtendedBookingService{

    private final Logger log = LoggerFactory.getLogger(ExtendedBookingServiceImpl.class);
    
    @Inject
    private ExtendedBookingRepository bookingRepository;

    @Inject
    private BookingStatusRepository bookingStatusRepository;
    
    @Inject
    private RoomMapper roomMapper;
    
    @Inject
    private BookingMapper bookingMapper;
    
    
    @Inject
    private GuestRepository guessRepository;
    
	@Override
	public List<ExtendedBookingDTO> findUnavailableRooms() {
		
		
		List<BookingStatus> bookingStatusList = bookingStatusRepository.findAll();
		
		List<BookingStatus> usedBookingStatusList = new ArrayList<BookingStatus>();
		
		for (BookingStatus status : bookingStatusList) {
		
			if (status.getBookingStatus().equals("Booked") || status.getBookingStatus().equals("Checked In")) {
				usedBookingStatusList.add(status);
			}
		}
		
		List<Booking> findUnavailableRooms = bookingRepository.findByBookingStatusIn(usedBookingStatusList);
		List<ExtendedBookingDTO> unavailableRooms = new ArrayList<ExtendedBookingDTO>();
		
		for (Booking room: findUnavailableRooms) {
			Guest guest = guessRepository.findOne(room.getGuest().getId());
			
			ExtendedBookingDTO booking = getBookingDTO(room, guest);
			
			unavailableRooms.add(booking);
		}
		return unavailableRooms;
	}

	private ExtendedBookingDTO getBookingDTO(Booking room, Guest guest) 
	{
		ExtendedBookingDTO booking = new ExtendedBookingDTO();
		
		booking.setId(room.getId());
		booking.setGuestId(guest.getId());
		booking.setCheckIn(room.getCheckIn());
		booking.setCheckOut(room.getCheckOut());
		booking.setGuestName(guest.getName());
		booking.setIdentityNo(guest.getIdentityNo());
		booking.setTelNo(guest.getTelNo());
		booking.setStatus(room.isStatus());
		booking.setBookingStatus(room.getBookingStatus().getBookingStatus());
		
		booking.setRooms(getRoomsList(room));
		
		return booking;
	}

	private Set<RoomDTO> getRoomsList(Booking room)
	{
		Iterator<Room> iterator = room.getRooms().iterator();
		Set<RoomDTO> rooms = new HashSet<>();
		while (iterator.hasNext()) {
			Room next = iterator.next();
			rooms.add(roomMapper.toDto(next));
		}
		return rooms;
	}
	
	@Override
	public List<BookingDTO> findGuestBooking(Long guestId) {
		
		ZonedDateTime checkInDate = ZonedDateTime.of(ZonedDateTime.now().getYear(), 1, 1, 0, 0, 0, 0, ZonedDateTime.now().getZone());   	
		List<Booking> guestBookings = bookingRepository.findByGuestIdAndCheckInGreaterThan(guestId,checkInDate);
		return bookingMapper.toDto(guestBookings);
	}

	@Override
	public List<ExtendedBookingDTO> findBookingByDateRange(ZonedDateTime fromDate, ZonedDateTime toDate) {
		
		List<Booking> bookingReport = bookingRepository.findByCheckOutGreaterThanAndCheckOutLessThan(fromDate,toDate);
		List<ExtendedBookingDTO> bookingReportList = new ArrayList<ExtendedBookingDTO>();
		
		for (Booking room: bookingReport) {
			Guest guest = guessRepository.findOne(room.getGuest().getId());
			
			ExtendedBookingDTO booking = getBookingDTO(room, guest);
			
			bookingReportList.add(booking);
		}
		return bookingReportList;
	}
}
