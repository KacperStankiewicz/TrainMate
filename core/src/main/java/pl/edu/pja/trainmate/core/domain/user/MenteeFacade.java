package pl.edu.pja.trainmate.core.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.ResultDto;
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

    public ResultDto<Long> invite(String email) {
        var user = keycloakService.createUser(email);
        return service.createMentee(user);
    }

    public void updatePersonalData(MenteeUpdateDto menteeUpdateDto) {
        service.updatePersonalData(menteeUpdateDto);
    }

    public void deleteAccount(String userId) {
        service.changeAccountActivity(userId, false);
    }

    public void activateAccount(String userId) {
        service.changeAccountActivity(userId, true);
    }
}
