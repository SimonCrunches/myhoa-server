package org.technopolis.entity.actors;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.technopolis.entity.AbstractEntity;
import org.technopolis.entity.logic.Initiative;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity {

    @Column(name = "first_name", nullable = false)
    protected String firstName;

    @Column(name = "last_name", nullable = false)
    protected String lastName;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "credentials_id")
    protected Credentials credentials;

    @JsonBackReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    protected Set<Initiative> createdInitiatives = new HashSet<>();

}
