package org.technopolis.entity.enums;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

public enum Progress {
    ITEM1("item1"),
    ITEM2("item2"),
    ITEM3("item3"),
    ITEM4("item4"),
    ITEM5("item5");

    private final String progress;

    Progress(@Nonnull final String progress) {
        this.progress = progress;
    }

    public String getProgress() {
        return progress;
    }

    public static Progress convertToEntityAttribute(@Nonnull final String name) {
        return Stream.of(Progress.values())
                .filter(c -> c.getProgress().equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
