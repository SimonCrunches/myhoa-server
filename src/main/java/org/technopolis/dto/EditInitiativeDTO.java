package org.technopolis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class EditInitiativeDTO {

    @JsonProperty("category")
    private String category;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("milestone")
    private String milestone;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("contractor")
    private Boolean contractor;

    @JsonProperty("imageUrl")
    private String imageUrl;
}
