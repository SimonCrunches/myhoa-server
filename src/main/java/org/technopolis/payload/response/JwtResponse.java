package org.technopolis.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private final String type = "Bearer";
    private Integer id;
    private String login;
    private String email;
    private List<String> roles;
}
