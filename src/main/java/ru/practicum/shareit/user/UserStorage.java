package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.DataAlreadyExist;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.HashMap;

@Repository
public class UserStorage {
    private final HashMap<Long, User> userStorage = new HashMap<>();
    private static Long userId = 0L;

    public User addUser(User user) {
        userId++;
        user.setId(userId);
        for (User u : userStorage.values()) {
            if (u.getEmail().equals(user.getEmail())) {
                throw new DataAlreadyExist("пользователь с почтой [" + user.getEmail() + "] уже существует");
            }
        }
        userStorage.put(user.getId(), user);
        return user;
    }

    public User getUserById(Long id) {
        if (!userStorage.containsKey(id)) {
            throw new NotFoundException("Пользователь с id [" + id + "] не найден");
        }
        return userStorage.get(id);
    }

    public User updateUser(Long id, User user) {
        if (user.getEmail() != null) {
            for (User u : userStorage.values()) {
                if (u.getEmail().equals(user.getEmail())) {
                    throw new DataAlreadyExist("пользователь с почтой [" + user.getEmail() + "] уже существует");
                }
            }
        }
        User user1 = getUserById(id);
        if (user.getEmail() != null) {
            user1.setEmail(user.getEmail());
        }
        if (user.getName() != null) {
            user1.setName(user.getName());
        }
        userStorage.put(user1.getId(), user1);
        return user1;
    }

    public void deleteUser(Long id) {
        userStorage.remove(id);
    }

}
