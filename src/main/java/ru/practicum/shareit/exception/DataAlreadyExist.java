package ru.practicum.shareit.exception;

public class DataAlreadyExist extends RuntimeException {
    public DataAlreadyExist(String message) {
        super(message);
    }
}
