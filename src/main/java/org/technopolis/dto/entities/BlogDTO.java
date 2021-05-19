package org.technopolis.dto.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.technopolis.entity.logic.Blog;
import org.technopolis.utils.CommonUtils;

import javax.annotation.Nonnull;

@Data
@NoArgsConstructor
public class BlogDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("creationDate")
    private String creationDate;

    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonProperty("initiative")
    private Integer initiative;

    public BlogDTO(@Nonnull final Blog blog) {
        this.id = blog.getId();
        this.description = blog.getDescription();
        this.title = blog.getTitle();
        this.creationDate = blog.getCreationDate().format(CommonUtils.LOCALDATETIME);
        this.imageUrl = blog.getImageUrl();
        this.initiative = blog.getInitiative().getId();
    }
}
