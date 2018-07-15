package lk.hotel.extended.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import lk.hotel.extended.service.ExtendedGuestService;
import lk.hotel.extended.service.AgentGuestService;
import lk.hotel.service.dto.GuestDTO;
import lk.hotel.extended.service.dto.ExtendedGuestDTO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import lk.hotel.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing Booking.
 */
@RestController
@RequestMapping("/api")
public class ExtendedGuestResource {

    private final Logger log = LoggerFactory.getLogger(ExtendedGuestResource.class);
    
    private static final String ENTITY_NAME = "guest";

    @Inject
    private ExtendedGuestService guestService;

    @Inject
    private AgentGuestService agentAndGuestService;

    @GetMapping("/guests/{telNo}/{identityNo}")
    @Timed
    public ResponseEntity<List<GuestDTO>> findGuest(@PathVariable String telNo, @PathVariable String identityNo) 
    {
    	List<GuestDTO> guestList;
    
    	if (!telNo.toString().trim().equals("Empty")) 
    	{
    		guestList = guestService.findByTelNo(telNo);
    	}
    	else if (!identityNo.toString().trim().equals("Empty")) 
    	{
    		guestList = guestService.findByIdentityNo(identityNo);
    	}
    	else
    	{
        	guestList = new ArrayList<GuestDTO>();
    	}
    	
        return Optional.ofNullable(guestList)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

        /**
     * POST  /guests : Create a new guest.
     *
     * @param guestDTO the guestDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new guestDTO, or with status 400 (Bad Request) if the guest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agent-guest")
    @Timed
    public ResponseEntity<GuestDTO> createGuest(@Valid @RequestBody ExtendedGuestDTO guestDTO) throws URISyntaxException {
        log.debug("REST request to save Agent / Guest : {}", guestDTO);
        if (guestDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new guest cannot already have an ID")).body(null);
        }
        GuestDTO result = agentAndGuestService.save(guestDTO);
        return ResponseEntity.created(new URI("/api/agent-guest/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}
