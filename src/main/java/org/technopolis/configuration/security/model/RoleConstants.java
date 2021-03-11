package org.technopolis.configuration.security.model;

import javax.annotation.Nonnull;

public enum RoleConstants {
    ROLE_ACTIVE_USER("ROLE_ACTIVE_USER"),
    ROLE_EXPERT("ROLE_EXPERT");

    private final String role;

    RoleConstants(@Nonnull final String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}