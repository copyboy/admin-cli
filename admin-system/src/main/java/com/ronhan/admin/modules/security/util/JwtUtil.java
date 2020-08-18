package com.ronhan.admin.modules.security.util;

import com.ronhan.admin.modules.security.properties.JwtProperties;
import com.ronhan.admin.security.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * JWT工具类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-17 14:52
 */
@Slf4j
@Component
public class JwtUtil {

    /**
     * 用户名称
     */
    private static final String USERNAME = Claims.SUBJECT;

    private static final String USERID = "userid";
    /**
     * 创建时间
     */
    private static final String CREATED = "created";
    /**
     * 权限列表
     */
    private static final String AUTHORITIES = "authorities";
    /**
     * 密钥
     */
    private static final String SECRET = "abcdefgh";
    /**
     * 有效期1小时
     */
    private static final long EXPIRE_TIME = 60 * 60 * 1000;

    @Resource
    private JwtProperties jwtProperties;

    /**
     * 生成令牌
     *
     * @return 令牌
     */
    public static String generateToken(SecurityUser userDetail) {
        Map<String, Object> claims = new HashMap<>(8);
        claims.put(USERID,userDetail.getUserId());
        claims.put(USERNAME, userDetail.getUsername());
        claims.put(CREATED, new Date());
        claims.put(AUTHORITIES, userDetail.getAuthorities());
        return generateToken(claims);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private static String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    /**
     * 根据请求令牌获取登录认证信息
     *
     * @return 用户名
     */
    public SecurityUser getUserFromToken(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return null;
            }
            String username = claims.getSubject();
            if (username == null) {
                return null;
            }
            if (isTokenExpired(token)) {
                return null;
            }
            // 解析对应的权限以及用户id
            Object authors = claims.get(AUTHORITIES);
            Integer userId = (Integer)claims.get(USERID);
            Set<String> perms = new HashSet<>();
            if (authors instanceof List) {
                for (Object object : (List) authors) {
                    perms.add(((Map) object).get("authority").toString());
                }
            }
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(perms.toArray(new String[0]));
            if (validateToken(token, username)){
                // 未把密码放到jwt
                return new SecurityUser(userId, username, "", authorities, null);
            }
        }
        return null;
    }

    /**
     * 从令牌中获取数据声明
     * @param token 令牌
     * @return 数据声明
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 验证令牌
     */
    private static Boolean validateToken(String token, String username) {
        String userName = getUsernameFromToken(token);
        return (userName.equals(username) && !isTokenExpired(token));
    }

    /**
     * 刷新令牌
     */
    public static String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put(CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 判断令牌是否过期
     * @param token 令牌
     * @return 是否过期
     */
    private static Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取请求token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(jwtProperties.getHeader());
        if (StringUtils.isNotEmpty(token)) {
            token = token.substring(jwtProperties.getTokenHead().length());
        }
        return token;
    }


}
