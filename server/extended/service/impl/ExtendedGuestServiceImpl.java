package lk.hotel.extended.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lk.hotel.domain.Guest;
import lk.hotel.domain.Agent;
import lk.hotel.extended.service.ExtendedGuestService;
import lk.hotel.repository.extended.ExtendedGuestRepository;
import lk.hotel.repository.AgentRepository;
import lk.hotel.extended.service.dto.ExtendedGuestDTO;
import lk.hotel.service.dto.GuestDTO;
import lk.hotel.service.dto.AgentDTO;
import lk.hotel.extended.service.dto.ExtendedGuestDTO;
import lk.hotel.service.mapper.GuestMapper;
import lk.hotel.service.mapper.AgentMapper;

/**
 * Service Implementation for managing Guest.
 */
@Service
@Transactional
public class ExtendedGuestServiceImpl implements ExtendedGuestService{

    private final Logger log = LoggerFactory.getLogger(ExtendedGuestServiceImpl.class);
    
    @Inject
    private ExtendedGuestRepository guestRepository;

    @Inject
    private GuestMapper guestMapper;

    @Inject
    private AgentRepository agentRepository;

    @Inject
    private AgentMapper agentMapper;

    @Transactional(readOnly = true) 
    public List<GuestDTO> findByName(String name) {
        log.debug("Request to get all Guests");
        List<Guest> result = guestRepository.findByName(name);
        
        List<GuestDTO> guestDTOList = new ArrayList<GuestDTO>();
        
        for (Guest guest:result) {
        	guestDTOList.add(guestMapper.toDto(guest));
        }
        
        return guestDTOList;
    }
   
    @Transactional(readOnly = true) 
    public List<GuestDTO> findByTelNo(String telNo) {
        log.debug("Request to get all Guests");
        List<Guest> result = guestRepository.findByTelNo(telNo);
        
        List<GuestDTO> guestDTOList = new ArrayList<GuestDTO>();
        
        for (Guest guest:result) {
        	guestDTOList.add(guestMapper.toDto(guest));
        }
        
        return guestDTOList;
    }
    
    @Transactional(readOnly = true) 
    public List<GuestDTO> findByIdentityNo(String identityNo) {
        log.debug("Request to get all Guests");
        List<Guest> result = guestRepository.findByIdentityNo(identityNo);
        
        List<GuestDTO> guestDTOList = new ArrayList<GuestDTO>();
        
        for (Guest guest:result) {
        	guestDTOList.add(guestMapper.toDto(guest));
        }
        
        return guestDTOList;
    }
}
