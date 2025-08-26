package TheFirstCommit.demo.config.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JWTUtil {
    private static Key jwtKey;

    private final static String ACCESS = "ACCESS";
    private final static String REFRESH = "REFRESH";

    private static Long ACCESS_EXPIRE;
    private static Long REFRESH_EXPIRE;

    public JWTUtil(
        @Value("${JWT_SECRET_KEY}") String JWT_SECRET_KEY,
        @Value("${jwt.access-token-time}") Long accessTokenTime,
        @Value("${jwt.refresh-token-time}") Long refreshTokenTime ) {

        jwtKey = Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes());
        ACCESS_EXPIRE = accessTokenTime;
        REFRESH_EXPIRE = refreshTokenTime;
    }

    // access/refresh | 만료 기간 | Long userId
    public static String generateAccessToken(Long userId) {
        return Jwts.builder()
            .setSubject(ACCESS)
            .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRE * 1000))
            .claim("userId", userId)
            .signWith(jwtKey)
            .compact();
    }
    public static String generateRefreshToken(Long userId) {
        return Jwts.builder()
            .setSubject(REFRESH)
            .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRE * 1000))
            .claim("userId", userId)
            .signWith(jwtKey)
            .compact();
    }

    public static Long decodeToken(String token){
        if(token == null || token.isEmpty())
            throw new NoTokenException();

        Claims body;
        try{
            body = Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token).getBody();
        }catch (Exception e) {
            throw new InvalidToken();
        }

        if(body.getExpiration().before(new Date()))
            throw new ExpiredException();

        return Long.parseLong(body.get("userId").toString());
    }
}
