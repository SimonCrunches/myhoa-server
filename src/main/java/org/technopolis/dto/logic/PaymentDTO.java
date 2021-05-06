package org.technopolis.dto.logic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentDTO {

    @JsonProperty(value = "initiativeId")
    private Integer initiativeId;

    @JsonProperty(value = "userId")
    private Integer userId;

    @JsonProperty(value = "payment")
    private Integer payment;

}
