package org.technopolis.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.technopolis.configuration.security.SecurityService;
import org.technopolis.configuration.security.jwt.JwtUtils;
import org.technopolis.configuration.security.model.SecurityConstants;
import org.technopolis.configuration.security.model.UserDTO;
import org.technopolis.data.actor.UserRepository;
import org.technopolis.entity.actors.User;
import org.technopolis.payload.response.JwtResponse;
import org.technopolis.payload.response.MessageResponse;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1.0")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final FirebaseAuth firebaseAuth;
    private final JwtUtils jwtUtils;
    private final SecurityService securityService;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(@Nonnull final AuthenticationManager authenticationManager,
                          @Nonnull final FirebaseAuth firebaseAuth,
                          @Nonnull final JwtUtils jwtUtils,
                          @Nonnull final SecurityService securityService,
                          @Nonnull final UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.firebaseAuth = firebaseAuth;
        this.jwtUtils = jwtUtils;
        this.securityService = securityService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestHeader(value = SecurityConstants.HEADER_FIREBASE) String idToken) throws Exception {
        final FirebaseToken decodedToken = firebaseAuth.verifyIdTokenAsync(idToken).get();

        final Optional<User> existedUser = userRepository.findByFirebaseToken(decodedToken.getUid());
        if (existedUser.isPresent()) {
            return ResponseEntity.ok(new MessageResponse("User already exists",
                    existedUser.get().getJwtToken()));
        }

        final UserDTO userDetails = securityService.getUser(SecurityContextHolder.getContext());
        final List<GrantedAuthority> authorities = new ArrayList<>();
        decodedToken.getClaims().forEach((k, v) -> authorities.add(new SimpleGrantedAuthority(k)));

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails, null, authorities));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwt = jwtUtils.generateJwtToken(authentication);

        final User user = new User(userDetails.getName(),
                null,
                userDetails.getUid(),
                jwt);

        userRepository.save(user);
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getName(),
                userDetails.getEmail(),
                authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())));
    }
}
