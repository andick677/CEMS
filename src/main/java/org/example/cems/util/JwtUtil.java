package org.example.cems.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.Accessors;
import org.example.cems.mapper.UserMapper;
import org.example.cems.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Autowired
    UserMapper userMapper;

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private long EXPIRRATION_TIME = 1000 * 60 * 60 * 8;

    // 使用 Jwts 的 parser 建立解析器，設定簽章的密鑰（SECRET_KEY），
    // 然後解析 token，最後取得其中的 body，也就是 claims。
    private Claims extractAllCLaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public <T> T extractClaim(String token,Function<Claims,T> claimsResolver){
        final Claims claims = extractAllCLaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private String createToken(Map<String,Object> claims,String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRRATION_TIME))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();

    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        // 根據 email 查詢 User 對象
        User user = userMapper.findByEmail(userDetails.getUsername());
        if (user != null) {
            claims.put("userId", user.getId());
            claims.put("role", user.getRole());
            claims.put("username",user.getUsername());
        } else {
            throw new RuntimeException("User not found for email: " + userDetails.getUsername());
        }
        return createToken(claims, userDetails.getUsername());
    }

    public Boolean validateToken(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }




}
