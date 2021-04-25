package org.technopolis.entity.enums;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

public enum Category {
    REPAIR("repair"),
    RESTORATION("restoration"),
    CITY_BEAUTIFICATION("city_beautification"),
    GREEN_BEAUTIFICATION("green_beautification"),
    OTHER("other");

    private final String category;

    Category(@Nonnull final String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public static Category convertToEntityAttribute(@Nonnull final String name) {
        return Stream.of(Category.values())
                .filter(c -> c.getCategory().equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
