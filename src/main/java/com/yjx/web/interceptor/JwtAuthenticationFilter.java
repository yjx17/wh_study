package com.yjx.web.interceptor;

import com.yjx.web.exception.JwtAuthenticationException;
import com.yjx.web.utils.*;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final AntPathMatcher matcher = new AntPathMatcher();

    // 直接在类中定义白名单路径
    private static final String[] WHITELIST = {
            "/users/login",
            "/users/register",
            "/register",
            "/login",
            "/doc.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/demo.html"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String path = request.getRequestURI();

            // 跳过白名单路径的 JWT 验证
            for (String pattern : WHITELIST) {
                if (matcher.match(pattern, path)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            String Authorization = request.getHeader("Authorization");

            if (Authorization == null || Authorization.isEmpty()) {
                throw new JwtAuthenticationException("缺少 Authorization 头");
            }

            Claims claims = TokenUtils.parseToken(Authorization);
            if (claims == null) {
                throw new JwtAuthenticationException("Token 已过期或无效");
            }

            // 统一设置Authentication对象
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            claims.getSubject(),
                            null,
                            null // 权限列表
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            BaseContext.setCurrentId(claims.getSubject());
            filterChain.doFilter(request, response);

        } catch (JwtAuthenticationException e) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.getWriter().write(String.format("{\"code\":401,\"message\":\"%s\"}", e.getMessage()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(String.format("{\"code\":500,\"message\":\"%s\"}", e.getMessage()));
        }
    }
}
