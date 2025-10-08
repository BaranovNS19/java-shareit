package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.DataAlreadyExist;
import ru.practicum.shareit.exception.NotFoundException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User creteUser(UserDto userDto) {
        if (UserMapper.toUser(userDto).getEmail() != null) {
            for (User u : userRepository.findAll()) {
                if (u.getEmail().equals(userDto.getEmail())) {
                    throw new DataAlreadyExist("пользователь с почтой [" + userDto.getEmail() + "] уже существует");
                }
            }
        }
        return userRepository.save(UserMapper.toUser(userDto));
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id [" + id + "] не найден"));
    }

    @Transactional
    @Override
    public User updateUser(Long id, UserDto userDto) {
        User existingUser = getUser(id);
        if (UserMapper.toUser(userDto).getEmail() != null) {
            for (User u : userRepository.findAll()) {
                if (u.getEmail().equals(userDto.getEmail())) {
                    throw new DataAlreadyExist("пользователь с почтой [" + userDto.getEmail() + "] уже существует");
                }
            }
        }
        if (userDto.getEmail() != null) {
            existingUser.setEmail(userDto.getEmail());
        }
        if (userDto.getName() != null) {
            existingUser.setName(userDto.getName());
        }
        return existingUser;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
