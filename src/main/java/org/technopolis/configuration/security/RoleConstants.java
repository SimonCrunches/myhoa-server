package org.technopolis.configuration.security;

import javax.annotation.Nonnull;

public enum RoleConstants {
    ROLE_GUEST("ROLE_GUEST"),
    ROLE_ACTIVE_USER("ROLE_ACTIVE_USER"),
    ROLE_EXPERT("ROLE_EXPERT"),
    ROLE_SUPER("ROLE_SUPER");

    private final String role;

    RoleConstants(@Nonnull final String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}