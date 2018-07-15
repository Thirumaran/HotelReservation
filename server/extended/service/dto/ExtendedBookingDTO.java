package lk.hotel.extended.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lk.hotel.service.dto.RoomDTO;

import java.util.Objects;


/**
 * A DTO for the Booking entity.
 */
public class ExtendedBookingDTO implements Serializable {

    private Long id;

    private ZonedDateTime checkIn;

    private ZonedDateTime checkOut;

    private Boolean status;

    private Long guestId;
    
    private String guestName;

    private String bookingStatus;
    
    private String telNo;
    
    private String identityNo;
    
    private Set<RoomDTO> rooms = new HashSet<>();

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ZonedDateTime getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(ZonedDateTime checkIn) {
		this.checkIn = checkIn;
	}

	public ZonedDateTime getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(ZonedDateTime checkOut) {
		this.checkOut = checkOut;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Long getGuestId() {
		return guestId;
	}

	public void setGuestId(Long guestId) {
		this.guestId = guestId;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public Set<RoomDTO> getRooms() {
		return rooms;
	}

	public void setRooms(Set<RoomDTO> rooms) {
		this.rooms = rooms;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExtendedBookingDTO bookingDTO = (ExtendedBookingDTO) o;

        if ( ! Objects.equals(id, bookingDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
