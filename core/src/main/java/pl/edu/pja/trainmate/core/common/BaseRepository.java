package pl.edu.pja.trainmate.core.common;

import javax.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long> {

    default T findExactlyOneById(Long id) {
        return this.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Could not find entity for id: %s", id)));
    }
}
