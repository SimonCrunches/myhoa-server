package org.technopolis.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String login;
    private String email;
    private List<String> roles;

    public JwtResponse(@Nonnull final String token) {
        this.token = token;
    }
}
