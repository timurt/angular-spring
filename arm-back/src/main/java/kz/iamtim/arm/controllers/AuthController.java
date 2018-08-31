package kz.iamtim.arm.controllers;

import kz.iamtim.arm.models.User;
import kz.iamtim.arm.payload.ErrorResponse;
import kz.iamtim.arm.payload.JwtAuthenticationResponse;
import kz.iamtim.arm.payload.LoginRequest;
import kz.iamtim.arm.payload.RegisterRequest;
import kz.iamtim.arm.security.JwtTokenProvider;
import kz.iamtim.arm.security.MyUserPrincipal;
import kz.iamtim.arm.service.RoleService;
import kz.iamtim.arm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller class for authentication, registration and keeping logged in.
 *
 * @author Timur Tibeyev.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /** Authentication manager. */
    @Autowired
    AuthenticationManager authenticationManager;

    /** Service layer for db operations over roles. */
    @Autowired
    UserService userService;

    /** Service layer for db operations over users. */
    @Autowired
    RoleService roleService;

    /** Bean to encode passwords. */
    @Autowired
    PasswordEncoder passwordEncoder;

    /** Bean to generate and validate JWT token. */
    @Autowired
    JwtTokenProvider tokenProvider;

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    /**
     * Signs in specified user and returns generated JWT token.
     *
     * @param loginRequest user login and password
     *
     * @return http response
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        LOGGER.debug("Login request for user {}", loginRequest.getLogin());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtAuthenticationResponse jwt = tokenProvider.generateToken(authentication);

        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        jwt.setLogin(principal.getName());
        jwt.setRole(principal.getRole());

        return ResponseEntity.ok(jwt);
    }

    /**
     * Saves new user.
     *
     * @param registerRequest new user information
     *
     * @return http response
     */
    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        LOGGER.debug("New user registration {}", registerRequest.getLogin());

        if (userService.existsByLogin(registerRequest.getLogin())) {
            return new ResponseEntity(new ErrorResponse("User already exists"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setLogin(registerRequest.getLogin());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setName(registerRequest.getName());
        if ("owner".equalsIgnoreCase(registerRequest.getRoleType())) {
            user.setRole(roleService.findByKey("OWNER"));
        } else {
            user.setRole(roleService.findByKey("CLIENT"));
        }

        userService.register(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Refreshes token to keep user authenticated.
     *
     * @return http response
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken() {
        LOGGER.debug("Refresh token endpoint called");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationResponse jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(jwt);
    }
}
