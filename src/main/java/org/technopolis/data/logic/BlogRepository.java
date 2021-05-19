package org.technopolis.data.logic;

import org.technopolis.data.BaseRepository;
import org.technopolis.entity.logic.Blog;
import org.technopolis.entity.logic.Initiative;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface BlogRepository extends BaseRepository<Blog> {

    List<Blog> findByInitiative(@Nonnull final Initiative initiative);

    Optional<Blog> findByInitiativeAndTitle(@Nonnull final Initiative initiative,
                                            @Nonnull final String title);
}
