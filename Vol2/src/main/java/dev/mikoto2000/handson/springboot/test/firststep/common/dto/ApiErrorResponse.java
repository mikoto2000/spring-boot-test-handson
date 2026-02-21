package dev.mikoto2000.handson.springboot.test.firststep.common.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class ApiErrorResponse {
    private String code;
    private String message;
    private List<ValidationErrorDetail> errors;
}
