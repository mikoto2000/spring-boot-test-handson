package dev.mikoto2000.springboot.workshop.bean;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * User
 */
@AllArgsConstructor
@Data
public class User {
  /**
   * ユーザー ID
   */
  private Long id;

  /**
   * ユーザー名
   */
  private String name;

  /**
   * 誕生日
   */
  private LocalDate birthday;
}

