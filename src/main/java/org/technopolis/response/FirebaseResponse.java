package org.technopolis.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FirebaseResponse {
    private final String accessToken;
    private final String username;
    private final String email;
    private final Object role;
}
