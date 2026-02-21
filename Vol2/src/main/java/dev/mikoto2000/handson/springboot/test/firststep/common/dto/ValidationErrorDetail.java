package dev.mikoto2000.handson.springboot.test.firststep.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ValidationErrorDetail {
    private String field;
    private String message;
}
