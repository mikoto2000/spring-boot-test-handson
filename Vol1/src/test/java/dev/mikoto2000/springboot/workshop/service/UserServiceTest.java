package dev.mikoto2000.springboot.workshop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.mikoto2000.springboot.workshop.bean.User;
import dev.mikoto2000.springboot.workshop.repository.UserMapper;
import dev.mikoto2000.springboot.workshop.util.DateTimeUtil;

/**
 * UserServiceTest
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  // モックする対象
  private DateTimeUtil dateTimeUtil;

  @Mock
  // モックする対象
  private UserMapper mapper;

  // テスト対象
  private UserService service;

  @Test
  public void testGetAge() {
    // 現在時刻を 2025/1/1 だとする
    when(dateTimeUtil.now())
      .thenReturn(LocalDate.of(2025, 1, 1));

    // ユーザー ID 1 のユーザーが 25 歳の場合
    when(mapper.findById(1L))
      .thenReturn(Optional.of(new User(1L, "mikoto2000", LocalDate.of(2000, 1, 1))));

    // 実行結果の取得
    Integer actual = service.getAge(1L);

    // 想定と実行結果の比較
    assertEquals(25, actual);
  }

  @BeforeEach
  public void setup() {
    service = new UserService(dateTimeUtil, mapper);
  }

  @AfterEach
  public void tearDown() {
    reset(mapper);
  }
}

