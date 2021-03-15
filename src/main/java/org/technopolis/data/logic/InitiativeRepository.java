package org.technopolis.data.logic;

import org.springframework.stereotype.Repository;
import org.technopolis.data.BaseRepository;
import org.technopolis.entity.actors.ActiveUser;
import org.technopolis.entity.logic.Category;
import org.technopolis.entity.logic.Initiative;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Repository
public interface InitiativeRepository extends BaseRepository<Initiative> {

    List<Initiative> findByCategory(@Nonnull final Category category);

    Optional<Initiative> findByTitle(@Nonnull final String title);

    List<Initiative> findByActiveUser(@Nonnull final ActiveUser activeUser);
}