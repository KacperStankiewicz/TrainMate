package pl.edu.pja.trainmate.core.api;

import static pl.edu.pja.trainmate.core.config.security.RoleType.ADMIN;
import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pja.trainmate.core.annotation.HasRole;
import pl.edu.pja.trainmate.core.domain.user.UserCreateDto;
import pl.edu.pja.trainmate.core.domain.user.UserFacade;
import pl.edu.pja.trainmate.core.domain.user.querydsl.UserProjection;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @PostMapping("/add")
    public Long addUser(@RequestBody UserCreateDto userCreateDto) {
        return userFacade.create(userCreateDto);
    }

    @HasRole(roleType = TRAINED_PERSON)
    @GetMapping("/all")
    public List<UserProjection> getAllUsers() {
        return userFacade.getUsers();
    }

    @HasRole(roleType = ADMIN)
    @GetMapping("/admin")
    public List<UserProjection> getAllUsersForAdmin() {
        return userFacade.getUsers();
    }

    //    @HasRole(roleType = ADMIN)
    @GetMapping("/search")
    public List<UserProjection> dupa() {
        return userFacade.getUsers();
    }
}
