package pl.edu.pja.trainmate.core.common;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class BaseJpaQueryService {

    @PersistenceContext
    private EntityManager entityManager;

    protected final JPAQueryFactory queryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    protected Predicate filterBy(Object inputCriteria, Supplier<Predicate> criteriaPredicateSupplier) {
        return isNotEmpty(inputCriteria) ? criteriaPredicateSupplier.get() : null;
    }

    protected Predicate isLike(StringPath field, String value) {
        return filterBy(value, () -> field.containsIgnoreCase(value));
    }

    protected <T extends Comparable> Predicate isBetween(ComparableExpression<T> field, T from, T to) {
        return new BooleanBuilder().and(
            filterBy(from, () -> field.goe(from))).and(filterBy(to, () -> field.loe(to)));
    }

    protected <T extends Number & Comparable<?>> Predicate isBetween(NumberExpression<T> field, NumberExpression<T> from, NumberExpression<T> to) {
        return new BooleanBuilder()
            .and(from.isNull().or(field.goe(from)))
            .and(to.isNull().or(field.loe(to)));
    }

    protected <T extends Comparable> Predicate equals(ComparableExpressionBase<T> field, T searchCriteria) {
        return filterBy(searchCriteria, () -> field.eq(searchCriteria));
    }

    protected <T> List<T> fetch(JPAQuery<T> query, Consumer<List<T>> additionalFunction) {
        List<T> list = query.fetch();
        additionalFunction.accept(list);
        return list;
    }

    private boolean isNotEmpty(Object inputCriteria) {
        if (inputCriteria instanceof Collection && areAllElementsNull((Collection<?>) inputCriteria)) {
            return false;
        }
        return !ObjectUtils.isEmpty(inputCriteria);
    }

    private boolean areAllElementsNull(Collection<?> inputCriteria) {
        return inputCriteria.stream().allMatch(Objects::isNull);
    }

    protected DateTemplate<LocalDate> asLocalDate(DateTimePath<LocalDateTime> dateTimePath) {
        return Expressions.dateTemplate(LocalDate.class, "cast({0} as LocalDate)", dateTimePath);
    }
}
