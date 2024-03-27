package pl.edu.pja.trainmate.core.common;

import static lombok.AccessLevel.PRIVATE;

import com.querydsl.core.annotations.QueryProjection;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.PersistenceConstructor;

@FieldNameConstants
@Embeddable
@Value
@NoArgsConstructor(access = PRIVATE, force = true)
public class UserId implements Serializable {

    public static final String UNKNOWN_AUDITOR = "<unknown>";

    private static final long serialVersionUID = 7667472043944913479L;

    @Column(name = "keycloak_id")
    String value;

    @PersistenceConstructor
    @QueryProjection
    public UserId(String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static UserId valueOf(String value) {
        Objects.requireNonNull(value);
        return new UserId(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserId userId = (UserId) o;
        return Objects.equals(value, userId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}