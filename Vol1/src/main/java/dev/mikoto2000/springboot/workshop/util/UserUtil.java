package dev.mikoto2000.springboot.workshop.util;

import java.time.LocalDate;
import java.time.Period;

/**
 * ユーザーに関する諸々の処理を詰め込んだ Util クラス。
 *
 * 本来なら User クラスに含めるべき。
 */
public class UserUtil {

  /**
   * 年齢を取得する。
   *
   * 引数 targetDate で指定した日付に、誕生日 birthday の人が何歳であるかを返却する。
   *
   * @param birthday 誕生日
   * @param targetDate 計算の基準日
   */
  public static Integer getAge(LocalDate birthday, LocalDate targetDate) {
    if (birthday == null) {
      throw new IllegalArgumentException("birthday に値を設定してください");
    }

    if (targetDate == null) {
      throw new IllegalArgumentException("targetDate に値を設定してください");
    }

    return Period.between(birthday, targetDate).getYears();
  }
}

