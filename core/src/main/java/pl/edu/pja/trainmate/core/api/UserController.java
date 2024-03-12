package pl.edu.pja.trainmate.core.api;

import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.domain.user.UserEntity;
import pl.edu.pja.trainmate.core.domain.user.UserFacade;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @PostMapping("/add")
    public UserEntity addUser(@RequestBody String name) {
        return userFacade.create(name);
    }

    @RolesAllowed("trained_person")
    @GetMapping("/all")
    public List<UserEntity> getAllUsers() {
        return userFacade.getUsers();
    }

    @GetMapping("/admin")
    @RolesAllowed("admin")
    public List<UserEntity> getAllUsersForAdmin() {
        return userFacade.getUsers();
    }
}
