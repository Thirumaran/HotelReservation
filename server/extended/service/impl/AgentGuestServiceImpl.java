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
import lk.hotel.extended.service.AgentGuestService;
import lk.hotel.extended.service.dto.ExtendedGuestDTO;
import lk.hotel.service.mapper.GuestMapper;
import lk.hotel.service.mapper.AgentMapper;

/**
 * Service Implementation for managing Guest.
 */
@Service
@Transactional
public class AgentGuestServiceImpl implements AgentGuestService{

    private final Logger log = LoggerFactory.getLogger(ExtendedGuestServiceImpl.class);
    
    @Inject
    private ExtendedGuestRepository guestRepository;

    @Inject
    private GuestMapper guestMapper;

    @Inject
    private AgentRepository agentRepository;

    @Inject
    private AgentMapper agentMapper;


    /**
     * Save a guest.
     *
     * @param guestDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GuestDTO save(ExtendedGuestDTO extGuestDTO) {
        log.debug("Request to creat new Guest or agent : {}", extGuestDTO);
        
        AgentDTO agentDTO = null;
        Guest guest = null;

        if (extGuestDTO.isAgent()) {
            Agent agent = agentMapper.toEntity(getAgentDTO(extGuestDTO));
            
            String telNo = agent.getTelNo();
            String idNo = agent.getIdentityNo();
            /*if (telNo != null && getTelNo()) {

            }*/

            agent = agentRepository.save(agent);
            agentDTO = agentMapper.toDto(agent);
            guest = guestMapper.toEntity(getGuestDTO(extGuestDTO,agentDTO));        
        } else {
            guest = guestMapper.toEntity(getGuestDTO(extGuestDTO));
        }        
        
        guest = guestRepository.save(guest);
        return guestMapper.toDto(guest);
    }


    private GuestDTO getGuestDTO(ExtendedGuestDTO extGuestDTO, AgentDTO agentDTO) {

        GuestDTO guestDTO = getGuestDTO(extGuestDTO);
        guestDTO.setAgentId(agentDTO.getId());
        guestDTO.setAgentName(agentDTO.getName());        

        return guestDTO;
    }

    private GuestDTO getGuestDTO(ExtendedGuestDTO extGuestDTO) {

        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setName(extGuestDTO.getName());
        guestDTO.setTelNo(extGuestDTO.getTelNo());
        guestDTO.setIdentityNo(extGuestDTO.getIdentityNo());
        guestDTO.setSrilankan(extGuestDTO.isSrilankan());
        guestDTO.setAddress(extGuestDTO.getAddress());
        guestDTO.setNote(extGuestDTO.getNote());
        guestDTO.setStatus(true);

        return guestDTO;
    }

    private AgentDTO getAgentDTO(ExtendedGuestDTO extGuestDTO) {

        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setName(extGuestDTO.getName());
        agentDTO.setTelNo(extGuestDTO.getTelNo());
        agentDTO.setIdentityNo(extGuestDTO.getIdentityNo());
        agentDTO.setStatus(true);

        return agentDTO;
    }
}
