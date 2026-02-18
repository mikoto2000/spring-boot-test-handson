package dev.mikoto2000.springboot.workshop.service;

import org.springframework.stereotype.Service;

import dev.mikoto2000.springboot.workshop.repository.UserMapper;
import dev.mikoto2000.springboot.workshop.util.DateTimeUtil;
import dev.mikoto2000.springboot.workshop.util.UserUtil;
import lombok.RequiredArgsConstructor;

/**
 * UserService
 */
@RequiredArgsConstructor
@Service
public class UserService {

  private final DateTimeUtil dateTimeUtil;

  private final UserMapper userMapper;

  /**
   * 指定した usreId のユーザーの年齢を返却する。
   *
   * @param userId ユーザー ID
   * @return ユーザーの年齢
   */
  public Integer getAge(Long userId) {

    // ユーザーの取得
    var userOpt = userMapper.findById(userId);
    if (userOpt.isEmpty()) {
      throw new RuntimeException(String.format("対象ユーザーが見つかりませんでした(userId: %d)", userId));
    }
    var user = userOpt.get();

    // 本日の日付の取得
    // 単体テスト時に楽に DI できるように
    // 直接 LocalDate.now() を使わないようにする
    var today = dateTimeUtil.now();

    // 誕生日の取得
    // 本当は User クラスにあるべきだけど
    // 説明の簡単化のため UserUtil クラスに実装している
    var birthday = UserUtil.getAge(user.getBirthday(), today);

    return birthday;
  }
}

