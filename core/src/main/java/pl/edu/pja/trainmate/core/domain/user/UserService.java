package pl.edu.pja.trainmate.core.domain.user;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class UserService {

    private final UserRepository userRepository;

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public UserEntity addUser(String name) {
        var user = UserEntity.builder().name(name).build();
        return userRepository.save(user);
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserEntity getUserById(Long id) {
        return userRepository.getById(id);
    }
}