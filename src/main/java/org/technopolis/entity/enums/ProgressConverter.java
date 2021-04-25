package org.technopolis.entity.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ProgressConverter implements AttributeConverter<Progress, String> {

    @Override
    public String convertToDatabaseColumn(final Progress progress) {
        return progress == null ? null : progress.getProgress();
    }

    @Override
    public Progress convertToEntityAttribute(final String name) {
        return name == null ? null : Stream.of(Progress.values())
                .filter(c -> c.getProgress().equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
