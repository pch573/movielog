package com.movielog.movielogbackend.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "users") //데이터베이스 테이블 이름 명시
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA는 기본 생성자가 필요합니다.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 ID를 자동으로 생성하도록 설정
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    //생성자는 Builder 패턴을 사용하는 것이 좋습니다.
    @Builder
    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    // 정보 수정을 위한 메서드
    public void update(UserUpdateRequestDto requestDto, PasswordEncoder passwordEncoder) {
        // StringUtils.hasText()는 null이 아니고, 공백이 아닌 문자가 하나 이상 포함되어 있는지 확인합니다.
        if (StringUtils.hasText(requestDto.getNickname())) {
            this.nickname = requestDto.getNickname();
        }
        if (StringUtils.hasText(requestDto.getPassword())) {
            this.password = passwordEncoder.encode(requestDto.getPassword());
        }
    }

}
