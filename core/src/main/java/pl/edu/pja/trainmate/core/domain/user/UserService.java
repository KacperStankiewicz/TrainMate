package pl.edu.pja.trainmate.core.domain.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.UserIdProvider;
import pl.edu.pja.trainmate.core.domain.user.querydsl.UserProjection;
import pl.edu.pja.trainmate.core.domain.user.querydsl.UserQueryService;

@Service
@RequiredArgsConstructor
class UserService {

    private final UserRepository userRepository;
    private final UserIdProvider userIdProvider;
    private final UserQueryService queryService;

    public List<UserProjection> searchByCriteria() {
        return queryService.searchUserByCriteria();
    }

    public Long addUser(UserCreateDto userCreateDto) {
        return userRepository.save(buildUser(userCreateDto)).getId();
    }

    private UserEntity buildUser(UserCreateDto userCreateDto) {

        var personalInfo = PersonalInfo.builder()
            .firstname(userCreateDto.getFirstname())
            .lastname(userCreateDto.getLastname())
            .dateOfBirth(userCreateDto.getDateOfBirth())
            .phone(userCreateDto.getPhone())
            .email(userCreateDto.getEmail())
            .build();

        return UserEntity.builder()
            .personalInfo(personalInfo)
            .userId(userIdProvider.getLoggedUserId())
            .build();
    }
}