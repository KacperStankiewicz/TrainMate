package pl.edu.pja.trainmate.core.domain.user;

import static pl.edu.pja.trainmate.core.common.error.MenteeErrorCode.COULD_NOT_CREATE_MENTEE;
import static pl.edu.pja.trainmate.core.common.error.MenteeErrorCode.EMAIL_MUST_NOT_BE_NULL;
import static pl.edu.pja.trainmate.core.common.error.MenteeErrorCode.FIRSTNAME_MUST_NOT_BE_NULL;
import static pl.edu.pja.trainmate.core.common.error.MenteeErrorCode.GENDER_MUST_NOT_BE_NULL;
import static pl.edu.pja.trainmate.core.common.error.MenteeErrorCode.INVALID_DATE_OF_BIRTH;
import static pl.edu.pja.trainmate.core.common.error.MenteeErrorCode.INVALID_HEIGHT;
import static pl.edu.pja.trainmate.core.common.error.MenteeErrorCode.INVALID_PHONE_NUMBER;
import static pl.edu.pja.trainmate.core.common.error.MenteeErrorCode.LASTNAME_MUST_NOT_BE_NULL;
import static pl.edu.pja.trainmate.core.config.security.RoleType.MENTEE;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.common.exception.CommonException;
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

    public MenteeProjection getMenteeByKeycloakId(String keycloakId) {
        return queryService.getMenteeByKeycloakId(keycloakId);
    }

    public ResultDto<Long> createMentee(UserRepresentation userRepresentation, boolean activate) {
        var mentee = buildUserEntity(userRepresentation, activate);

        return ResultDto.ofValueOrError(userRepository.save(mentee).getId(), COULD_NOT_CREATE_MENTEE);
    }

    public void updatePersonalData(MenteeUpdateDto menteeUpdateDto) {
        validateDto(menteeUpdateDto);
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

        userRepository.save(mentee);
    }

    public void changeAccountActivity(String userId, boolean active) {
        var mentee = userRepository.getActiveOrInactiveUserByKeycloakId(userId);
        mentee.setActive(active);

        keycloakService.enableOrDisableAccount(mentee.getUserId().getKeycloakId(), active);

        userRepository.save(mentee);
    }

    public void unsetFirstLoginFlag() {
        var userId = userProvider.getLoggedUserId();
        var user = getUserByKeycloakId(userId.getKeycloakId());
        user.unsetFirstLoginFlag();

        userRepository.save(user);
    }

    private UserEntity getUserByKeycloakId(String keycloakId) {
        return userRepository.getUserByKeycloakId(keycloakId);
    }

    public boolean checkIfUserExistsByEmail(String email) {
        return userRepository.userExistsByEmailAddress(email);

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
            .role(MENTEE)
            .build();
    }

    private void validateDto(MenteeUpdateDto dto) {
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new CommonException(EMAIL_MUST_NOT_BE_NULL);
        }

        if (dto.getFirstname() == null || dto.getFirstname().isEmpty()) {
            throw new CommonException(FIRSTNAME_MUST_NOT_BE_NULL);
        }

        if (dto.getLastname() == null || dto.getLastname().isEmpty()) {
            throw new CommonException(LASTNAME_MUST_NOT_BE_NULL);
        }

        if (dto.getDateOfBirth() == null || dto.getDateOfBirth().isEqual(LocalDate.now()) || dto.getDateOfBirth().isAfter(LocalDate.now())) {
            throw new CommonException(INVALID_DATE_OF_BIRTH);
        }

        if (dto.getPhone() == null || dto.getPhone().length() < 9 || dto.getPhone().length() > 23) {
            throw new CommonException(INVALID_PHONE_NUMBER);
        }

        if (dto.getGender() == null) {
            throw new CommonException(GENDER_MUST_NOT_BE_NULL);
        }

        if (dto.getHeight() == null || dto.getHeight() < 100 || dto.getHeight() > 250) {
            throw new CommonException(INVALID_HEIGHT);
        }
    }
}