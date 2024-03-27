package pl.edu.pja.trainmate.core.common;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class OrderByBuilder {

    private final Sort sort;
    private final Map<String, SortByBuilder> sortCases = new HashMap<>();
    private final List<OrderSpecifier<?>> defaultOrderSpecifiers = new ArrayList<>();

    private OrderByBuilder(Sort sort) {
        this.sort = sort;
    }

    public static OrderByBuilder with(Sort sort) {
        return new OrderByBuilder(sort);
    }

    public SortByBuilder whenPropertyIs(String property) {
        return new SortByBuilder(this, property);
    }

    public OrderByBuilder defaultSortBy(OrderSpecifier<?> defaultOrderSpecifier, OrderSpecifier<?>... nextDefaultOrderSpecifier) {
        defaultOrderSpecifiers.add(defaultOrderSpecifier);
        defaultOrderSpecifiers.addAll(List.of(nextDefaultOrderSpecifier));
        return this;
    }

    public OrderSpecifier[] build() {
        List<OrderSpecifier<?>> orderByClause = new ArrayList<>();
        for (Order order : sort) {
            SortByBuilder sortByBuilder = sortCases.get(order.getProperty());
            if (sortByBuilder != null) {
                for (Object sortBy : sortByBuilder.sortBy) {
                    if (sortBy instanceof OrderSpecifier) {
                        orderByClause.add((OrderSpecifier<?>) sortBy);
                    } else {
                        Expression<?> expression = (Expression<?>) sortBy;
                        orderByClause.add(toOrderSpecifier(order, expression));
                    }
                }
            }
        }
        if (orderByClause.isEmpty()) {
            orderByClause.addAll(defaultOrderSpecifiers);
        }
        return orderByClause.toArray(OrderSpecifier[]::new);
    }

    private OrderSpecifier<?> toOrderSpecifier(Order order, Expression<?> expression) {
        return new OrderSpecifier(order.getDirection().isAscending() ? ASC : DESC, expression);
    }

    public static class SortByBuilder {

        private final OrderByBuilder orderByBuilder;
        private final String property;
        private final List<Object> sortBy = new ArrayList<>();

        private SortByBuilder(OrderByBuilder orderByBuilder, String property) {
            this.orderByBuilder = orderByBuilder;
            this.property = property;
        }

        public SortByBuilder thenSortBy(Expression<?> expression) {
            this.sortBy.add(expression);
            return this;
        }

        public OrderByBuilder and() {
            orderByBuilder.sortCases.put(property, this);
            return orderByBuilder;
        }
    }
}