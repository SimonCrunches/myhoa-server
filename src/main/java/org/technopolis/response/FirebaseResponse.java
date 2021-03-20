package org.technopolis.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirebaseResponse {
    private String accessToken;
    private String username;
    private String email;
    private Collection<? extends GrantedAuthority> roles;
}
