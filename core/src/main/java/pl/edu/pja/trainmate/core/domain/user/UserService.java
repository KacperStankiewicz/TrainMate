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
import pl.edu.pja.trainmate.core.domain.user.dto.MenteeUpdateDto;
import pl.edu.pja.trainmate.core.domain.user.keycloak.KeycloakService;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeProjection;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeSearchCriteria;
import pl.edu.pja.trainmate.core.domain.user.querydsl.UserQueryService;

@Service
@RequiredArgsConstructor
class UserService {

    private final LoggedUserDataProvider userProvider;
    private final UserRepository userRepository;
    private final UserQueryService queryService;
    private final KeycloakService keycloakService;

    public Page<MenteeProjection> searchByCriteria(MenteeSearchCriteria criteria, Pageable pageable) {
        return queryService.searchMenteeByCriteria(criteria, pageable);
    }

    public ResultDto<Long> createMentee(UserRepresentation userRepresentation, boolean activate) {
        var mentee = buildUserEntity(userRepresentation, activate);

        return ResultDto.ofValueOrError(userRepository.save(mentee).getId(), COULD_NOT_CREATE_MENTEE);
    }

    public void updatePersonalData(MenteeUpdateDto menteeUpdateDto) {
        var keycloakId = userProvider.getUserDetails().getUserId().getKeycloakId();

        var mentee = getUserByKeycloakId(keycloakId);

        if (!mentee.getPersonalInfo().getEmail().equals(menteeUpdateDto.getEmail())) {
            keycloakService.updateUserEmail(menteeUpdateDto.getEmail(), keycloakId);
        }

        mentee.updatePersonalInfo(PersonalInfo.builder()
            .firstname(menteeUpdateDto.getFirstname())
            .lastname(menteeUpdateDto.getLastname())
            .phone(menteeUpdateDto.getPhone())
            .dateOfBirth(menteeUpdateDto.getDateOfBirth())
            .email(menteeUpdateDto.getEmail())
            .gender(menteeUpdateDto.getGender())
            .height(menteeUpdateDto.getHeight())
            .build());

        userRepository.saveAndFlush(mentee);
    }

    public void changeAccountActivity(String userId, boolean active) {
        var mentee = userRepository.getUserByKeycloakId(userId);
        mentee.setActive(active);

        keycloakService.enableOrDisableAccount(mentee.getUserId().getKeycloakId(), active);
    }

    private UserEntity getUserByKeycloakId(String keycloakId) {
        return userRepository.getUserByKeycloakId(keycloakId);
    }

    private UserEntity buildUserEntity(UserRepresentation userRepresentation, boolean activate) {
        return UserEntity.builder()
            .userId(UserId.valueOf(userRepresentation.getId()))
            .active(activate)
            .personalInfo(PersonalInfo.builder()
                .firstname(userRepresentation.getFirstName())
                .lastname(userRepresentation.getLastName())
                .email(userRepresentation.getEmail())
                .build())
            .role(TRAINED_PERSON)
            .build();
    }
}