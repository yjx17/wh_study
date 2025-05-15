package com.yjx.web.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义的 JWT 认证异常
 */
public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}

