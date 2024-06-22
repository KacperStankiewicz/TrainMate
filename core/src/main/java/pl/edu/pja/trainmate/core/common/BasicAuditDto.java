package pl.edu.pja.trainmate.core.common;

import static lombok.AccessLevel.PRIVATE;

import com.querydsl.core.annotations.QueryProjection;
import javax.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Embeddable
@NoArgsConstructor(force = true, access = PRIVATE)
public class BasicAuditDto {


    private final Long id;
    private final Long version;

    @QueryProjection
    public BasicAuditDto(Long id, Long version) {
        this.id = id;
        this.version = version;
    }

    public static BasicAuditDto ofValue(long id, long version) {
        return new BasicAuditDto(id, version);
    }
}
