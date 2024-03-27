package pl.edu.pja.trainmate.core.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MenteeRepository extends JpaRepository<UserEntity, Long> {

}
