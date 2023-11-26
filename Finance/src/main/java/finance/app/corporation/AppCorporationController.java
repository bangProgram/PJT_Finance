package finance.app.corporation;

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

import finance.app.corporation.service.AppCorporationService;
import finance.app.util.service.AppUtilService;
import finance.common.Controller.DefaultController;

@RestController // controller임을 알려주는 표시
@RequestMapping("/appApi/corp") 
public class AppCorporationController extends DefaultController{
	
	@Autowired
	private AppUtilService appUtilService;
	
	@Autowired
	private AppCorporationService appCorporationService;
	
	@PostMapping("/select")
    public ResponseEntity<Map<String, Object>> getCorpList(HttpServletRequest request, @RequestBody Map<String, Object> commandMap) throws Exception{
		commandMap = init(request, commandMap);
		
        try {
        	Map<String, Object> param = new HashMap<String, Object>();

        	List<Map<String, Object>> yearList = appUtilService.getYearList(param);
        	String stYear = ""; 
        	String edYear = "";
        	
        	if(commandMap.get("stYear") != null && commandMap.get("edYear") != null) {
        		stYear =  commandMap.get("stYear").toString();
        		edYear =  commandMap.get("edYear").toString();
        	}else {
        		for(int i=0; i<yearList.size(); i++) {
        			int curYear = Integer.parseInt(yearList.get(i).get("BSNS_YEAR").toString());
        			String halfCnt = yearList.get(i).get("REPRT_CNT").toString();
        			if(halfCnt.equals("3")) {
        				stYear =  Integer.toString(curYear-1);
        				edYear =  Integer.toString(curYear);
        			}
            		
        		}
        	}
        	
        	List<Map<String,Object>> corpList = appCorporationService.getCorpList(commandMap);

            Map<String, Object> responseData = new HashMap<String, Object>();
            System.out.println("corpList : "+corpList.toString());
            responseData.put("corpList", corpList);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }

}
