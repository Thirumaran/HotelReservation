package lk.hotel.extended.service;

import lk.hotel.service.dto.GuestDTO;
import lk.hotel.extended.service.dto.ExtendedGuestDTO;
import java.util.List;

/**
 * Service Interface for managing Guest.
 */
public interface AgentGuestService {

    GuestDTO save(ExtendedGuestDTO guestDTO);
}
