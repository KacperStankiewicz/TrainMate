//package pl.edu.pja.trainmate.core.controllers;
//
//import org.springframework.web.bind.annotation.*;
//import pl.edu.pja.trainmate.core.model.User;
//import pl.edu.pja.trainmate.core.service.UserService;
//
//import java.util.List;
//
//@RestController
//public class UserController {
//    private UserService userService;
//
//    public UserController(UserSrvice userService) {
//        this.userService = userService;
//    }
//
//    @PostMapping("/add")
//    public void addUser(@RequestBody User user){
//        userService.saveUser(user);
//    }
//
//    @GetMapping("/all")
//    public List<User> getAllProducts() {
//        return userService.getUsers();
//    }
//}
//todo: do posprzatania