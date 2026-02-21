package dev.mikoto2000.handson.springboot.test.firststep.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserCreateResponse {

    private Long id;
    private String username;
    private String email;

}
