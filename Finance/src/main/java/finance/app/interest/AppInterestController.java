package finance.app.interest;

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

import finance.app.corporation.service.AppCorporationService;
import finance.app.interest.service.AppInterestService;
import finance.app.util.service.AppUtilService;
import finance.common.Controller.DefaultController;

@RestController 
@RequestMapping("/appApi/inter") 
public class AppInterestController extends DefaultController {

	@Autowired
	private AppUtilService appUtilService;
	
	@Autowired
	private AppInterestService appInterestService;
	
	@PostMapping("/select")
    public ResponseEntity<Map<String, Object>> getCorpList(HttpServletRequest request, @RequestBody Map<String, Object> commandMap) throws Exception{
		commandMap = init(request, commandMap);
		
        try {
        	Map<String, Object> param = new HashMap<String, Object>();

        	List<Map<String, Object>> yearList = appUtilService.getYearList(param);
//        	(#{pStYear},#{pStHalf}),(#{pEdYear},#{pEdHalf})
        	String stYear = ""; 
        	String edYear = "";
        	String stHalf = ""; 
        	String edHalf = "";
        	

            Map<String, Object> responseData = new HashMap<String, Object>();
        	
        	if(commandMap.get("pStYear") != null && commandMap.get("pEdYear") != null) {
				stYear =  commandMap.get("pStYear").toString();
				edYear =  commandMap.get("pEdYear").toString();
        		if(commandMap.get("pStHalf") != null && commandMap.get("pEdHalf") != null) {
        			stHalf =  commandMap.get("pStHalf").toString();
        			edHalf =  commandMap.get("pEdHalf").toString();
                	List<Map<String,Object>> corpList = appInterestService.getInterListHalf(commandMap);
                    System.out.println("stYear / stHalf : "+stYear+" / "+stHalf+" ~ edYear / edHalf : "+edYear+" / "+edHalf+"\n corpList : "+corpList.size());
                	responseData.put("corpList", corpList);
        		}else {
        			List<Map<String,Object>> corpList = appInterestService.getInterListYear(commandMap);
                    System.out.println("stYear : "+stYear+" ~ edYear : "+edYear+"\n corpList : "+corpList.size());
        			responseData.put("corpList", corpList);
        		}

        		return ResponseEntity.ok(responseData);
        	}else {
        		for(int i=0; i<yearList.size(); i++) {
        			int curYear = Integer.parseInt(yearList.get(i).get("BSNS_YEAR").toString());
        			String halfCnt = yearList.get(i).get("REPRT_CNT").toString();
        			if(halfCnt.equals("3")) {
        				stYear =  Integer.toString(curYear-1);
        				edYear =  Integer.toString(curYear);
        				

            			param.put("pStYear", stYear);
            			param.put("pEdYear", edYear);
            			
            			List<Map<String,Object>> corpList = appInterestService.getInterListYear(commandMap);
                        System.out.println("stYear : "+stYear+" ~ edYear : "+edYear+"\n corpList : "+corpList.size());
            			responseData.put("corpList", corpList);
            			return ResponseEntity.ok(responseData);
        			}
        		}
        	}
    		return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }
}
