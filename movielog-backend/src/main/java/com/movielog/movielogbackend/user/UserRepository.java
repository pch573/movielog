package com.movielog.movielogbackend.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일로 사용자를 찾는 쿼리 메서드 (중복 검사용)
    Optional<User> findByEmail(String email);

    // 닉네임으로 사용자를 찾는 쿼리 메서드 (중복 검사용)
    Optional<User> findByNickname(String nickname);
}
