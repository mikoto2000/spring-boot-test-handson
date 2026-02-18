package dev.mikoto2000.springboot.workshop.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import dev.mikoto2000.springboot.workshop.service.UserService;

/**
 * UserControllerTest
 */
@WebMvcTest(UserController.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private UserService service;

  @Test
  public void ユーザーの年齢を取得する() throws Exception {
    // ユーザー ID 1 のユーザーが 25 歳だったとする
    when(service.getAge(1L)).thenReturn(25);

    // controller 呼び出しと値の確認
    mockMvc.perform(get("/users/1/age"))
      .andExpect(status().isOk())
      .andExpect(content().string("25"));
  }
}

