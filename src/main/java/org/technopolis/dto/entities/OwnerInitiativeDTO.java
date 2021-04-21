package org.technopolis.dto.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import org.technopolis.entity.logic.Initiative;

import javax.annotation.Nonnull;

@NoArgsConstructor
public class OwnerInitiativeDTO extends InitiativeDTO {

    @JsonProperty("isMine")
    private Boolean isMine;

    public OwnerInitiativeDTO(@Nonnull final Initiative initiative,
                              @Nonnull final Boolean isMine) {
        super(initiative);
        this.isMine = isMine;
    }

    public Boolean getMine() {
        return isMine;
    }
}
