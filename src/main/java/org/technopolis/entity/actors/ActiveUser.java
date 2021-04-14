package org.technopolis.entity.actors;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.technopolis.entity.logic.FavouriteInitiative;
import org.technopolis.entity.logic.Initiative;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "active_user")
public class ActiveUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "first_name")
    protected String firstName;

    @Column(name = "last_name")
    protected String lastName;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    @Email(regexp=".*@.*\\..*")
    private String email;

    @Column(name = "firebaseToken", nullable = false)
    private String firebaseToken;

    @Column(name = "pictureUrl", nullable = false)
    private String pictureUrl;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "active_user_roles",
            joinColumns = @JoinColumn(name = "active_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> authorities = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "activeUser", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Initiative> createdInitiatives = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "activeUser", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<FavouriteInitiative> favouriteInitiatives = new HashSet<>();

    @Override
    public Set<Role> getAuthorities() {
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
