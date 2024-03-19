package com.ebikerrent.alquilerbicicletas.exceptions;

import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private MethodArgumentNotValidException exception;

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> manejarResourceNotFound(ResourceNotFoundException resourceNotFoundException) {
        Map<String, String> exceptionMessage = new HashMap<>();
        exceptionMessage.put("message", "Resource not found: " + resourceNotFoundException.getMessage());
        return exceptionMessage;
    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> manejarBadRequest(BadRequestException badRequestException) {
        Map<String, String> exceptionMessage = new HashMap<>();
        exceptionMessage.put("message", "Bad Request: " + badRequestException.getMessage());

        return exceptionMessage;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return "Error: El nombre del producto ya existe, por favor intentar con otro nombre";}

    @ExceptionHandler({DuplicateEntryException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> manejarDuplicateEntry(DuplicateEntryException duplicateEntryException) {
        Map<String, String> exceptionMessage = new HashMap<>();
        exceptionMessage.put("message", "Duplicate Entry: " + duplicateEntryException.getMessage());
        return exceptionMessage;
    }
}
