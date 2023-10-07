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
    private final long VALIDATEIEM = 1000 * 60 * 7 ; // 7분

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
    
    // 토큰 유지시간 추출 (분단위 기준으로 추출 ex: 269124 -> 4.4854)
    public long extractMaintain(String token) {
    	final Claims claims = Jwts.parser().setSigningKey(SECURITY_KEY).
    	        parseClaimsJws(token).getBody();
    	
    	long expTime = claims.getExpiration().getTime();
    	long curTime = System.currentTimeMillis();
    	long maintainTime = (expTime - curTime)/1000/60;
    	
    	return maintainTime;
    }

    // 만료 여부 확인
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 토큰 유효성 체크 ( 사용자 정합성 / 토큰만료 여부 )
    public boolean isTokenValidation(String token, String uid) {
        String userId = extractUserId(token);
        System.out.println("user chk : "+userId.equals(uid) + " / token chk : " + isTokenExpired(token));
        if (userId.equals(uid) && !isTokenExpired(token)) {
            return true;
        }
        return false;
    }
}