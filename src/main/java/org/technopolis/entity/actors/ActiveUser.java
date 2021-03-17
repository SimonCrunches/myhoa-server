package org.technopolis.entity.actors;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.technopolis.entity.AbstractEntity;
import org.technopolis.entity.logic.Initiative;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "active_user")
public class ActiveUser extends AbstractEntity implements UserDetails {

    @Column(name = "first_name")
    protected String firstName;

    @Column(name = "last_name")
    protected String lastName;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    @Column(name = "firebaseToken", nullable = false)
    protected String firebaseToken;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "active_user_roles",
            joinColumns = @JoinColumn(name = "active_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> authorities = new HashSet<>();

    public ActiveUser(@Nonnull final String firstName,
                      @Nonnull final String lastName,
                      @Nonnull final String firebaseToken) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.firebaseToken = firebaseToken;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "activeUser", fetch = FetchType.LAZY, orphanRemoval = true)
    protected Set<Initiative> createdInitiatives = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
