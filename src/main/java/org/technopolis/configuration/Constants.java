package org.technopolis.configuration;

import javax.annotation.Nonnull;

public final class Constants {

    public enum Roles {
        ROLE_GUEST("Guest"),
        ROLE_ACTIVE_USER("Active User"),
        ROLE_EXPERT("Expert");

        private final String role;

        Roles(@Nonnull final String role) {
            this.role = role;
        }

        public String getRole() {
            return role;
        }
    }
}
