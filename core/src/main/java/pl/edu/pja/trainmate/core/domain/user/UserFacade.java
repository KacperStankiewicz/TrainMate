package pl.edu.pja.trainmate.core.domain.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserFacade {

    private final UserService service;

    public UserEntity create(String name) {
        return service.addUser(name);
    }

    public List<UserEntity> getUsers() {
        return service.getUsers();
    }
}
