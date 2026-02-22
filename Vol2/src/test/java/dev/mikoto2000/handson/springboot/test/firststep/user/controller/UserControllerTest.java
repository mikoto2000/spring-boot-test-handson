package dev.mikoto2000.handson.springboot.test.firststep.user.controller;

import static org.junit.jupiter.api.Assertions.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import dev.mikoto2000.handson.springboot.test.firststep.user.dto.UserCreateRequest;
import dev.mikoto2000.handson.springboot.test.firststep.user.dto.UserCreateResponse;
import tools.jackson.databind.json.JsonMapper;

/**
 * UserController の入力バリデーションを HTTP 層で検証するテスト。
 *
 * DTO 単体テストでは制約そのものを確認し、
 * ここでは @RequestBody + @Valid により想定した HTTP ステータスコードが返ることを確認する。
 * エラーレスポンスの形式は ApiExceptionHandlerTest で確認する。
 */
@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JsonMapper jsonMapper;

  @Nested
  @DisplayName("正常系")
  class NormalCase {
    @Test
    void test妥当なリクエストで201が返る() throws Exception {
      var request = new UserCreateRequest(
          "test",
          "test@example.com",
          "password123"
          );

      var body = jsonMapper.writeValueAsString(request);

      MvcResult result = mockMvc.perform(post("/users")
          .contentType(MediaType.APPLICATION_JSON)
          .content(body))
        // HTTP ステータスの確認
        .andExpect(status().isCreated())
        .andReturn();

      // 期待通りの型か、JsonMapper で変換することにより確認する
      UserCreateResponse response = assertDoesNotThrow(
          () -> jsonMapper.readValue(result.getResponse().getContentAsString(), UserCreateResponse.class),
          "UserCreateResponse に変換できるはず");

      // 型の内容が期待通りか確認する
      assertEquals(1L, response.getId());
      assertEquals("test", response.getUsername());
      assertEquals("test@example.com", response.getEmail());
    }
  }

  @Nested
  @DisplayName("異常系")
  class ErrorCase {
    @Test
    void test不正なリクエストで400が返る() throws Exception {
      var request = new UserCreateRequest(
          "",
          "test@example.com",
          "password123"
          );

      var body = jsonMapper.writeValueAsString(request);

      mockMvc.perform(post("/users")
          .contentType(MediaType.APPLICATION_JSON)
          .content(body))
        // HTTP ステータスの確認
        .andExpect(status().isBadRequest());
    }

    // レスポンスの型を確認するテストは ApiExceptionHandlerTest に任せる
  }
}
