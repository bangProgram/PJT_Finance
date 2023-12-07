package finance.app.planbook;

import java.time.LocalDate;
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

import finance.app.interest.service.AppInterestService;
import finance.app.login.service.AppLoginService;
import finance.app.planbook.service.AppPlanbookService;
import finance.app.util.service.AppUtilService;
import finance.common.Controller.DefaultController;

@RestController 
@RequestMapping("/appApi/planbook") 
public class AppPlanbookController extends DefaultController {
	
	@Autowired
	private AppUtilService appUtilService;
	
	@Autowired
	private AppLoginService appLoginservice;
	
	@Autowired
	private AppPlanbookService appPlanbookService;
	
	@GetMapping("/init")
    public ResponseEntity<Map<String, Object>> getCorpList(HttpServletRequest request) throws Exception{
        try {
        	Map<String, Object> param = new HashMap<String, Object>();
        	String curUserId = appLoginservice.getCurUserId();
        	param.put("curUserId", curUserId);

            Map<String, Object> responseData = new HashMap<String, Object>();
            List<Map<String,Object>> initList = appPlanbookService.getPlanbookList(param);
			responseData.put("initList", initList);
			responseData.put("initCnt", initList.size());
            
			return ResponseEntity.ok(responseData);
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }
	
	@PostMapping("/list")
    public ResponseEntity<Map<String, Object>> getPlanbookList(HttpServletRequest request, @RequestBody Map<String, Object> commandMap) throws Exception{
		commandMap = init(request, commandMap);
		
        try {

            List<Map<String,Object>> planbookList = appPlanbookService.getPlanbookList(commandMap);
        	

            Map<String, Object> responseData = new HashMap<String, Object>();
            responseData.put("planbookList", planbookList);
            responseData.put("planbookCnt", planbookList.size());

			return ResponseEntity.ok(responseData);
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }
	
	@PostMapping("/detail")
    public ResponseEntity<Map<String, Object>> getPlanbookDetail(HttpServletRequest request, @RequestBody Map<String, Object> commandMap) throws Exception{
		commandMap = init(request, commandMap);
		
        try {

            Map<String,Object> planDetailInfo = appPlanbookService.getPlanDetailInfo(commandMap);
        	

            Map<String, Object> responseData = new HashMap<String, Object>();
            responseData.put("planDetailInfo", planDetailInfo);

			return ResponseEntity.ok(responseData);
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }
	
	@PostMapping("/memo")
    public ResponseEntity<Map<String, Object>> getPlanDetailMemo(HttpServletRequest request, @RequestBody Map<String, Object> commandMap) throws Exception{
		commandMap = init(request, commandMap);
		
        try {

        	List<Map<String,Object>> planDetailMemo = appPlanbookService.getPlanDetailMemo(commandMap);
        	

            Map<String, Object> responseData = new HashMap<String, Object>();
            responseData.put("planDetailMemo", planDetailMemo);
            responseData.put("planDetailMemoCnt", planDetailMemo.size());

			return ResponseEntity.ok(responseData);
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }

	@PostMapping("/memo/add")
    public ResponseEntity<Map<String, Object>> addPlanMemo(HttpServletRequest request, @RequestBody Map<String, Object> commandMap) throws Exception{
		commandMap = init(request, commandMap);
		
        try {
    		Integer resultInt = appPlanbookService.addPlanMemo(commandMap);
    	    
            Map<String, Object> responseData = new HashMap<String, Object>();
            
        	
    	    if(resultInt > 0) {
    	    	List<Map<String,Object>> planDetailMemo = appPlanbookService.getPlanDetailMemo(commandMap);
	            responseData.put("planDetailMemo", planDetailMemo);
	            responseData.put("planDetailMemoCnt", planDetailMemo.size());
        		return ResponseEntity.ok(responseData);
    	    }else {
        		return ResponseEntity.badRequest().body(null);
    	    }

        	
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }
}
