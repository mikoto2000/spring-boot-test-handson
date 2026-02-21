package dev.mikoto2000.handson.springboot.test.firststep.user.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * DTO の Bean Validation を Spring を起動せずに検証するテスト。
 */
@DisplayName("UserCreateRequest の Bean Validation")
class UserCreateRequestValidationTest {

  // バリデーションを実行するためのバリデーター
  private static Validator validator;

  // 全テストの前に一度だけ実行する処理
  @BeforeAll
  static void setUp() {
    // バリデーターを生成
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Nested
  @DisplayName("正常系")
  class NormalCases {
    @Test
    @DisplayName("妥当な値なら違反なし")
    void validate_ok() {

      // Arrange
      UserCreateRequest request = new UserCreateRequest(
          "test",
          "test@example.com",
          "password123"
          );

      // Act
      Set<ConstraintViolation<UserCreateRequest>> violations = validator.validate(request);

      // Assert
      assertTrue(violations.isEmpty(), "違反は0件のはず");
    }
  }

  @Nested
  @DisplayName("異常系")
  class ErrorCases {
    @Test
    @DisplayName("不正な値なら違反が返る")
    void validate_ng() {
      // Arrange
      UserCreateRequest request = new UserCreateRequest(
          "",
          "abc",
          "123"
          );

      // Act
      Set<ConstraintViolation<UserCreateRequest>> violations = validator.validate(request);

      // Assert
      // エラー内容の順序は未定義なので、 Set にして contains で確認できるようにする
      Set<String> paths = violations.stream()
        .map(v -> v.getPropertyPath().toString())
        .collect(Collectors.toSet());

      assertTrue(paths.contains("username"), "username の違反があるはず");
      assertTrue(paths.contains("email"), "email の違反があるはず");
      assertTrue(paths.contains("password"), "password の違反があるはず");
    }
  }

  @Nested
  @DisplayName("境界値")
  class BoundaryCases {

    @ParameterizedTest(name = "[{index}] {0}: username長={1} → username違反={2}")
    @MethodSource("dev.mikoto2000.handson.springboot.test.firststep.user.dto.UserCreateRequestValidationTest#usernameLengthBoundaryCases")
    @DisplayName("username の文字数境界")
    void username_length_boundary(String caseName, int usernameLength, boolean expectedViolation) {
      // Arrange:
      // username だけを境界値として変化させる
      // email / password は常に妥当値に固定し、判定対象を username に絞る
      String username = "a".repeat(usernameLength);
      UserCreateRequest request = new UserCreateRequest(
          username,           // MethodSource により変化する
          "test@example.com", // 常に妥当
          "password123"       // 常に妥当
          );

      // Act: Bean Validation を実行
      Set<ConstraintViolation<UserCreateRequest>> violations = validator.validate(request);

      // Assert 1:
      // 今回は username だけを変化させているため、違反の有無をそのまま username の判定結果として扱える。
      boolean actualViolation = !violations.isEmpty();

      assertEquals(
          expectedViolation,
          actualViolation,
          "case=" + caseName + ", usernameLength=" + usernameLength + " の判定が期待と異なる"
          );

      // Assert 2:
      // propertyPath に username が含まれるかを確認する。
      boolean usernamePathFound = violations.stream()
        .map(ConstraintViolation::getPropertyPath)
        .anyMatch(path -> path.toString().contains("username"));

      assertEquals(
          expectedViolation,
          usernamePathFound,
          "case=" + caseName + ", usernameLength=" + usernameLength + " のパスが期待と異なる"
          );
    }

    // TODO: 他の項目の境界値テスト
  }

  /**
   * username の境界値ケースを供給する。
   *
   * 引数の意味:
   *  1列目: ケース名（説明用）
   *  2列目: username の文字数
   *  3列目: username に違反が期待されるか（true=違反あり）
   *
   * 今回の制約:
   *  - username は 3〜20 文字
   */
  static Stream<Arguments> usernameLengthBoundaryCases() {
    return Stream.of(
        Arguments.of("最小値-1", 2, true),
        Arguments.of("最小値",   3, false),
        Arguments.of("最大値",   20, false),
        Arguments.of("最大値+1", 21, true)
        );
  }
}
