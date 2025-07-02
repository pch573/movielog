package com.movielog.movielogbackend.user;

import com.movielog.movielogbackend.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 내 정보 조회 API
    @GetMapping("/me")
    public ResponseEntity<UserInfoResponseDto> getMyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // @AuthenticationPrincipal 어노테이션을 통해 현재 로그인된 사용자의 정보를 가져옵니다.
        String email = userDetails.getUsername();
        UserInfoResponseDto responseDto = userService.getMyInfo(email);
        return ResponseEntity.ok(responseDto);
    }

    // 내 정보 수정 API
    @PutMapping("/me")
    public ResponseEntity<String> updateMyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody UserUpdateRequestDto requestDto) {
        String email = userDetails.getUsername();
        userService.updateMyInfo(email, requestDto);
        return ResponseEntity.ok("사용자 정보가 성공적으로 수정되었습니다.");
    }
}
