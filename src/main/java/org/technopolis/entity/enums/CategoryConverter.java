package org.technopolis.entity.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, String> {

    @Override
    public String convertToDatabaseColumn(final Category category) {
        return category == null ? null : category.getCategory();
    }

    @Override
    public Category convertToEntityAttribute(final String name) {
        return name == null ? null : Stream.of(Category.values())
                .filter(c -> c.getCategory().equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
