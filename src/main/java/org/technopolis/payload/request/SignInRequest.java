package org.technopolis.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignInRequest {
    @NotBlank
    private String login;

    @NotBlank
    private String password;

}
