package pl.edu.pja.trainmate.core.domain.user;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static pl.edu.pja.trainmate.core.common.error.MenteeErrorCode.COULD_NOT_FIND_MENTEE;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import pl.edu.pja.trainmate.core.common.BaseRepository;
import pl.edu.pja.trainmate.core.common.exception.CommonException;

@Repository
public interface UserRepository extends BaseRepository<UserEntity> {

    default UserEntity getUserByKeycloakId(String keycloakId) {
        return Optional.ofNullable(findUserEntityByUserId_KeycloakIdAndActive(keycloakId, true))
            .orElseThrow(
                () -> new CommonException(COULD_NOT_FIND_MENTEE.getCode(), COULD_NOT_FIND_MENTEE.format(keycloakId), BAD_REQUEST)
            );
    }

    default UserEntity getActiveOrInactiveUserByKeycloakId(String keycloakId) {
        return Optional.ofNullable(findUserEntityByUserId_KeycloakId(keycloakId))
            .orElseThrow(
                () -> new CommonException(COULD_NOT_FIND_MENTEE.getCode(), COULD_NOT_FIND_MENTEE.format(keycloakId), BAD_REQUEST)
            );
    }

    default boolean userExistsByEmailAddress(String email) {
        return existsByPersonalInfo_Email(email);
    }

    boolean existsByPersonalInfo_Email(String email);

    UserEntity findUserEntityByUserId_KeycloakIdAndActive(String keycloakId, boolean active);

    UserEntity findUserEntityByUserId_KeycloakId(String keycloakId);
}
