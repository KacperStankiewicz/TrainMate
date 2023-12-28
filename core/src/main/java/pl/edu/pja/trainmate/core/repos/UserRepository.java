package pl.edu.pja.trainmate.core.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pja.trainmate.core.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
