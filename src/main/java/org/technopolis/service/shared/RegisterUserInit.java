package org.technopolis.service.shared;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserInit {
    private final String userName;
    private final String email;
    private final String picture;
    private final String token;
}
