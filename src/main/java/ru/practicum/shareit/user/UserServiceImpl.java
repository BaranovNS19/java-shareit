package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserStorage fakeUserRepository;

    @Autowired
    public UserServiceImpl(UserStorage fakeUserRepository) {
        this.fakeUserRepository = fakeUserRepository;
    }

    @Override
    public User creteUser(UserDto userDto) {
        return fakeUserRepository.addUser(UserMapper.toUser(userDto));
    }

    @Override
    public User getUser(Long id) {
        return fakeUserRepository.getUserById(id);
    }

    @Override
    public User updateUser(Long id, UserDto userDto) {
        return fakeUserRepository.updateUser(id, UserMapper.toUser(userDto));
    }

    @Override
    public void deleteUser(Long id) {
        fakeUserRepository.deleteUser(id);
    }
}
