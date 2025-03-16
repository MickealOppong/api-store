package com.repo.api.dto.response;

import com.repo.api.dto.TokenDto;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class LoginResponse {

    private Long id;
    private String username;
    private String name;
    private TokenDto tokenDto;
    private Instant lastLogin;
}
