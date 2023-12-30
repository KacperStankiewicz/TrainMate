package pl.edu.pja.trainmate.core.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pja.trainmate.core.model.User;
import pl.edu.pja.trainmate.core.repos.UserRepository;

import java.util.List;
//todo: formatowanie
@Service
@AllArgsConstructor //do wwyalenia
public class UserImplementation implements UserService { //todo: ja bym nie szedłl w interfejs serwisu + implementacja, lepiej zrrobic serwis package-private i do tego pubbbliczna fasade
    @Autowired      //todo: lepiej tak nie robic. dodaj adnotacje na requiredArgs i private final
    UserRepository userRepository;
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(long id) { //todo: uzywajmy ,,duzych" longów czyli obiektu Long
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.getById(id);
    }
}