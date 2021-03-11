package org.technopolis.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.technopolis.configuration.security.model.RoleConstants;
import org.technopolis.configuration.security.jwt.JwtUtils;
import org.technopolis.configuration.security.service.UserDetailsImpl;
import org.technopolis.data.actor.RoleRepository;
import org.technopolis.entity.actors.Role;
import org.technopolis.payload.request.SignInRequest;
import org.technopolis.payload.request.SignUpRequest;
import org.technopolis.payload.response.JwtResponse;
import org.technopolis.payload.response.MessageResponse;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1.0")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthController(@Nonnull final AuthenticationManager authenticationManager,
                          @Nonnull final RoleRepository roleRepository,
                          @Nonnull final PasswordEncoder encoder,
                          @Nonnull final JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequest signInRequest) {

        /*final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getLogin(), signInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwt = jwtUtils.generateJwtToken(authentication);

        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));*/
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        /*if (credentialsRepository.existsCredentialsByLogin(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (credentialsRepository.existsCredentialsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        final Credentials credentials = new Credentials(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        final Set<String> strRoles = signUpRequest.getRole();
        final Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            final Role userRole = roleRepository.findRoleByName(RoleConstants.ROLE_GUEST)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "Expert":
                        final Role adminRole = roleRepository.findRoleByName(RoleConstants.ROLE_EXPERT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "Active":
                        final Role modRole = roleRepository.findRoleByName(RoleConstants.ROLE_ACTIVE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        final Role userRole = roleRepository.findRoleByName(RoleConstants.ROLE_GUEST)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        credentials.setRoles(roles);
        credentialsRepository.save(credentials);*/

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
