package finance.common.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// 토큰을 발행 및 정보추출용
@Service
public class JwtUtil {

    // final 은 상수 변경x
    private final String SECURITY_KEY = "JB@secret";

    // 1000 => 1초
    private final long VALIDATEIEM = 1000 * 60 ; // 1분

    // 토큰 생성
    public String generatorToken(String userId) {
		System.out.println("토큰발급 함수 내부 들어옴");
    	
        Map<String, Object> map = new HashMap<String, Object>();
        String token = Jwts.builder()
                .setClaims(map) // 빈 map을 만들어줘야함
                .setSubject(userId)
                // 발행시간
                .setIssuedAt(new Date(System.currentTimeMillis())) 
                // 만료시간
                .setExpiration(new Date(System.currentTimeMillis() + VALIDATEIEM)) 
                // 페이로드
                .claim("userId", userId)
                // 알고리즘 방식, 키
                .signWith(SignatureAlgorithm.HS256, SECURITY_KEY)
                .compact();
        return token;
    }

    // 토큰에서 아이디 추출
    public String extractUserId(String token) {
    	final Claims claims = Jwts.parser().setSigningKey(SECURITY_KEY).
    	        parseClaimsJws(token).getBody();
    	
    	return claims.getSubject();
    }

    // 토큰에서 만료시간 추출
    public Date extractExpiration(String token) {
    	final Claims claims = Jwts.parser().setSigningKey(SECURITY_KEY).
    	        parseClaimsJws(token).getBody();
    	
    	return claims.getExpiration();
    }

    // 유효시간 체크
    public boolean isTokenNotExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 토큰이 유효한지 체크
    public boolean isTokenValidation(String token, String uid) {
        String userId = extractUserId(token);
        if (userId.equals(uid) && isTokenNotExpired(token)) {
            return true;
        }
        return false;
    }
}