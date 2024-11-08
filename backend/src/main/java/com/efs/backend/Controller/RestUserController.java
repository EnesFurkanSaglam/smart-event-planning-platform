package com.efs.backend.Controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class RestUserController {

//    private IUserService userService;
//
//    @Autowired
//    public void setUserService(IUserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/users")
//    public ResponseEntity<List<User>> getUsers(){
//        List<User> userList = userService.getUsers();
//        return ResponseEntity.ok(userList);
//    }
//
//    @GetMapping("users/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
//        User user = userService.getUserById(id);
//        if (user != null) {
//            return ResponseEntity.ok(user);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping("/users")
//    public ResponseEntity<?> saveUser(@RequestBody User user) {
//        userService.saveUser(user);
//        return ResponseEntity.ok("User Created.");
//    }
//
//
//
//
//    @DeleteMapping(value = "/users/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
//        userService.deleteUserById(id);
//        return ResponseEntity.ok("User deleted...");
//    }
//
//    @PutMapping(value = "/users")
//    public ResponseEntity<?> updateUser(@RequestBody User user) {
//        userService.updateUser(user);
//        return ResponseEntity.ok("User updated...");
//    }


}
