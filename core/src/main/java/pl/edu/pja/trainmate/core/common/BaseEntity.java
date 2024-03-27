package pl.edu.pja.trainmate.core.common;

import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {

    @CreatedDate
    private LocalDateTime creationDateTime;

    @CreatedBy
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "created_by"))
    private UserId createdBy;

    @LastModifiedDate
    private LocalDateTime modificationDateTime;

    @LastModifiedBy
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "modified_by"))
    private UserId modifiedBy;

    public abstract Long getId();

    public void updateModificationDateTime() {
        this.modificationDateTime = LocalDateTime.now();
    }
}