package com.lulu.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    private static String signKey = "lulu";
    private static Long expire = 43200000L;

    /**
     * 生成JWT令牌
     * @param claims JWT第二部分 payload 中儲存的内容
     * @return
     */
    public static String generateJwt(Map<String, Object> claims){
        String jwt = Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, signKey)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
        return jwt;
    }

    /**
     * 解析JWT令牌
     * @param jwt JWT令牌
     * @return JWT第二部分 payload 中儲存的内容
     */
    public static Claims parseJWT(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }

    /**
     * 加密密碼，因不可明碼存進資料庫
     * @param password
     * @return
     */
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * 比對使用者輸入的密碼是否正確
     * @param userInputPassword
     * @param storedHashedPassword
     * @return
     */
    public static boolean comparePasswords(String userInputPassword, String storedHashedPassword) {
        return BCrypt.checkpw(userInputPassword, storedHashedPassword);
    }

    /**
     * 檢查 token 是否過期
     * @param token
     * @return
     */

    public static boolean isTokenExpired(String token) {
        try {
            Claims claims = parseJWT(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }


}
