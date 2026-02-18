package dev.mikoto2000.springboot.workshop.util;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

/**
 * DateTimeUtil
 *
 * LocalDate.now() などを直接クラス内に書くと、
 * 単体テスト不能となるため Util クラスを作り DI できるようにしておく。
 */
@Component
public class DateTimeUtil {

  /**
   * 現在の日付を返却する。
   */
  public LocalDate now() {
    return LocalDate.now();
  }
}

