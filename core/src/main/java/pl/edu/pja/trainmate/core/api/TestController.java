package pl.edu.pja.trainmate.core.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.model.User;
import pl.edu.pja.trainmate.core.service.UserImplementation;
import pl.edu.pja.trainmate.core.service.UserService;

import java.util.List;
//todo: formatowanie i importy
@RestController
public class TestController {
    private UserImplementation userImplementation;

    public TestController(UserImplementation userImplementation) { //todo: to do wywaleenia, mozna dac requiredArgsConstructor
        this.userImplementation = userImplementation;
    }

    @GetMapping("/dupa")
    public String dupa() {
        return "DUPSKO";
    }

    @PostMapping("/add")
    public void addUser(@RequestBody User user){
        userImplementation.saveUser(user);
    }

    @GetMapping("/all")
    public List<User> getAllProducts() {
        return userImplementation.getUsers();
    }
}
