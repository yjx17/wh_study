package com.yjx.web.constant;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtConstants {
    private static final String RAW_SECRET = "916vzxm/pAnWuQDmCffpcez9ak0A2UxlqUu0ok+YDuc=";

    public static SecretKey getSecretKey() {
        // 转为 Base64 编码后作为签名密钥
//        你对字符串做了 Base64 编码，但 MyBatis-Plus 或 JJWT 并不要求你编码密钥，这会导致最终的签名密钥和原始密钥不一样，造成：
//          ❌ 签名验证失败：
//        JWT signature does not match locally computed signature
//        会把明文 key 做一次 Base64 编码，变成了另一串字节，导致你在生成 token 和解析 token 时使用了不同的密钥，即使看起来用的是同一个字符串。

//        byte[] keyBytes = Base64.getEncoder().encode(RAW_SECRET.getBytes());
//        return Keys.hmacShaKeyFor(keyBytes);

        //直接用字符串字节，不要 Base64 编码！
        return Keys.hmacShaKeyFor(RAW_SECRET.getBytes(StandardCharsets.UTF_8));
    }
}
