package pl.edu.pja.trainmate.core.domain.user;

import static java.lang.Boolean.TRUE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static pl.edu.pja.trainmate.core.common.error.MenteeErrorCode.MENTEE_WITH_PROVIDED_EMAIL_ALREADY_EXISTS;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.ResultDto;
import pl.edu.pja.trainmate.core.common.exception.CommonException;
import pl.edu.pja.trainmate.core.domain.user.dto.MenteeUpdateDto;
import pl.edu.pja.trainmate.core.domain.user.keycloak.KeycloakService;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeProjection;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeSearchCriteria;

@RequiredArgsConstructor
@Service
public class MenteeFacade {

    private final UserService service;
    private final KeycloakService keycloakService;

    public Page<MenteeProjection> search(MenteeSearchCriteria criteria, Pageable pageable) {
        return service.searchByCriteria(criteria, pageable);
    }

    public MenteeProjection getMenteeByKeycloakId(String keycloakId) {
        return service.getMenteeByKeycloakId(keycloakId);
    }

    public ResultDto<Long> invite(String email, boolean activate) {
        if (TRUE.equals(service.checkIfUserExistsByEmail(email))) {
            throw new CommonException(
                MENTEE_WITH_PROVIDED_EMAIL_ALREADY_EXISTS.getCode(),
                MENTEE_WITH_PROVIDED_EMAIL_ALREADY_EXISTS.format(email),
                BAD_REQUEST
            );
        }

        var user = keycloakService.createUser(email);
        return service.createMentee(user, activate);
    }

    public void updatePersonalData(MenteeUpdateDto menteeUpdateDto) {
        service.updatePersonalData(menteeUpdateDto);
    }

    public void deactivateAccount(String userId) {
        service.changeAccountActivity(userId, false);
    }

    public void activateAccount(String userId) {
        service.changeAccountActivity(userId, true);
    }

    public void unsetFirstLoginFlag() {
        service.unsetFirstLoginFlag();
    }
}
