package lk.hotel.extended.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Guest entity.
 */
public class ExtendedGuestDTO implements Serializable {

    private Long id;

    private String name;

    @Lob
    private String address;

    private String telNo;

    @NotNull
    private Boolean srilankan;

    private String identityNo;

    @Lob
    private String note;

    private Boolean isAgent;

    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public Boolean isSrilankan() {
        return srilankan;
    }

    public void setSrilankan(Boolean srilankan) {
        this.srilankan = srilankan;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean isAgent() {
        return isAgent;
    }

    public void setIsAgent(Boolean isAgent) {
        this.isAgent = isAgent;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExtendedGuestDTO guestDTO = (ExtendedGuestDTO) o;
        if(guestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), guestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GuestDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", telNo='" + getTelNo() + "'" +
            ", srilankan='" + isSrilankan() + "'" +
            ", identityNo='" + getIdentityNo() + "'" +
            ", note='" + getNote() + "'" +
            ", isAgent='" + isAgent() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
