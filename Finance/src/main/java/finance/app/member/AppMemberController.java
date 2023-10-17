package finance.app.member;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpSession;

import org.apache.taglibs.standard.lang.jstl.Literal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import finance.app.member.service.AppMemberService;
import finance.cms.member.MemberVO;
import finance.cms.member.service.MemberService;
import finance.common.Controller.DefaultController;
import finance.common.Service.JwtUtilService;
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
	private JwtUtilService jwtUtil;
	
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
		String userName = commandMap.get("userName").toString();
        Map<String, Object> responseData = new HashMap<String, Object>();
        responseData.put("message", "회원가입 되었습니다. "+userName+"님 환영합니다!");
		
	    if(resultInt == 1) {
	    	return ResponseEntity.ok(responseData);
	    }else {
	    	 return ResponseEntity.badRequest().body(null);
	    }
	    
	}
	
	@PostMapping("/update")
	public ResponseEntity<Map<String, Object>> updateMember(@RequestBody Map<String, Object> commandMap) throws Exception{ 
		commandMap = init(commandMap);

        Map<String, Object> responseData = new HashMap<String, Object>();
        
		try {
			System.out.println("App Update Member : "+commandMap.toString());
			Integer resultInt = 0;
			// 처리된 데이터를 응답으로 보내기
			String updateCategory = "";
			
			if(!commandMap.containsKey("userId")) {
		    	responseData.put("message", "사용자 정보가 없는 비정상적인 파라미터입니다");
				return ResponseEntity.badRequest().body(responseData);
			}
			
			if(!commandMap.isEmpty()) {
				Iterator<String> keys = commandMap.keySet().iterator();
				while(keys.hasNext()) {
					updateCategory += keys.next() + " ";
				}
				resultInt = appMemberService.updateMember(commandMap);
			}
			
			System.out.println("다음 카테고리의 사용자 정보가 변경되었습니다\n"+updateCategory);
		    if(resultInt == 1) {
		    	responseData.put("message", "다음 카테고리의 사용자 정보가 변경되었습니다\n"+updateCategory);
				Map<String, Object> memberVO = appMemberService.getMemberToJson(commandMap);
		    	responseData.put("memberData", memberVO);
		    	return ResponseEntity.ok(responseData);
		    }else {
		    	responseData.put("message", "사용자 정보 수정에 실패했습니다.");
		    	return ResponseEntity.badRequest().body(responseData);
		    }
		}catch(Exception e) {
			responseData.put("message", "Server Error "+e.toString());
			return ResponseEntity.badRequest().body(responseData);
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
	
		Integer resultInt = 0;
		
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

        Map<String, Object> responseData = new HashMap<String, Object>();
		// 처리된 데이터를 응답으로 보내기
        if(!userId.equals("") && userId != null) {

    		Map<String, Object> memberVO = appMemberService.getMemberToJson(commandMap);
    		if(memberVO != null) {
    			return ResponseEntity.ok(memberVO);
    		}else {
    	        responseData.put("message", "사용자 정보가 존재하지 않습니다.");
    		    return ResponseEntity.badRequest().body(responseData);
    		}
    		
        }
        responseData.put("message", "사용자 정보가 존재하지 않습니다.");
		return ResponseEntity.badRequest().body(responseData);
	    
	}
	
	@PostMapping("/uploadAvatar")
	public ResponseEntity<Map<String, Object>> uploadAvatar(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId) throws Exception{ 
		System.out.println("여기는 들어오나?");
		 Map<String, Object> commandMap =  new HashMap<String, Object>();
	
		commandMap.put("userId", userId);
		// 처리된 데이터를 응답으로 보내기
		
		System.out.println("파일 여부 확인 : " + (file != null));
		if (file != null) {
			System.out.println("사용자 명 : "+userId+ " / 파일 명 : "+file.getOriginalFilename());
			String uploadDirectory = "C:\\PJT_Finance\\Finance\\src\\main\\webapp\\resources\\uploads\\members\\avatars\\";
            String fileName = file.getOriginalFilename();
            String fullPath = uploadDirectory + fileName;
            System.out.println("파일 경로 : "+fullPath);
            
            commandMap.put("fileName", fileName);

            // 지정된 디렉토리가 없다면 생성
            File uploadDir = new File(uploadDirectory);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 파일을 저장할 경로 설정
            File dest = new File(fullPath);

            try {
                // 파일을 저장
                file.transferTo(dest);
                System.out.println("파일 실제 저장 후");
                appMemberService.updateMember(commandMap);
        		return ResponseEntity.ok(null);
            } catch (IOException e) {
                e.printStackTrace();
        		return ResponseEntity.badRequest().body(null);
            }
        } else {
    		return ResponseEntity.badRequest().body(null);
        }
	    
	}
	
	@GetMapping("/downloadAvatar/{imageName}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String imageName) throws Exception{
        
        try {

    		String directory = "C:\\PJT_Finance\\Finance\\src\\main\\webapp\\resources\\uploads\\members\\avatars\\";
    		String fullPath = directory+imageName+".jpeg";
    		System.out.println("JB fullPath : "+fullPath);
    		FileSystemResource resource = new FileSystemResource(fullPath);

    		System.out.println("JB resource.exists() : "+resource.exists());
    		if (!resource.exists()) {
                throw new Exception();
            }
    		
            HttpHeaders header = new HttpHeaders();
            Path filePath = null;
            filePath = Paths.get(fullPath);
            header.add("Content-Type", Files.probeContentType(filePath));
            return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }
		
}
