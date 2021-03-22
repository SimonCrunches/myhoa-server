package org.technopolis.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
public class FirebaseResponse {
    private final String accessToken;
    private final String username;
    private final String email;
    private final Object role;
}
