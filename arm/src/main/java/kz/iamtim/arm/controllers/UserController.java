package kz.iamtim.arm.controllers;

import kz.iamtim.arm.dto.UserDto;
import kz.iamtim.arm.enums.RoleType;
import kz.iamtim.arm.models.User;
import kz.iamtim.arm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Documentation for {@code UserController}.
 *
 * @author Timur Tibeyev.
 */

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public Page<UserDto> list(
            @PageableDefault(size = 20) Pageable pageable) {
        final Page<User> users = userService.getByPage(pageable);

        Page<UserDto> dtoPage = users.map(user -> new UserDto(user));
        return dtoPage;
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity delete(@PathVariable Long userId) {
        if (userService.userExists(userId)) {
            userService.delete(userId);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{userId}")
    ResponseEntity<UserDto> get(@PathVariable Long userId) {
        if (userService.userExists(userId)) {
            final User res = userService.findById(userId);
            return new ResponseEntity(new UserDto(res), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    UserDto save(@RequestBody UserDto input) {
        User user = null;
        if (input.getId() != null) {
            user = userService.findById(input.getId());
        }

        if (user == null) {
            user = new User();
        }

        user.setLogin(input.getLogin());
        user.setName(input.getName());
        user.setRoleType(RoleType.valueOf(input.getRoleType()));

        return new UserDto(userService.save(user));
    }
}
