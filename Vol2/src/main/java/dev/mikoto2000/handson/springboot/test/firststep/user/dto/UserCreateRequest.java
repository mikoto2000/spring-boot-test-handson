package dev.mikoto2000.handson.springboot.test.firststep.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserCreateRequest {

    @NotBlank(message = "username は必須です")
    @Size(min = 3, max = 20, message = "username は 3〜20 文字で入力してください")
    private String username;

    @NotBlank(message = "email は必須です")
    @Email(message = "email の形式が不正です")
    private String email;

    @NotBlank(message = "password は必須です")
    @Size(min = 8, max = 100, message = "password は 8〜100 文字で入力してください")
    private String password;
}
