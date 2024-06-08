package pl.edu.pja.trainmate.core.common;

import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.edu.pja.trainmate.core.common.exception.OptimisticLockingException;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {

    protected Long version = 0L;

    @CreatedDate
    private LocalDateTime creationDateTime;

    @CreatedBy
    @Embedded
    @AttributeOverride(name = "keycloakId", column = @Column(name = "created_by"))
    private UserId createdBy;

    @LastModifiedDate
    private LocalDateTime modificationDateTime;

    @LastModifiedBy
    @Embedded
    @AttributeOverride(name = "keycloakId", column = @Column(name = "modified_by"))
    private UserId modifiedBy;

    public abstract Long getId();

    @PreUpdate
    private void incrementVersion() {
        this.version = this.version + 1L;
    }

    public void validateVersion(Long version) {
        if (this.isInvalidVersion(version)) {
            throw new OptimisticLockingException(this.getEntityName());
        }
    }

    private boolean isInvalidVersion(Long version) {
        return this.version == null || !this.version.equals(version);
    }

    private String getEntityName() {
        return this.getClass().getSimpleName();
    }
}