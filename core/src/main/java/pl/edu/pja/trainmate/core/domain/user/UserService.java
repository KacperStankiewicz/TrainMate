package pl.edu.pja.trainmate.core.domain.user;

import static pl.edu.pja.trainmate.core.common.error.MenteeErrorCode.COULD_NOT_CREATE_MENTEE;
import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;

import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.config.security.LoggedUserDataProvider;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeProjection;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeSearchCriteria;
import pl.edu.pja.trainmate.core.domain.user.querydsl.UserQueryService;

@Service
@RequiredArgsConstructor
class UserService {

    private final LoggedUserDataProvider userIdProvider;
    private final UserRepository userRepository;
    private final UserQueryService queryService;

    public Page<MenteeProjection> searchByCriteria(MenteeSearchCriteria criteria, Pageable pageable) {
        return queryService.searchMenteeByCriteria(criteria, pageable);
    }

    public ResultDto<Long> createMentee(UserRepresentation userRepresentation) {
        var mentee = buildUserEntity(userRepresentation);

        return ResultDto.ofValueOrError(userRepository.save(mentee).getId(), COULD_NOT_CREATE_MENTEE);
    }

    private UserEntity buildUserEntity(UserRepresentation userRepresentation) {
        return UserEntity.builder()
            .userId(UserId.valueOf(userRepresentation.getId()))
            .personalInfo(PersonalInfo.builder()
                .firstname(userRepresentation.getFirstName())
                .lastname(userRepresentation.getLastName())
                .email(userRepresentation.getEmail())
                .build())
            .role(TRAINED_PERSON)
            .build();
    }
}