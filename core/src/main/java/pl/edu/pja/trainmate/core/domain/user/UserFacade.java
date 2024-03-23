package pl.edu.pja.trainmate.core.domain.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.domain.user.querydsl.UserProjection;

@RequiredArgsConstructor
@Service
public class UserFacade {

    private final UserService service;

    public Long create(UserCreateDto userCreateDto) {
        return service.addUser(userCreateDto);
    }

    public List<UserProjection> getUsers() {
        return service.searchByCriteria();
    }
}
