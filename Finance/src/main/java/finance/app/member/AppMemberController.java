package finance.app.member;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import finance.app.member.service.AppMemberService;
import finance.cms.member.MemberVO;
import finance.cms.member.service.MemberService;
import finance.common.Controller.DefaultController;
import finance.common.Service.JwtUtil;
import freemarker.template.utility.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController // controller임을 알려주는 표시
@RequestMapping("/appApi/member") 
public class AppMemberController extends DefaultController {

	@Autowired
	private AppMemberService appMemberService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/create")
	public ResponseEntity<Map<String, Object>> createMember(@RequestBody Map<String, Object> commandMap) throws Exception{ 
		commandMap = init(commandMap);
		
		System.out.println("App Create Member : "+commandMap.toString());
		String passWd = commandMap.get("password").toString();
		Integer resultInt = 0;
		if(!passWd.equals("") && passWd != null) {

			String passWd_encode = bCryptPasswordEncoder.encode(passWd);
			commandMap.put("password", passWd_encode);
			resultInt = appMemberService.createMember(commandMap);
		}
		// 처리된 데이터를 응답으로 보내기
		String nickName = commandMap.get("userNick").toString();
        Map<String, Object> responseData = new HashMap<String, Object>();
        responseData.put("message", "회원가입 되었습니다. "+nickName+"님 환영합니다!");
		
	    if(resultInt == 1) {
	    	return ResponseEntity.ok(responseData);
	    }else {
	    	 return ResponseEntity.badRequest().body(null);
	    }
	    
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> LoginMember(@RequestBody Map<String, Object> commandMap) throws Exception{ 
		commandMap = init(commandMap);
	
		String userId = commandMap.get("userId").toString();
		String passWd = commandMap.get("password").toString();
		
		// 처리된 데이터를 응답으로 보내기
        Map<String, Object> responseData = new HashMap<String, Object>();
		
        System.out.println("로그인 사용자 확인 : "+userId);
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
	
	@PostMapping("/logout")
	public ModelAndView logoutMember(@RequestBody Map<String, Object> commandMap) throws Exception{ 
		commandMap = init(commandMap);
	
		String passWd = commandMap.get("password").toString();
		Integer resultInt = 0;
		if(!passWd.equals("") && passWd != null) {

			String passWd_encode = bCryptPasswordEncoder.encode(passWd);
			commandMap.put("password", passWd_encode);
			resultInt = appMemberService.createMember(commandMap);
		}
		
	    if(resultInt == 1) {
	    	return getMessageModel("msgAndRedirect", "회원가입이 완료되었습니다.", "/main");
	    }else {
	    	return getMessageModel("msgAndRedirect", "회원가입에 실패했습니다", "/main");
	    }
	    
	}
	
	@PostMapping("/auth")
	public ResponseEntity<Map<String, Object>> refreshMember(@RequestBody Map<String, Object> commandMap) throws Exception{ 
		commandMap = init(commandMap);
		// 처리된 데이터를 응답으로 보내기
        Map<String, Object> responseData = new HashMap<String, Object>();
	
		try {
			
			System.out.println("refreshMember 확인 시작");
			String token = commandMap.get("token").toString();
			String userId = commandMap.get("userId").toString();
			System.out.println("token 확인 시작 : " + token);
			
	        System.out.println("토큰 유무 확인 시작3");
			if(!token.equals("") && token != null) {
				System.out.println("토큰 존재함 : "+token);
				if(jwtUtil.isTokenValidation(token, userId)) {
					System.out.println(userId + "사용자 토큰 확인");
					long maintainTime = jwtUtil.extractMaintain(token);
					System.out.println("토큰 만료까지 남은 시간 : " +maintainTime + " 분.");
					if(maintainTime <= 5.0) {
						String newToken = jwtUtil.generatorToken(userId);
						responseData.put("token", newToken);
				        responseData.put("message", userId + "님 새로운 토큰이 부여되었습니다");
						return ResponseEntity.ok(responseData);
					}else {
						responseData.put("token", token);
				        responseData.put("message", userId + "님 토큰이 확인되었습니다");
						return ResponseEntity.ok(responseData);
					}
				}else {
			        responseData.put("message", "로그인 시간이 만료되었습니다. 다시 로그인 해주세요");
			        responseData.put("token", null);
			    	return ResponseEntity.badRequest().body(responseData);
				}
			}else {
				System.out.println("토큰 존재하지 않음 ");
		        responseData.put("message", "비정상적인 경로입니다 다시 확인해주세요");
		        responseData.put("token", null);
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
	
	@PostMapping("/getMember")
	public ResponseEntity<Map<String, Object>> getMemberForUserId(@RequestBody Map<String, Object> commandMap) throws Exception{ 
		commandMap = init(commandMap);
	
		String userId = commandMap.get("userId").toString();
		
		// 처리된 데이터를 응답으로 보내기
        if(!userId.equals("") && userId != null) {

    		Map<String, Object> memberVO = appMemberService.getMemberToJson(commandMap);
    		return ResponseEntity.ok(memberVO);
        }
		return ResponseEntity.badRequest().body(null);
	    
	}
		
}
