package com.movielog.movielogbackend.user;

import com.movielog.movielogbackend.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // final이 붙은 필드의 생서앚를 자동으로 생성해주는 Lombok 어노테이션
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional // 이 메서드가 실행될 때 트랜잭션을 시작하고, 성공적으로 끝나면 커밋합니다.
    public void signUp(SignUpRequestDto requestDto) {
        // 1. 이메일 중복 확인
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 2. 닉네임 중복 확인
        if (userRepository.findByNickname(requestDto.getNickname()).isPresent()){
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        // 3. 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(requestDto.getPassword());

        // 4. DTO를 Entity로 변환하여 DB에 저장
        User user = User.builder()
                .email(requestDto.getEmail())
                .password(encodePassword)
                .nickname(requestDto.getNickname())
                .build();
        userRepository.save(user);

    }

    public String login(LoginRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        // 1. 사용자 존재 확인
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));

        // 2. 비밀번호 일치 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. JWT 토큰 생성 및 반환
        return jwtUtil.createToken(user.getEmail());
    }

    //내 정보 조회
    @Transactional(readOnly = true) //읽기 전용 트랜잭션으로 성능 향상
    public UserInfoResponseDto getMyInfo(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("해당 이메일의 사용자를 찾을 수 없습니다."));
        return new UserInfoResponseDto(user);
    }

    //내 정보 수정
    @Transactional
    public void updateMyInfo(String email, UserUpdateRequestDto requestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("해당 이메일의 사용자를 찾을 수 없습니다."));

        // 닉네임 변경 시 중복 확인
        if (requestDto.getNickname() != null && !requestDto.getNickname().equals(user.getNickname())){
            userRepository.findByNickname(requestDto.getNickname())
                    .ifPresent(u ->{
                        throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
                    });
        }

        user.update(requestDto, passwordEncoder);
        // @Transactional 어노테이션 덕분에 메서드가 종료될 때 변경된 내용을 DB에 자동으로 반영(Dirty Checking)해줌
        // 따라서 userRepository.save(user)를 호출할 필요가 없습니다.
    }
}
