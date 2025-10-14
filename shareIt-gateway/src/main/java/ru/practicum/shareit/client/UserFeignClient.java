package ru.practicum.shareit.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

@FeignClient(
        name = "userService",
        url = "${shareit-server.url}",
        path = "/users"
)
public interface UserFeignClient {

    @PostMapping
    ResponseEntity<Object> createUser(@RequestBody @Valid UserDto userDto);

    @GetMapping("/{id}")
    ResponseEntity<Object> getUserById(@PathVariable Long id);

    @PatchMapping("/{id}")
    ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserDto userDto);

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable Long id);
}
