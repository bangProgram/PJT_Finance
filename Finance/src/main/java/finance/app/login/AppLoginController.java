package finance.app.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finance.app.member.service.AppMemberService;
import finance.common.Controller.DefaultController;
import finance.common.Service.JwtUtilService;
import io.jsonwebtoken.ExpiredJwtException;


@RestController // controller임을 알려주는 표시
@RequestMapping("/appApi/auth") 
public class AppLoginController extends DefaultController {
	@Autowired
	private AppMemberService appMemberService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private JwtUtilService jwtUtil;
	
	@PostMapping("/loginPorc")
	public ResponseEntity<Map<String, Object>> LoginMember(HttpServletRequest request, @RequestBody Map<String, Object> commandMap) throws Exception{ 
		commandMap = init(request, commandMap);
		
		String device_IP = request.getRemoteAddr();
		
		String userId = commandMap.get("userId").toString();
		String passWd = commandMap.get("password").toString();
		
		// 처리된 데이터를 응답으로 보내기
        Map<String, Object> responseData = new HashMap<String, Object>();
		
        System.out.println("로그인 사용자 확인 : "+userId + " / IP : "+device_IP);
		Map<String, Object> memberVO = appMemberService.getMemberToJson(commandMap);
		//사용자 정보 있을경우 진입
		if(memberVO != null) {
			//입력받은 비밀번호와 사용자 비밀번호가 일치 할 경우 진입
			System.out.println(passWd +" : 입력 / 저장 : "+memberVO.get("password"));
			if(bCryptPasswordEncoder.matches(passWd, memberVO.get("password").toString())) {
				System.out.println("비밀번호 matches 확인");
				String token = jwtUtil.generatorToken(userId);
				System.out.println("토큰 발급 확인 : "+token);
								
				responseData.put("memberData", memberVO);
				responseData.put("token", token);
				responseData.put("message", userId+"님 환영합니다.");
				return ResponseEntity.ok(responseData);
			}else{
				appMemberService.updateErrorCnt(commandMap);
		        responseData.put("message", "사용자 정보가 일치하지 않습니다.");
		        responseData.put("token", null);
		    	return ResponseEntity.badRequest().body(responseData);
			}
		}else {
	        responseData.put("message", "사용자 정보가 존재하지 않습니다.");
	        responseData.put("token", null);
	    	return ResponseEntity.badRequest().body(responseData);
		}
	    
	}
	
	@PostMapping("/logoutPorc")
	public ResponseEntity<Map<String, Object>> logoutMember(HttpServletRequest request, @RequestBody Map<String, Object> commandMap) throws Exception{ 
		commandMap = init(request, commandMap);
		
		// 처리된 데이터를 응답으로 보내기
        Map<String, Object> responseData = new HashMap<String, Object>();
	
        responseData.put("token", null);
		return ResponseEntity.ok(responseData);
	    
	}
	
	@PostMapping("/refreshMember")
	public ResponseEntity<Map<String, Object>> refreshMember(HttpServletRequest request, @RequestBody Map<String, Object> commandMap) throws Exception{ 
		commandMap = init(request, commandMap);
		// 처리된 데이터를 응답으로 보내기
        Map<String, Object> responseData = new HashMap<String, Object>();
	
		try {
			System.out.println("refreshMember 확인 시작");
			String token = commandMap.get("token").toString();
			String userId = commandMap.get("userId").toString();
			System.out.println("클라이언트 토큰 : " + token);
			
	        
			/*
			 * 클라이언트 부여된 토큰과 서버에 저장된 토큰의 정합성 체크
			*/
			if(!token.equals("") && token != null) {
				System.out.println("사용자 정합성 : "+jwtUtil.isTokenValidation(token, userId));
				if(jwtUtil.isTokenValidation(token, userId)) {
					System.out.println(userId + "사용자 토큰 확인");
					long maintainTime = jwtUtil.extractMaintain(token);
					System.out.println("토큰 만료까지 남은 시간 : " +maintainTime + " 분.");
					/*
					 * 토큰 유지시간 5분 이하일 경우 토큰 재발급
					*/
					if(maintainTime <= 5.0) {
						String newToken = jwtUtil.generatorToken(userId);
						responseData.put("token", newToken);
				        responseData.put("message", userId + "님 새로운 토큰이 부여되었습니다");
					}else {
						responseData.put("token", token);
				        responseData.put("message", userId + "님 토큰이 확인되었습니다");
					}
				}else {
			        responseData.put("message", "로그인 시간이 만료되었습니다. 다시 로그인 해주세요");
			        responseData.put("token", null);
				}
			}else {
				System.out.println("토큰 존재하지 않음 ");
		        responseData.put("message", "비정상적인 경로입니다 다시 확인해주세요");
		        responseData.put("token", null);
			}
			
			Object result = responseData.get("token");
			System.out.print("Refresh result : "+result + " / ");
			
			if(result != null) {
				System.out.println(result.toString());
				return ResponseEntity.ok(responseData);
			}else {
		    	return ResponseEntity.badRequest().body(responseData);
			}
		}
		catch (ExpiredJwtException ex) {
			// 토큰이 만료되었을 때 처리할 내용을 여기에 작성합니다.
		    System.out.println("토큰이 만료되었습니다.");
	        responseData.put("message", "토큰이 만료되었습니다.");
	        responseData.put("token", null);
		    return ResponseEntity.badRequest().body(responseData);
		} catch (Exception e) {
		    // 다른 예외가 발생했을 때 처리할 내용을 여기에 작성합니다.
		    System.out.println("예외 발생: " + e.getMessage());
	        responseData.put("message", "예외 발생: " + e.getMessage());
	        responseData.put("token", null);
		    return ResponseEntity.badRequest().body(responseData);
		}
		
	}
	
}