package pl.edu.pja.trainmate.core.common;

import static lombok.AccessLevel.PRIVATE;

import com.querydsl.core.annotations.QueryProjection;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.data.annotation.PersistenceConstructor;

@Embeddable
@Value
@NoArgsConstructor(access = PRIVATE, force = true)
public class UserId implements Serializable {

    public static final String UNKNOWN_AUDITOR = "<unknown>";

    private static final long serialVersionUID = 7667472043944913479L;

    @Column(name = "keycloak_id")
    String keycloakId;

    @PersistenceConstructor
    @QueryProjection
    public UserId(String keycloakId) {
        Objects.requireNonNull(keycloakId);
        this.keycloakId = keycloakId;
    }

    public static UserId valueOf(String keycloakId) {
        Objects.requireNonNull(keycloakId);
        return new UserId(keycloakId);
    }

    @Override
    public String toString() {
        return keycloakId;
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
        return Objects.equals(keycloakId, userId.keycloakId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keycloakId);
    }
}