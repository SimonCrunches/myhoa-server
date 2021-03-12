package org.technopolis.entity.actors;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.technopolis.entity.AbstractEntity;
import org.technopolis.entity.logic.Initiative;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class User extends AbstractEntity {

    @Column(name = "first_name", nullable = false)
    protected String firstName;

    @Column(name = "last_name", nullable = false)
    protected String lastName;

    @Column(name = "token", nullable = false)
    protected String token;

    public User(@Nonnull final String firstName,
                @Nonnull final String lastName,
                @Nonnull final String token) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    protected Set<Initiative> createdInitiatives = new HashSet<>();

}
