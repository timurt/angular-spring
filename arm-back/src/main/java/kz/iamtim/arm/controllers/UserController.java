package kz.iamtim.arm.controllers;

import kz.iamtim.arm.payload.UserDto;
import kz.iamtim.arm.models.Role;
import kz.iamtim.arm.models.User;
import kz.iamtim.arm.payload.ChangePasswordRequest;
import kz.iamtim.arm.payload.ErrorResponse;
import kz.iamtim.arm.payload.RegisterRequest;
import kz.iamtim.arm.service.RoleService;
import kz.iamtim.arm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

import static kz.iamtim.arm.specs.UserSpecification.filtered;

/**
 * Controller class for {@code User} class CRUD operations.
 *
 * @author Timur Tibeyev.
 */

@RestController
@RequestMapping("/api/users")
public class UserController {

    /** Service layer for db operations over users. */
    @Autowired
    UserService userService;

    /** Service layer for db operations over roles. */
    @Autowired
    RoleService roleService;

    /** Bean to encode passwords. */
    @Autowired
    PasswordEncoder passwordEncoder;

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * Returns list of the users.
     *
     * @param search search text
     * @param roleType role type
     * @param pageable {@code Pageable} contains page number and page size fields
     *
     * @return list of the users
     */
    @GetMapping
    public Page<UserDto> list(
            @PathParam("search") final String search,
            @PathParam("roleType") final String roleType,
            @PageableDefault final Pageable pageable) {
        LOGGER.debug("Get all users");

        Role role = null;
        if (roleType != null && !roleType.isEmpty()) {
            role = roleService.findByKey(roleType.toUpperCase());
        }
        final Page<User> users = userService.getByPage(filtered(search, role), pageable);

        Page<UserDto> dtoPage = users.map(user -> new UserDto(user));
        return dtoPage;
    }

    /**
     * Returns list of the owners.
     *
     * @return list of the owners
     */
    @GetMapping("/owners")
    public List<UserDto> getOwners() {
        LOGGER.debug("Get all owners");

        List<UserDto> owners = userService.getOwners().stream()
                .map(user -> new UserDto(user)).collect(Collectors.toList());
        return owners;
    }

    /**
     * Deletes corresponding user.
     *
     * @param userId id of the user
     * @return http response
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity delete(@PathVariable final Long userId) {
        LOGGER.debug("Delete user with id {}", userId);

        if (userService.userExists(userId)) {
            userService.delete(userId);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Returns required user.
     *
     * @param userId id of the user
     * @return http response
     */
    @GetMapping("/{userId}")
    ResponseEntity<UserDto> get(@PathVariable final Long userId) {
        LOGGER.debug("Get user with id {}", userId);

        if (userService.userExists(userId)) {
            final User res = userService.findById(userId);
            return new ResponseEntity(new UserDto(res), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Creates new user.
     *
     * @param input {@code RegisterRequest} object
     * @return http response
     */
    @PostMapping
    ResponseEntity save(@Valid @RequestBody final RegisterRequest input) {
        LOGGER.debug("Save new user {}", input.getLogin());

        User userByLogin = userService.findByLogin(input.getLogin());

        if (userByLogin != null) {
            return new ResponseEntity(new ErrorResponse("User already exists"),
                    HttpStatus.BAD_REQUEST);
        }

        if (input.getPassword() == null || input.getPassword().isEmpty()) {
            return new ResponseEntity(new ErrorResponse("Empty password"), HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        user.setLogin(input.getLogin());
        user.setName(input.getName());

        Role role = roleService.findByKey(input.getRoleType());

        if (role == null) {
            role = roleService.findByKey("CLIENT");
        }
        user.setRole(role);

        userService.save(user);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Updates existing user.
     *
     * @param userId id of the user to be updated
     * @param input {@code UserDto} object containing updates
     * @return http response
     */
    @PostMapping("/{userId}")
    ResponseEntity update(@PathVariable final Long userId,
                          @Valid @RequestBody final UserDto input) {
        LOGGER.debug("Update existing user with id {}", input.getId());

        if (!userService.userExists(userId)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        User user = userService.findById(userId);

        User userByLogin = userService.findByLogin(input.getLogin());

        if (userByLogin != null && !user.getId().equals(userByLogin.getId())) {
            return new ResponseEntity(new ErrorResponse("User already exists"),
                    HttpStatus.BAD_REQUEST);
        }

        user.setLogin(input.getLogin());
        user.setName(input.getName());

        Role role = roleService.findByKey(input.getRoleType());

        if (role == null) {
            role = roleService.findByKey("CLIENT");
        }
        user.setRole(role);

        userService.save(user);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Method to change password.
     *
     * @param input {@code ChangePasswordRequest} object containing user id and new password
     * @return http response
     */
    @PostMapping("/change-password")
    ResponseEntity changePassword(@Valid @RequestBody final ChangePasswordRequest input) {
        LOGGER.debug("Change password request for user with id {}", input.getId());

        if (userService.userExists(input.getId())) {
            User res = userService.findById(input.getId());
            res.setPassword(passwordEncoder.encode(input.getPassword()));
            userService.save(res);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
