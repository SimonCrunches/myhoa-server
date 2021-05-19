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
    protected Integer id;

    @JsonProperty("category")
    protected String category;

    @JsonProperty("title")
    protected String title;

    @JsonProperty("description")
    protected String description;

    @JsonProperty("latitude")
    protected Double latitude;

    @JsonProperty("longitude")
    protected Double longitude;

    @JsonProperty("milestone")
    protected String milestone;

    @JsonProperty("price")
    protected Integer price;

    @JsonProperty("currentFunds")
    protected Integer currentFunds;

    @JsonProperty("contractor")
    protected Boolean contractor;

    @JsonProperty("imageUrl")
    protected String imageUrl;

    @JsonProperty("activeUser")
    protected Integer activeUser;

    @JsonProperty("creationDate")
    protected String creationDate;

    @JsonProperty("reports")
    protected Integer reports;

    @JsonProperty("documentUrl")
    private String documentUrl;

    @JsonProperty("wallet")
    private String wallet;

    @JsonProperty("fundingUrl")
    private String fundingUrl;

    public InitiativeDTO(@Nonnull final Initiative initiative,
                         @Nonnull final Integer currentFunds) {
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
        this.currentFunds = currentFunds;
        this.reports = initiative.getReports();
        this.documentUrl = initiative.getDocumentUrl();
        this.wallet = initiative.getWallet();
        this.fundingUrl = initiative.getFundingUrl();
    }

}
