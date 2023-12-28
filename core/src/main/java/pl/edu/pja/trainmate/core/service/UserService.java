package pl.edu.pja.trainmate.core.service;

import pl.edu.pja.trainmate.core.model.User;

import java.util.List;

public interface UserService {
    public List<User> getUsers();
    public void saveUser(User user);
    public void deleteUser(long id);
    public User getUserById(long id);
}
