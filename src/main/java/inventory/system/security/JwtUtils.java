package inventory.system.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
public class JwtUtils {

    private static final long REFRESH_EXPIRATION_TIME_IN_MILLISEC = 1000L * 60L * 60L * 24L * 30L; // expires in 1 month
    private static final long ACCESS_EXPIRATION_TIME_IN_MILLISEC = 1000L * 60L * 5L; // expires in 5 min

    private SecretKey key;

    @Value("${spring.jwt.secret}")
    private String secretJwtString;

    @PostConstruct
    private void init() {
        byte[] keyByte = secretJwtString.getBytes(StandardCharsets.UTF_8);
        this.key = new SecretKeySpec(keyByte, "HmacSHA256");
    }

    public String generateAccessToken(String email) {
        return generateToken(email, ACCESS_EXPIRATION_TIME_IN_MILLISEC);
    }

    public String generateRefreshToken(String email) {
        return generateToken(email, REFRESH_EXPIRATION_TIME_IN_MILLISEC);
    }

    public String generateToken(String email, long tokenExpiration) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(key)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    // refresh tojen validation
    public boolean validateRefresh(String token) {
        try {
            var claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.getExpiration().after(new Date());

        } catch (JwtException e) {
            return false;
        }
    }
}
