package org.technopolis.entity.actors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.technopolis.configuration.security.model.RoleConstants;
import org.technopolis.entity.AbstractEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "role")
public class Role extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleConstants name;

}
