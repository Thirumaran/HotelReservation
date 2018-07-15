package lk.hotel.extended.service;

import lk.hotel.service.dto.GuestDTO;
import lk.hotel.extended.service.dto.ExtendedGuestDTO;
import java.util.List;

/**
 * Service Interface for managing Guest.
 */
public interface ExtendedGuestService {
	List<GuestDTO> findByTelNo(String telNo);
	
	List<GuestDTO> findByIdentityNo(String identityNo);

	List<GuestDTO> findByName(String name);
}
