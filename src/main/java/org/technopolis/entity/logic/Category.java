package org.technopolis.entity.logic;

import javax.annotation.Nonnull;

public enum Category {
    REPAIR("repair"),
    RESTORATION("restoration"),
    INFRASTRUCTURE("infrastructure"),
    OTHER("other");

    private final String category;

    Category(@Nonnull final String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
