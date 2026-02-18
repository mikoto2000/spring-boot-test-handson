package dev.mikoto2000.springboot.workshop.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import dev.mikoto2000.springboot.workshop.bean.User;

/**
 * UserMapperTest
 */
@MybatisTest
public class UserMapperTest {

  @Autowired
  private UserMapper mapper;

  @Test
  @Sql("/sql/UserMapperTest-findById.sql")
  @Transactional
  public void testFindById() {
    Optional<User> userOpt = mapper.findById(1L);

    assertTrue(userOpt.isPresent());

    User user = userOpt.get();

    assertEquals(user.getId(), 1L);
    assertEquals(user.getName(), "mikoto2000");
    assertEquals(user.getBirthday(), LocalDate.of(2000, 1, 1));
  }
}

