package pl.edu.pja.trainmate.core.infrastructure.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BaseJpaQueryService;
import pl.edu.pja.trainmate.core.common.NumberRange;
import pl.edu.pja.trainmate.core.common.OrderByBuilder;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeSearchCriteria;
import pl.edu.pja.trainmate.core.domain.user.QPersonalInfo;
import pl.edu.pja.trainmate.core.domain.user.QUserEntity;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeProjection;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeQueryService;
import pl.edu.pja.trainmate.core.domain.user.querydsl.QMenteeProjection;

@Service
@RequiredArgsConstructor
class QueryDslMenteeQueryService extends BaseJpaQueryService implements MenteeQueryService {

    private static final QUserEntity user = QUserEntity.userEntity;
    private static final QPersonalInfo personalInfo = user.personalInfo;

    @Override
    public Page<MenteeProjection> searchMenteeByCriteria(MenteeSearchCriteria criteria, Pageable pageable) {
        var query = queryFactory()
            .select(new QMenteeProjection(
                personalInfo.firstname,
                personalInfo.lastname,
                personalInfo.dateOfBirth,
                personalInfo.phone,
                personalInfo.email
            ))
            .from(user)
            .where(new BooleanBuilder()
                .and(buildAgePredicate(criteria.getAgeRange()))
                .and(isLike(personalInfo.firstname, criteria.getFirstname()))
                .and(isLike(personalInfo.lastname, criteria.getLastname()))
                .and(isLike(personalInfo.email, criteria.getEmail()))
            )
            .orderBy(OrderByBuilder.with(pageable.getSort())
                .whenPropertyIs("age").thenSortBy(personalInfo.dateOfBirth)
                .and().whenPropertyIs("firstname").thenSortBy(personalInfo.firstname)
                .and().whenPropertyIs("lastname").thenSortBy(personalInfo.firstname)
                .and().whenPropertyIs("email").thenSortBy(personalInfo.firstname)
                .and().defaultSortBy(personalInfo.lastname.asc())
                .build());

        return fetchPage(query, pageable);
    }

    private Predicate buildAgePredicate(NumberRange range) {
        if (range == null) {
            return null;
        }

        var youngestBirthDate = Optional.ofNullable(range.getFrom())
            .map(age -> LocalDate.now().minusYears(age))
            .orElse(null);
        var oldestBirthDate = Optional.ofNullable(range.getTo())
            .map(age -> LocalDate.now().minusYears(age))
            .orElse(null);

        return isBetween(personalInfo.dateOfBirth, oldestBirthDate, youngestBirthDate);
    }
}
