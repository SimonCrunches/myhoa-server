package org.technopolis.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.technopolis.configuration.security.SecurityConfig;
import org.technopolis.data.actor.ActiveUserRepository;
import org.technopolis.data.actor.RoleRepository;
import org.technopolis.entity.actors.ActiveUser;
import org.technopolis.entity.actors.Role;
import org.technopolis.service.UserService;
import org.technopolis.service.shared.RegisterUserInit;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final ActiveUserRepository userDao;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(@Nonnull final ActiveUserRepository userDao,
                           @Nonnull final RoleRepository roleRepository) {
        this.userDao = userDao;
        this.roleRepository = roleRepository;
    }

    public UserDetails loadUserByUsername(@Nonnull final String username) throws UsernameNotFoundException {
        final ActiveUser userDetails = userDao.findByUsername(username).orElse(null);
        if (userDetails == null)
            return null;

        final Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (final GrantedAuthority role : userDetails.getAuthorities()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        return new User(userDetails.getUsername(), userDetails.getPassword(), grantedAuthorities);
    }

    @Override
    @Transactional
    @Secured(value = SecurityConfig.Roles.ROLE_ACTIVE_USER)
    public ActiveUser registerUser(@Nonnull final RegisterUserInit init) {

        final ActiveUser userLoaded = userDao.findByUsername(init.getUserName()).orElse(null);

        if (userLoaded == null) {
            ActiveUser userEntity = ActiveUser.builder()
                    .username(init.getUserName())
                    .email(init.getEmail())
                    .firebaseToken(init.getToken())
                    .pictureUrl(init.getPicture())
                    .firstName(init.getUserName())
                    .lastName(init.getUserName())
                    .authorities(getActiveUserRoles())
                    .password(UUID.randomUUID().toString()).build();
            userDao.save(userEntity);
            log.info("registerUser -> user created");
            return userDao.findByUsername(init.getUserName()).get();
        } else {
            log.info("registerUser -> user exists");
            return userLoaded;
        }
    }

    @Override
    public ActiveUser getUser(@Nonnull final Integer id) {
        return userDao.findById(id).orElse(null);
    }


    @PostConstruct
    public void init() {
    }

    private Set<Role> getExpertRoles() {
        return Collections.singleton(getRole(SecurityConfig.Roles.ROLE_EXPERT));
    }

    private Set<Role> getActiveUserRoles() {
        return Collections.singleton(getRole(SecurityConfig.Roles.ROLE_ACTIVE_USER));
    }

    private Role getRole(@Nonnull final String authority) {
        final Role adminRole = roleRepository.findRoleByAuthority(authority).orElse(null);
        return Objects.requireNonNullElseGet(adminRole, () -> {
            final Role newRole = new Role();
            newRole.setAuthority(authority);
            roleRepository.save(newRole);
            return roleRepository.findRoleByAuthority(authority).get();
        });
    }

}
