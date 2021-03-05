package org.technopolis.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.technopolis.entity.AbstractEntity;

@NoRepositoryBean
public interface BaseRepository<T extends AbstractEntity> extends JpaRepository<T, Integer> {
}
