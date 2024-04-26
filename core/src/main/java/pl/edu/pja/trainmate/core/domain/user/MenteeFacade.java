package pl.edu.pja.trainmate.core.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.ResultDto;
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

    //public ResultDto<Long> create(MenteeCreateDto menteeCreateDto) {
    //    return service.createMentee(menteeCreateDto);//todo: to nie bedzie create tylko update danych usera po zalogowaniu
    //}

    public ResultDto<Long> invite(String email) {
        var user = keycloakService.createUser(email);
        return service.createMentee(user);
    }
}
