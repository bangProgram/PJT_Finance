package finance.app.trade;

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
import org.springframework.web.bind.annotation.RestController;

import finance.app.assetmamage.service.AppAssetmanageService;
import finance.app.login.service.AppLoginService;
import finance.app.trade.service.AppTradeService;
import finance.app.util.service.AppUtilService;
import finance.common.Controller.DefaultController;

@RestController 
@RequestMapping("/appApi/trade") 
public class AppTradeController extends DefaultController{
	
	@Autowired
	private AppUtilService appUtilService;
	
	@Autowired
	private AppLoginService appLoginService;
	
	@Autowired
	private AppTradeService appTradeactionService;
	
	@GetMapping("/init")
    public ResponseEntity<Map<String, Object>> initTradeList(HttpServletRequest request) throws Exception{
        try {
        	Map<String, Object> param = new HashMap<String, Object>();
        	String curUserId = appLoginService.getCurUserId();
        	param.put("curUserId", curUserId);

            Map<String, Object> responseData = new HashMap<String, Object>();
            List<Map<String,Object>> initList = appTradeactionService.initTradeList(param);
			responseData.put("initList", initList);
			responseData.put("initCnt", initList.size());
            
			return ResponseEntity.ok(responseData);
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }
	
	@PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addTrade(HttpServletRequest request, @RequestBody Map<String, Object> commandMap) throws Exception{
		commandMap = init(request, commandMap);
		
        try {
    		Integer resultInt = appTradeactionService.addTrade(commandMap);
    		
    	    
            Map<String, Object> responseData = new HashMap<String, Object>();
        	
    	    if(resultInt > 0) {
        		return ResponseEntity.ok(responseData);
    	    }else {
        		return ResponseEntity.badRequest().body(null);
    	    }

        	
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }
	
	@PostMapping("/del")
    public ResponseEntity<Map<String, Object>> delTrade(HttpServletRequest request, @RequestBody Map<String, Object> commandMap) throws Exception{
		commandMap = init(request, commandMap);
		
        try {
    		Integer resultInt = appTradeactionService.delTrade(commandMap);
    		
    	    
            Map<String, Object> responseData = new HashMap<String, Object>();
        	

    		return ResponseEntity.ok(responseData);        	
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }
	
	@GetMapping("/corpList")
    public ResponseEntity<Map<String, Object>> getTradeCorpList() throws Exception{
		
        try {
        	Map<String, Object> param = new HashMap<String, Object>();
			String userId = appLoginService.getCurUserId();
        	param.put("curUserId", userId);

        	List<Map<String, Object>> corpList = appTradeactionService.getTradeCorpList(param);

            Map<String, Object> responseData = new HashMap<String, Object>();
            responseData.put("corpList", corpList);
            responseData.put("corpListCnt", corpList.size());

    		return ResponseEntity.ok(responseData);
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }
	
	@PostMapping("/record")
    public ResponseEntity<Map<String, Object>> getTradeRecord(HttpServletRequest request, @RequestBody Map<String,Object> commandMap) throws Exception{
		commandMap = init(request, commandMap);
		
        try {

        	List<Map<String, Object>> assetRecord = appTradeactionService.getTradeRecord(commandMap);

            Map<String, Object> responseData = new HashMap<String, Object>();
            responseData.put("assetRecord", assetRecord);
            responseData.put("assetRecordCnt", assetRecord.size());

    		return ResponseEntity.ok(responseData);
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }
	
	@PostMapping("/corpDetail/add")
    public ResponseEntity<Map<String, Object>> addTradeCorpDetail(HttpServletRequest request, @RequestBody Map<String,Object> commandMap) throws Exception{
		commandMap = init(request, commandMap);
		String corpCode = commandMap.get("pCorpCode").toString();
		
        try {

    		appTradeactionService.addTradeCorpDetail(commandMap);
        	
            Map<String, Object> responseData = new HashMap<String, Object>();
            
            //거래일지 작성 후 기업상세 거래이력 상태 변경
        	Map<String, Object> param = new HashMap<String, Object>();
        	param.put("curUserId", appLoginService.getCurUserId());
        	param.put("sCorpCode", corpCode);
            List<Map<String, Object>> assetRecord = appTradeactionService.getTradeRecord(param);
            
            responseData.put("assetRecord", assetRecord);
            responseData.put("assetRecordCnt", assetRecord.size());
            
    		return ResponseEntity.ok(responseData);
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }
	
	@PostMapping("/corpDetail/del")
    public ResponseEntity<Map<String, Object>> delTradeCorpDetail(HttpServletRequest request, @RequestBody Map<String,Object> commandMap) throws Exception{
		commandMap = init(request, commandMap);
		String corpCode = commandMap.get("pCorpCode").toString();
		
        try {
        	appTradeactionService.delTradeCorpDetail(commandMap);

        	Map<String, Object> responseData = new HashMap<String, Object>();
            
            //거래일지 작성 후 기업상세 거래이력 상태 변경
        	Map<String, Object> param = new HashMap<String, Object>();
        	param.put("curUserId", appLoginService.getCurUserId());
        	param.put("sCorpCode", corpCode);
            List<Map<String, Object>> assetRecord = appTradeactionService.getTradeRecord(param);

            responseData.put("assetRecord", assetRecord);
            responseData.put("assetRecordCnt", assetRecord.size());
            
    		return ResponseEntity.ok(responseData);
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }
}
