package org.technopolis.configuration.security.model;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 4408418647685225829L;
    private final String uid;
    private final String name;
    private final String email;
    private final boolean isEmailVerified;
}
