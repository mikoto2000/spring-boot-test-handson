package dev.mikoto2000.handson.springboot.test.firststep.common.advice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.mikoto2000.handson.springboot.test.firststep.common.dto.ApiErrorResponse;
import dev.mikoto2000.handson.springboot.test.firststep.common.dto.ValidationErrorDetail;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<ValidationErrorDetail> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e2 -> new ValidationErrorDetail(e2.getField(), e2.getDefaultMessage()))
                .toList();

        return new ApiErrorResponse(
                "VALIDATION_ERROR",
                "入力値が不正です",
                errors
        );
    }
}
