package dev.mikoto2000.springboot.workshop.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * UserUtilTest
 */
public class UserUtilTest {
  @Test
  public void 誕生日ちょうど() {
    LocalDate birthday = LocalDate.of(2000, 1, 1);
    LocalDate targetDate = LocalDate.of(2025, 1, 1);

    Integer expect = 25;
    Integer actual = UserUtil.getAge(birthday, targetDate);

    // 期待する値と実際の値を比較
    assertEquals(expect, actual);
  }

  // TODO: 誕生日直前・直後を実装

  @Test
  public void birthdayがnullで例外が発生する() {
    LocalDate birthday = null;
    LocalDate targetDate = LocalDate.of(2025, 1, 1);

    Exception e = assertThrows(
        IllegalArgumentException.class,
        () -> UserUtil.getAge(birthday, targetDate));

    // 期待する値と実際の値を比較
    assertEquals("birthday に値を設定してください", e.getMessage());
  }

}

