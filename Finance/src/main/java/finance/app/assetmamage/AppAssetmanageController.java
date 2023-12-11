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
	
	@PostMapping("/record")
    public ResponseEntity<Map<String, Object>> getAssetRecord(HttpServletRequest request, @RequestBody Map<String,Object> commandMap) throws Exception{
		commandMap = init(request, commandMap);
		
        try {

        	List<Map<String, Object>> assetRecord = appAssetmanageService.getAssetRecord(commandMap);

            Map<String, Object> responseData = new HashMap<String, Object>();
            responseData.put("assetRecord", assetRecord);
            responseData.put("assetRecordCnt", assetRecord.size());

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
