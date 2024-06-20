package pl.edu.pja.trainmate.core.common;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Embeddable
@Value
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED, force = true)
public class StorageId {

    @Column(name = "storage_id")
    UUID value;

    public static StorageId valueOf(UUID value) {
        Objects.requireNonNull(value);
        return new StorageId(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StorageId storageId = (StorageId) o;
        return Objects.equals(value, storageId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
