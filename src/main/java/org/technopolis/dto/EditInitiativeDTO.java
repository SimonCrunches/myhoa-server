package org.technopolis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
