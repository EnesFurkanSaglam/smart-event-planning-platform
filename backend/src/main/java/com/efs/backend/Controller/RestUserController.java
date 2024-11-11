package com.efs.backend.Controller;


import com.efs.backend.JWT.JWTService;
import com.efs.backend.Model.User;
import com.efs.backend.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class RestUserController {

    private IUserService userService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

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
        // Token'ı "Bearer <token>" şeklinde gönderdiğimiz için, öncelikle "Bearer " kısmını çıkarıyoruz.
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Token'dan kullanıcı adı alınır
        String username = jwtService.getUsernameByToken(token);

        // Kullanıcı adı ile veritabanından kullanıcıyı alıyoruz
        User user = userService.getUserByUsername(username);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PostMapping("/users")
//    public ResponseEntity<?> saveUser(@RequestBody User user) {
//        userService.saveUser(user);
//        return ResponseEntity.ok("User Created.");
//    }




//    @DeleteMapping(value = "/users/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
//        userService.deleteUserById(id);
//        return ResponseEntity.ok("User deleted...");
//    }

//    @PutMapping(value = "/users")
//    public ResponseEntity<?> updateUser(@RequestBody User user) {
//        userService.updateUser(user);
//        return ResponseEntity.ok("User updated...");
//    }


}
