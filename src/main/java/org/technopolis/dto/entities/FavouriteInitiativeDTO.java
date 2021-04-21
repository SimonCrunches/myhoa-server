package org.technopolis.dto.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import org.technopolis.entity.logic.Initiative;

import javax.annotation.Nonnull;

@NoArgsConstructor
public class FavouriteInitiativeDTO extends InitiativeDTO {

    @JsonProperty("isFavourite")
    private Boolean isFavourite;

    public FavouriteInitiativeDTO(@Nonnull final Initiative initiative,
                                  @Nonnull final Boolean isFavourite) {
        super(initiative);
        this.isFavourite = isFavourite;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }
}
