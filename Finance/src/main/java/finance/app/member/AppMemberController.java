package finance.app.member;

import java.util.HashMap;
import java.util.Map;

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
import finance.cms.member.service.MemberService;
import finance.common.Controller.DefaultController;
import freemarker.template.utility.StringUtil;

@RestController // controller임을 알려주는 표시
@RequestMapping("/appApi/member") 
public class AppMemberController extends DefaultController {

	@Autowired
	private AppMemberService appMemberService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
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
	public ModelAndView LoginMember(@RequestParam Map<String, Object> commandMap) throws Exception{ 
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
	
	@PostMapping("/logout")
	public ModelAndView logoutMember(@RequestParam Map<String, Object> commandMap) throws Exception{ 
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
		
}
