package com.yjx.web.utils;

import com.yjx.web.constant.JwtConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;

import java.util.Date;

@Log4j2
public class TokenUtils {
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24小时

    public static String generateToken(String subject, String issuer) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer(issuer)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(JwtConstants.getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static Claims parseToken(String token) {
        Claims claims;
        try {
            // 解析 token
            claims = Jwts.parserBuilder()
                    .setSigningKey(JwtConstants.getSecretKey())
                    .build()
                    .parseClaimsJws(token)  // 解析JWT
                    .getBody();

            // 检查 token 是否过期
            if (claims.getExpiration().before(new Date())) {
                log.warn("Token已过期，无法继续使用！");
                return null; // 或者抛出自定义异常
            }

        } catch (ExpiredJwtException e) {
            // 捕获过期异常
            log.warn("Token 已过期");
            return null;  // 不返回过期的 claims
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException |
                 SignatureException e) {
            // 捕获无效或格式错误的异常
            log.error("无效的JWT令牌", e);
            return null;
        }
        return claims;  // 返回有效的 claims
    }
}
