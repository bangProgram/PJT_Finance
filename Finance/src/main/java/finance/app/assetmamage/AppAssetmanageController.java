package finance.app.assetmamage;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import finance.app.assetmamage.service.AppAssetmanageService;
import finance.app.corporation.service.AppCorporationService;
import finance.app.login.service.AppLoginService;
import finance.app.util.service.AppUtilService;
import finance.common.Controller.DefaultController;

@RestController 
@RequestMapping("/appApi/asset") 
public class AppAssetmanageController extends DefaultController{
	@Autowired
	private AppUtilService appUtilService;
	
	@Autowired
	private AppLoginService appLoginService;
	
	@Autowired
	private AppAssetmanageService appAssetmanageService;
	
	@PostMapping("/amount")
	public ResponseEntity<Map<String, Object>> getAssetAmount(HttpServletRequest request, @RequestBody Map<String, Object> commandMap) throws Exception{ 
		commandMap = init(request, commandMap);
	
			
		//추후 사용자 검색시 사용하기위해 남겨둠
        Map<String, Object> responseData = new HashMap<String, Object>();
		// 처리된 데이터를 응답으로 보내기
        if(appLoginService.isLogin()) {
        	
    		Map<String, Object> portAmountData = appAssetmanageService.getAssetAmount(commandMap);
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
	
	@PostMapping("/merge")
    public ResponseEntity<Map<String, Object>> mergeAssetAmount(HttpServletRequest request, @RequestBody Map<String,Object> commandMap) throws Exception{
		commandMap = init(request, commandMap);

        Map<String, Object> responseData = new HashMap<String, Object>();
		// 처리된 데이터를 응답으로 보내기
        if(appLoginService.isLogin()) {
    		int resultInt = appAssetmanageService.mergeAssetAmount(commandMap);
    		if(resultInt == 1) {
        		Map<String, Object> portAmountData = appAssetmanageService.getAssetAmount(commandMap);
    			responseData.put("portAmountData", portAmountData);
    			return ResponseEntity.ok(responseData);
    		}else {
    		    return ResponseEntity.badRequest().body(null);
    		}
    		
        }
		return ResponseEntity.badRequest().body(responseData);
    }
	
	@GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAssetList() throws Exception{
		
        try {
        	Map<String, Object> param = new HashMap<String, Object>();
			String userId = appLoginService.getCurUserId();
        	param.put("curUserId", userId);

        	List<Map<String, Object>> assetList = appAssetmanageService.getAssetList(param);

            Map<String, Object> responseData = new HashMap<String, Object>();
            responseData.put("assetList", assetList);
            responseData.put("assetListCnt", assetList.size());

    		return ResponseEntity.ok(responseData);
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }
	
	
	@PostMapping("/proportion")
    public ResponseEntity<Map<String, Object>> getAssetProportion(HttpServletRequest request, @RequestBody Map<String,Object> commandMap) throws Exception{
		commandMap = init(request, commandMap);
		
        try {
        	List<Map<String, Object>> assetProportion = appAssetmanageService.getAssetProportion(commandMap);

            Map<String, Object> responseData = new HashMap<String, Object>();
            responseData.put("assetProportion", assetProportion);
            responseData.put("assetProportionCnt", assetProportion.size());

    		return ResponseEntity.ok(responseData);
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }
	
	

}
