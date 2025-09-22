package ru.practicum.shareit.user;

public interface UserService {
    User creteUser(User user);

    User getUser(Long id);

    User updateUser(Long id, User user);

    void deleteUser(Long id);
}
