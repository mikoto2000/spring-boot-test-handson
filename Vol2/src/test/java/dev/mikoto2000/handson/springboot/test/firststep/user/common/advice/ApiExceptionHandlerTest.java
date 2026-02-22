package dev.mikoto2000.handson.springboot.test.firststep.user.common.advice;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.mikoto2000.handson.springboot.test.firststep.common.advice.ApiExceptionHandler;
import dev.mikoto2000.handson.springboot.test.firststep.common.dto.ApiErrorResponse;
import dev.mikoto2000.handson.springboot.test.firststep.user.dto.UserCreateRequest;
import jakarta.validation.Valid;
import tools.jackson.databind.json.JsonMapper;

/**
 * ApiExceptionHandler を standalone MockMvc で直接検証するテスト。
 *
 * Controller 本体の実装には依存せず、
 * 例外 -> エラーレスポンス形式への変換ロジックを確認する。
 */
@DisplayName("ApiExceptionHandler のテスト")
class ApiExceptionHandlerTest {

  private MockMvc mockMvc;

  private JsonMapper jsonMapper;

  @BeforeEach
  void setUp() {
    // JsonMapper を生成（Spring の DI に依存しない）
    jsonMapper = new JsonMapper();

    // テスト用 Controller + Advice を明示登録して MockMvc を構築
    mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
        .setControllerAdvice(new ApiExceptionHandler())
        .build();
  }

  @Test
  void testバリデーションエラー時にApiErrorResponseを返す() throws Exception {
    // Arrange:
    // username を空にして、@Valid によるバリデーションエラーを発生させる
    var request = new UserCreateRequest(
        "",
        "test@example.com",
        "password1234"
        );

    String body = jsonMapper.writeValueAsString(request);

    // Act:
    // ダミー Controller を叩く（@Valid で例外発生 -> Advice が処理）
    MvcResult result = mockMvc.perform(post("/test/users")
            .contentType(APPLICATION_JSON)
            .content(body))
        .andExpect(status().isBadRequest())
        .andReturn();

    // Assert 1:
    // レスポンス JSON を ApiErrorResponse に変換できることを確認
    ApiErrorResponse response = assertDoesNotThrow(
        () -> jsonMapper.readValue(result.getResponse().getContentAsString(), ApiErrorResponse.class),
        "ApiErrorResponse に変換できるはず"
        );

    // Assert 2:
    // Advice が組み立てたエラー応答の固定項目を確認
    assertEquals("VALIDATION_ERROR", response.getCode());
    assertEquals("入力値が不正です", response.getMessage());

    // Assert 3:
    // username に NotBlank と Size の両方が付いているので 2 件
    assertEquals(2, response.getErrors().size(), "username のエラーは 2 件のはず");
  }

  /**
   * Advice テスト用のダミー Controller
   *
   * @Valid により MethodArgumentNotValidException を発生させるためだけに使う。
   */
  @RestController
  static class TestController {

    @PostMapping("/test/users")
    void create(@RequestBody @Valid UserCreateRequest request) {
      // バリデーションエラーを起こすことが目的なので、正常時の処理は不要
    }
  }
}
