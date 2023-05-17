package com.example.shipmentserver.component;

import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.shipmentserver.util.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(JwtToken.TOKEN_HEADER);
        String msg;
        ServletOutputStream os = response.getOutputStream();
        String uri = request.getRequestURI();

        try {
            JwtToken.verify(token);
            log.info("in token interceptor, this url is {}, valid", uri);
            return true;
        } catch (TokenExpiredException e) {
            msg = "token is expired";
            log.error("in token interceptor, this url is {}, expired", uri);
            os.write(JSON.toJSONString(BaseResponse.error(msg)).getBytes());
        } catch (SignatureVerificationException e) {
            msg = "signature is invalid";
            log.error("in token interceptor, this url is {}, invalid signature", uri);
            os.write(JSON.toJSONString(BaseResponse.error(msg)).getBytes());
        } catch (AlgorithmMismatchException e) {
            msg = "wrong encrypt algorithm";
            log.error("in token interceptor, this url is {}, wrong encrypt algorithm", uri);
            os.write(JSON.toJSONString(BaseResponse.error(msg)).getBytes());
        } catch (Exception e) {
            msg = "invalid token";
            log.error(token);
            log.error("in token interceptor, this url is {}, invalid token", uri);
            log.error(e.toString());
            os.write(JSON.toJSONString(BaseResponse.error(msg)).getBytes());
        }
        return false;
    }
}
