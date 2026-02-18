package dev.mikoto2000.springboot.workshop.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import dev.mikoto2000.springboot.workshop.bean.User;

/**
 * UserMapper
 */
@Mapper
public interface UserMapper {

  /**
   * 対象 userId のユーザーを取得する。
   */
  @Select("""
          select
            *
          from
            "user"
          where
            id = #{userId}
          """)
  Optional<User> findById(Long userId);
}

