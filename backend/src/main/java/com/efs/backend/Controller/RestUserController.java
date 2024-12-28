package com.efs.backend.Controller;


import com.efs.backend.DTO.DTOUser;
import com.efs.backend.JWT.JWTService;
import com.efs.backend.Model.User;
import com.efs.backend.Service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.efs.backend.Controller.RootEntity.ok;

@RestController
@RequestMapping("/user")
public class RestUserController {

    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    private JWTService jwtService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        List<User> userList = userService.getUsers();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/me")
    public ResponseEntity<User> getUserFromToken(@RequestHeader("Authorization") String token) {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String username = jwtService.getUsernameByToken(token);

        User user = userService.getUserByUsername(username);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok("User Created.");
    }


    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted...");
    }

    @PatchMapping(value = "/users")
    public RootEntity<User> updateUser(@RequestBody DTOUser dtoUser) {

        User dbUser = userService.getUserByUsername(dtoUser.getUsername());

        if (dtoUser.getPassword() == null || dtoUser.getPassword().isEmpty()) {
            dtoUser.setPassword(dbUser.getPassword());
        }else {
            String encryptedPassword = passwordEncoder.encode(dtoUser.getPassword());
            dtoUser.setPassword(encryptedPassword);
        }

        BeanUtils.copyProperties(dtoUser, dbUser);
        userService.updateUser(dbUser);
        return ok(userService.updateUser(dbUser));
    }


}
