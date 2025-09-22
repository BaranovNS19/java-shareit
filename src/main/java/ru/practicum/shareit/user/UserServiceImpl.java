package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final FakeUserRepository fakeUserRepository;

    @Autowired
    public UserServiceImpl(FakeUserRepository fakeUserRepository) {
        this.fakeUserRepository = fakeUserRepository;
    }

    @Override
    public User creteUser(User user) {
        return fakeUserRepository.addUser(user);
    }

    @Override
    public User getUser(Long id) {
        return fakeUserRepository.getUserById(id);
    }

    @Override
    public User updateUser(Long id, User user) {
        return fakeUserRepository.updateUser(id, user);
    }

    @Override
    public void deleteUser(Long id) {
        fakeUserRepository.deleteUser(id);
    }
}
