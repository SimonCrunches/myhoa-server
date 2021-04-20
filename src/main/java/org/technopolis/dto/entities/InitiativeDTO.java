package org.technopolis.dto.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.technopolis.entity.logic.Initiative;
import org.technopolis.utils.CommonUtils;

import javax.annotation.Nonnull;

@Data
@NoArgsConstructor
public class InitiativeDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("category")
    private String category;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("milestone")
    private String milestone;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("contractor")
    private Boolean contractor;

    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonProperty("activeUser")
    private Integer activeUser;

    @JsonProperty("creationDate")
    private String creationDate;

    public InitiativeDTO(@Nonnull final Initiative initiative) {
        this.id = initiative.getId();
        this.category = initiative.getCategory().getCategory();
        this.contractor = initiative.getContractor();
        this.description = initiative.getDescription();
        this.imageUrl = initiative.getImageUrl();
        this.latitude = initiative.getLatitude();
        this.longitude = initiative.getLongitude();
        this.milestone = initiative.getMilestone().format(CommonUtils.LOCALDATE);
        this.price = initiative.getPrice();
        this.title = initiative.getTitle();
        this.activeUser = initiative.getActiveUser().getId();
        this.creationDate = initiative.getCreationDate().format(CommonUtils.LOCALDATETIME);
    }

}
