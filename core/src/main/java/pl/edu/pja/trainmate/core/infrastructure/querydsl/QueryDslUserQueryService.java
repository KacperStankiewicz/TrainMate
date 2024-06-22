package pl.edu.pja.trainmate.core.infrastructure.querydsl;

import static pl.edu.pja.trainmate.core.common.error.MenteeErrorCode.COULD_NOT_FIND_MENTEE;
import static pl.edu.pja.trainmate.core.config.security.RoleType.MENTEE;

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
import pl.edu.pja.trainmate.core.common.exception.CommonException;
import pl.edu.pja.trainmate.core.common.utils.OrderByBuilder;
import pl.edu.pja.trainmate.core.config.security.LoggedUserDataDto;
import pl.edu.pja.trainmate.core.config.security.QLoggedUserDataDto;
import pl.edu.pja.trainmate.core.domain.user.QPersonalInfo;
import pl.edu.pja.trainmate.core.domain.user.QUserEntity;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeProjection;
import pl.edu.pja.trainmate.core.domain.user.querydsl.MenteeSearchCriteria;
import pl.edu.pja.trainmate.core.domain.user.querydsl.QMenteeProjection;
import pl.edu.pja.trainmate.core.domain.user.querydsl.UserQueryService;

@Service
@RequiredArgsConstructor
class QueryDslUserQueryService extends BaseJpaQueryService implements UserQueryService {

    private static final QUserEntity user = QUserEntity.userEntity;
    private static final QPersonalInfo personalInfo = user.personalInfo;

    @Override
    public Page<MenteeProjection> searchMenteeByCriteria(MenteeSearchCriteria criteria, Pageable pageable) {
        var query = queryFactory()
            .select(buildMenteeProjection())
            .from(user)
            .where(new BooleanBuilder()
                .and(buildAgePredicate(criteria.getAgeRange()))
                .and(isLike(personalInfo.firstname, criteria.getFirstname()))
                .and(isLike(personalInfo.lastname, criteria.getLastname()))
                .and(isLike(personalInfo.email, criteria.getEmail()))
                .and(equals(personalInfo.gender, criteria.getGender()))
                .and(equals(user.role, MENTEE))
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

    public MenteeProjection getMenteeByKeycloakId(String keycloakId) {
        return Optional.ofNullable(queryFactory()
                .select(buildMenteeProjection())
                .from(user)
                .where(new BooleanBuilder()
                    .and(user.userId.keycloakId.eq(keycloakId))
                )
                .fetchOne())
            .orElseThrow(() -> new CommonException(COULD_NOT_FIND_MENTEE));
    }

    @Override
    public LoggedUserDataDto getUserByKeycloakId(String keycloakId) {
        return queryFactory()
            .select(new QLoggedUserDataDto(
                user.userId,
                user.role,
                user.personalInfo,
                user.firstLogin
            ))
            .from(user)
            .where(new BooleanBuilder()
                .and(user.userId.keycloakId.eq(keycloakId))
                .and(user.active.isTrue())
            )
            .fetchOne();
    }

    private QMenteeProjection buildMenteeProjection() {
        return new QMenteeProjection(
            personalInfo.firstname,
            personalInfo.lastname,
            personalInfo.dateOfBirth,
            personalInfo.phone,
            personalInfo.email,
            personalInfo.gender,
            personalInfo.height,
            user.userId,
            user.firstLogin,
            user.active
        );
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
