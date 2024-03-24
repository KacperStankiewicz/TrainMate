package pl.edu.pja.trainmate.core.infrastructure.querydsl;

import com.querydsl.core.BooleanBuilder;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.common.BaseJpaQueryService;
import pl.edu.pja.trainmate.core.domain.user.QUserEntity;
import pl.edu.pja.trainmate.core.domain.user.querydsl.QUserProjection;
import pl.edu.pja.trainmate.core.domain.user.querydsl.UserProjection;
import pl.edu.pja.trainmate.core.domain.user.querydsl.UserQueryService;

@Service
@RequiredArgsConstructor
public class QueryDslUserQueryService extends BaseJpaQueryService implements UserQueryService {

    private static final QUserEntity user = QUserEntity.userEntity;

    @Override
    public List<UserProjection> searchUserByCriteria() {
        return queryFactory()
            .select(new QUserProjection(
                user.personalInfo.firstname,
                user.personalInfo.lastname,
                user.personalInfo.dateOfBirth,
                user.personalInfo.phone,
                user.personalInfo.email
            ))
            .from(user)
            .where(new BooleanBuilder()
                .and(isBetween(user.personalInfo.dateOfBirth, LocalDate.of(2001, 1, 1), LocalDate.of(2002, 1, 1))))
            .fetch();
    }
}
