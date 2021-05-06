package org.technopolis.dto.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import org.technopolis.entity.logic.Initiative;

import javax.annotation.Nonnull;

@NoArgsConstructor
public class OwnerFavouriteInitiativeDTO extends InitiativeDTO {

    @JsonProperty("isMine")
    private Boolean isMine;

    @JsonProperty("isFavourite")
    private Boolean isFavourite;

    public OwnerFavouriteInitiativeDTO(@Nonnull final Initiative initiative,
                                       @Nonnull final Integer currentFunds,
                                       @Nonnull final Boolean isMine,
                                       @Nonnull final Boolean isFavourite) {
        super(initiative, currentFunds);
        this.isMine = isMine;
        this.isFavourite = isFavourite;
    }

    public Boolean getMine() {
        return isMine;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }
}
