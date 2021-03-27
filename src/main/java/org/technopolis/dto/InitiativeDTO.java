package org.technopolis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class InitiativeDTO {

    @NotBlank
    @JsonProperty("category")
    private String category;

    @NotBlank
    @JsonProperty("info")
    private String title;

    @JsonProperty("description")
    private String description;

    @NotNull
    @JsonProperty("latitude")
    private Double latitude;

    @NotBlank
    @JsonProperty("longitude")
    private Double longitude;

    @NotNull
    @JsonProperty("milestone")
    private String milestone;

    @JsonProperty("price")
    private Integer price;

    @NotNull
    @JsonProperty("contractor")
    private Integer contractor;

}
