package finance.app.transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finance.app.assetmamage.service.AppAssetmanageService;
import finance.app.login.service.AppLoginService;
import finance.app.transaction.service.AppTransactionService;
import finance.app.util.service.AppUtilService;
import finance.common.Controller.DefaultController;

@RestController 
@RequestMapping("/appApi/trans") 
public class AppTransactionController extends DefaultController{
	
	@Autowired
	private AppUtilService appUtilService;
	
	@Autowired
	private AppLoginService appLoginService;
	
	@Autowired
	private AppTransactionService appTransactionService;
	
	@PostMapping("/record")
    public ResponseEntity<Map<String, Object>> getTransRecord(HttpServletRequest request, @RequestBody Map<String,Object> commandMap) throws Exception{
		commandMap = init(request, commandMap);
		
        try {

        	List<Map<String, Object>> assetRecord = appTransactionService.getTransRecord(commandMap);

            Map<String, Object> responseData = new HashMap<String, Object>();
            responseData.put("assetRecord", assetRecord);
            responseData.put("assetRecordCnt", assetRecord.size());

    		return ResponseEntity.ok(responseData);
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }
}
