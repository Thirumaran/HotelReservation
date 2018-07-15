package lk.hotel.repository.extended;

import lk.hotel.domain.Guest;

import org.springframework.data.jpa.repository.*;

import java.util.List;

public interface ExtendedGuestRepository extends JpaRepository<Guest,Long> {
	List<Guest> findByTelNo(String telNo);
	
	List<Guest> findByIdentityNo(String identityNo);

	List<Guest> findByName(String name);
}
