package com.movielog.movielogbackend.user;

import lombok.Getter;

@Getter
public class UserInfoResponseDto  {
    private final long id;
    private final String email;
    private final String nickname;

    public UserInfoResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}
