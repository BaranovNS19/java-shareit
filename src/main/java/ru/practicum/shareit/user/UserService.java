package ru.practicum.shareit.user;

public interface UserService {
    User creteUser(UserDto userDto);

    User getUser(Long id);

    User updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);
}
