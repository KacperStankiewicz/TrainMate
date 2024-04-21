package pl.edu.pja.trainmate.core.domain.user.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pja.trainmate.core.config.security.LoggedUserDataDto;

public interface UserQueryService {

    Page<MenteeProjection> searchMenteeByCriteria(MenteeSearchCriteria criteria, Pageable pageable);

    LoggedUserDataDto getUserByKeycloakId(String keycloakId);
}
