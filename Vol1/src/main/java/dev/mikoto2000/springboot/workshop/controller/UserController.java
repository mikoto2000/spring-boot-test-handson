package dev.mikoto2000.springboot.workshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dev.mikoto2000.springboot.workshop.service.UserService;
import lombok.RequiredArgsConstructor;

/**
 * UserController
 */
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService service;

  /**
   * 指定した usreId のユーザーの年齢を返却する。
   *
   * @param userId ユーザー ID
   * @return ユーザーの年齢
   */
  @GetMapping("/users/{userId}/age")
  public Integer getAge(
      @PathVariable Long userId
      ) {
    return service.getAge(userId);
  }
}

