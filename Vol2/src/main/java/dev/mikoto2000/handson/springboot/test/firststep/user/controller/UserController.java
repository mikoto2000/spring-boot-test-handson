package dev.mikoto2000.handson.springboot.test.firststep.user.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import dev.mikoto2000.handson.springboot.test.firststep.user.dto.UserCreateRequest;
import dev.mikoto2000.handson.springboot.test.firststep.user.dto.UserCreateResponse;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserCreateResponse create(@RequestBody @Valid UserCreateRequest request) {
        // 教材用なのでサービス呼び出しは省略、適当にレスポンスを組み立てて返却
        return new UserCreateResponse(1L, request.getUsername(), request.getEmail());
    }
}
