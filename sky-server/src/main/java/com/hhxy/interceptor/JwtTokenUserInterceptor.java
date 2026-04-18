package com.hhxy.interceptor;

import com.hhxy.constant.JwtClaimsConstant;
import com.hhxy.context.BaseContext;
import com.hhxy.properties.JwtProperties;
import com.hhxy.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader(jwtProperties.getUserTokenName());
        String requestUri = request.getRequestURI();

        try {
            log.info("User JWT check start, path={}, tokenPresent={}", requestUri, token != null);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());

            BaseContext.setCurrentId(userId);
            log.info("User JWT parsed, path={}, userId={}", requestUri, userId);
            return true;
        } catch (Exception ex) {
            BaseContext.removeCurrentId();
            log.warn("User JWT check failed, path={}, reason={}", requestUri, ex.getMessage());
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContext.removeCurrentId();
        log.info("User context cleared, path={}", request.getRequestURI());
    }
}
