package pl.edu.pja.trainmate.core.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.domain.user.UserEntity;
import pl.edu.pja.trainmate.core.domain.user.UserFacade;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserFacade userFacade;

    @PostMapping("/add")
    public UserEntity addUser(@RequestBody String name) {
        return userFacade.create(name);
    }

    @GetMapping("/all")
    public List<UserEntity> getAllUsers() {
        return userFacade.getUsers();
    }
}
