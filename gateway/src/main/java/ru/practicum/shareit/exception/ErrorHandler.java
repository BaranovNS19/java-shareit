package ru.practicum.shareit.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<ErrorResponse> handleFeignNotFound(FeignException.NotFound ex) {
        String serverResponse = ex.contentUTF8();
        ErrorResponse errorResponse = parseErrorResponse(serverResponse);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    private ErrorResponse parseErrorResponse(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, ErrorResponse.class);
        } catch (Exception e) {
            return new ErrorResponse("Ошибка", json);
        }
    }
}
