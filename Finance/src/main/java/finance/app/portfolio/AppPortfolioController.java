package finance.app.portfolio;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finance.app.login.service.AppLoginService;
import finance.app.member.service.AppMemberService;
import finance.app.portfolio.service.AppPortfolioService;
import finance.common.Controller.DefaultController;

@RestController // controller임을 알려주는 표시
@RequestMapping("/appApi/portfolio") 
public class AppPortfolioController extends DefaultController {
	
	@Autowired
	private AppPortfolioService appPortfolioService;
	
	@Autowired
	private AppLoginService appLoginService; 
	
	@PostMapping("/getPortAmount")
	public ResponseEntity<Map<String, Object>> getPortAmount(HttpServletRequest request, @RequestBody Map<String, Object> commandMap) throws Exception{ 
		commandMap = init(request, commandMap);
	
			
		//추후 사용자 검색시 사용하기위해 남겨둠
		String userId = "";
		if(commandMap.get("pUserId") != null) {
			
		}

        Map<String, Object> responseData = new HashMap<String, Object>();
		// 처리된 데이터를 응답으로 보내기
        if(appLoginService.isLogin()) {
        	
    		Map<String, Object> portAmountData = appPortfolioService.getPortAmount(commandMap);
    		if(portAmountData != null) {
    			responseData.put("message", "포트폴리오 정보를 가져왔습니다.");
    			responseData.put("portAmountData", portAmountData);
    			return ResponseEntity.ok(responseData);
    		}else {
    	        responseData.put("message", "사용자 정보가 존재하지 않습니다.");
    	        responseData.put("portAmountData", null);
    		    return ResponseEntity.ok(responseData);
    		}
    		
        }
        responseData.put("message", "로그인 정보가 올바르지 않습니다.");
		return ResponseEntity.badRequest().body(responseData);
	}
	
	@PostMapping("/updatePortfolio")
	public ResponseEntity<Map<String, Object>> updatePortfolio(HttpServletRequest request, @RequestBody Map<String, Object> commandMap) throws Exception{ 
		commandMap = init(request, commandMap);
	
		String userId = commandMap.get("userId").toString();

        Map<String, Object> responseData = new HashMap<String, Object>();
		// 처리된 데이터를 응답으로 보내기
        if(appLoginService.isLogin()) {

    		int resultInt = appPortfolioService.insertPortfolioAsset(commandMap);
    		if(resultInt == 1) {
    			return ResponseEntity.ok(null);
    		}else {
    		    return ResponseEntity.badRequest().body(null);
    		}
    		
        }
		return ResponseEntity.badRequest().body(responseData);
	}
}
