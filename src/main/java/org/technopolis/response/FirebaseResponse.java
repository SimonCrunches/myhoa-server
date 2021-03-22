package org.technopolis.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.technopolis.entity.actors.Role;

import java.util.Set;

@Data
@AllArgsConstructor
public class FirebaseResponse {

    private final String accessToken;

    private final String username;

    private final String email;

    private final Set<Role> roles;
}
