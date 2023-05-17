package com.example.shipmentserver.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.shipmentserver.component.UserInfo;
import com.example.shipmentserver.config.Constants;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;

/**
 * JWT token工具类
 */
@Slf4j
public class JwtToken {
    public static final String TOKEN_HEADER = "Authorization";
    private static final int AMOUNT = 7 * 24;
//    private static HashMap<String, String> userInfo = new HashMap<>();
    /**
     * 生成token
     *
     * @param map 意欲定义在payload中的资源
     * @return token
     */
    public static String create(HashMap<String, String> map) {
        JWTCreator.Builder builder = JWT.create();

        // 自定义超时时间
        Calendar ins = Calendar.getInstance();
        ins.add(Calendar.HOUR, AMOUNT);
        builder.withExpiresAt(ins.getTime());

        // 自定义payload
        map.forEach(builder::withClaim);

        // 展示token信息
        log.info("token created:" + map);

        return builder.sign(Algorithm.HMAC256(Constants.SECRET));
    }

    /**
     * 校验token
     *
     * @param token token
     */
    public static void verify(String token) {
        // bearer token
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("invalid token");
        }
        token = token.substring(7);
        JWT.require(Algorithm.HMAC256(Constants.SECRET)).build().verify(token);
        // decode the token, save userinfo
        var id = JwtToken.decode(token).getClaim("id").asString();
        var username = JwtToken.decode(token).getClaim("username").asString();
        var email = JwtToken.decode(token).getClaim("email").asString();
        var role = JwtToken.decode(token).getClaim("role").asString();
        UserInfo.set("id", id);
        UserInfo.set("username", username);
        UserInfo.set("email", email);
        UserInfo.set("role", role);
    }

    /**
     * 解析token
     *
     * @param token token
     * @return DecodedJWT
     */
    public static DecodedJWT decode(String token) {
        return JWT.require(Algorithm.HMAC256(Constants.SECRET)).build().verify(token);
    }

    /**
     * 从请求头中获取token并返回解析过的token对象
     *
     * @param request 请求
     * @return 解析后的token
     */
    public static DecodedJWT acquireClaimFromHeader(HttpServletRequest request) {
        String token = request.getHeader(JwtToken.TOKEN_HEADER);
        return JwtToken.decode(token);
    }

}
