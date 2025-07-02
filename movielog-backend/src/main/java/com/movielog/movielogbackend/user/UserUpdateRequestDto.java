package com.movielog.movielogbackend.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDto {
    private String nickname;
    private String password;
}
