package org.technopolis.dto.logic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditUserDTO {

    @JsonProperty(value = "first_name")
    protected String firstName;

    @JsonProperty(value = "last_name")
    protected String lastName;

    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "email")
    private String email;
}
