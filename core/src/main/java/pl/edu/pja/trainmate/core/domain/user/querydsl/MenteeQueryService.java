package pl.edu.pja.trainmate.core.domain.user.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenteeQueryService {

    Page<MenteeProjection> searchMenteeByCriteria(MenteeSearchCriteria criteria, Pageable pageable);
}
