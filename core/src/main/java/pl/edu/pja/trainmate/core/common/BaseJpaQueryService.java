package pl.edu.pja.trainmate.core.common;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang3.ObjectUtils;
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

    protected <T> Page<T> fetchPage(JPAQuery<T> query, Pageable pageable) {
        return fetchPage(query, pageable, ignore -> {
        });
    }

    protected <T> Page<T> fetchPage(JPAQuery<T> query, Pageable pageable, Consumer<List<T>> additionalFunction) {
        long total = query.fetchCount();
        List<T> list = List.of();
        if (total > 0) {
            list = query
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
            additionalFunction.accept(list);
        }
        return new PageImpl<>(list, pageable, total);
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

    protected <T extends Comparable> Predicate equals(ComparableExpressionBase<T> field, T searchCriteria) {
        return filterBy(searchCriteria, () -> field.eq(searchCriteria));
    }

    protected <T extends Comparable> Predicate isMemberOf(ComparableExpression<T> field, Collection<T> containedBy) {
        return filterBy(containedBy, () -> field.in(containedBy));
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
}
