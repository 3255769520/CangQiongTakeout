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

/**
 * Admin JWT token interceptor.
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader(jwtProperties.getAdminTokenName());
        String requestUri = request.getRequestURI();

        try {
            log.info("Admin JWT check start, path={}, tokenPresent={}", requestUri, token != null);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());

            BaseContext.setCurrentId(empId);
            log.info("Admin JWT parsed, path={}, empId={}", requestUri, empId);
            return true;
        } catch (Exception ex) {
            BaseContext.removeCurrentId();
            log.warn("Admin JWT check failed, path={}, reason={}", requestUri, ex.getMessage());
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContext.removeCurrentId();
        log.info("Admin context cleared, path={}", request.getRequestURI());
    }
}
