package finance.app.corporation;

import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finance.app.corporation.service.AppCorporationService;
import finance.app.util.service.AppUtilService;
import finance.common.Controller.DefaultController;

@RestController 
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
//        	(#{pStYear},#{pStHalf}),(#{pEdYear},#{pEdHalf})
        	String stYear = ""; 
        	String edYear = "";
        	String stHalf = ""; 
        	String edHalf = "";
        	

            Map<String, Object> responseData = new HashMap<String, Object>();
                    	
        	if(commandMap.get("pStYear") != null && commandMap.get("pEdYear") != null) {
				stYear =  commandMap.get("pStYear").toString();
				edYear =  commandMap.get("pEdYear").toString();
				System.out.println("반기 값이 있다고? " + (commandMap.get("pStHalf") != null) + " / " + (commandMap.get("pEdHalf") != null));
				if(commandMap.get("searchType").equals("year")) {
        			List<Map<String,Object>> corpList = appCorporationService.getCorpListYear(commandMap);
                    System.out.println("stYear : "+stYear+" ~ edYear : "+edYear+"\n corpList : "+corpList.size());
        			responseData.put("corpList", corpList);
    				responseData.put("corpCnt", corpList.size());
				}else {
        			stHalf =  commandMap.get("pStHalf").toString();
        			edHalf =  commandMap.get("pEdHalf").toString();
                	List<Map<String,Object>> corpList = appCorporationService.getCorpListHalf(commandMap);
                    System.out.println("stYear / stHalf : "+stYear+" / "+stHalf+" ~ edYear / edHalf : "+edYear+" / "+edHalf+"\n corpList : "+corpList.size());
                	responseData.put("corpList", corpList);
    				responseData.put("corpCnt", corpList.size());
				}
        		return ResponseEntity.ok(responseData);
        	}else {
        		for(int i=0; i<yearList.size(); i++) {
        			int curYear = Integer.parseInt(yearList.get(i).get("BSNS_YEAR").toString());
        			String halfCnt = yearList.get(i).get("REPRT_CNT").toString();
        			if(halfCnt.equals("3")) {
        				stYear =  Integer.toString(curYear-1);
        				edYear =  Integer.toString(curYear);
        				

        				commandMap.put("pStYear", stYear);
        				commandMap.put("pEdYear", edYear);
            			
            			List<Map<String,Object>> corpList = appCorporationService.getCorpListYear(commandMap);
                        System.out.println("stYear : "+stYear+" ~ edYear : "+edYear+"\n corpList : "+corpList.size());
            			responseData.put("corpList", corpList);
            			responseData.put("corpCnt", corpList.size());
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
	
	@PostMapping("/detail")
    public ResponseEntity<Map<String, Object>> getCorpDetail(HttpServletRequest request, @RequestBody Map<String, Object> commandMap) throws Exception{
		commandMap = init(request, commandMap);
		
        try {
        	String stockCode = commandMap.get("pCorpCode").toString();

			
        	
            Map<String, Object> responseData = new HashMap<String, Object>();
            
            // 전일종가 가져오기
            Map<String,Object> clsPrice = appCorporationService.getCorpClsPrice(commandMap);
        	responseData.put("clsPrice", clsPrice);
        	
        	//배당정보 API 관련 파라미터
    		String corpCode = getCorpCode(stockCode).get("CORP_CODE").toString();
        	/*
			 * 	가장최근분기 기준
			 *	배당수익률 	= thstrm 그대로 표시
			 *	배당금 	= thstrm / 분기수 
			*/
    		Map<String,Object> recentReport = getRecentReport(stockCode);
        	String baseYear = recentReport.get("BSNS_YEAR").toString();
        	String reprtCode = recentReport.get("DART_REPRT_CODE").toString();
        	int cnt = Integer.parseInt(recentReport.get("DART_REPRT_CODE").toString());
        	//배당정보 가져오기
        	
        	

            String apiUrl = "https://opendart.fss.or.kr/api/alotMatter.json";
            // 파라미터 설정
            String parameters = "?crtfc_key="+openDartCertifiedKey+"&corp_code="+corpCode+"&bsns_year="+baseYear+"&reprt_code="+reprtCode;

            // URL과 파라미터 조합
            String uri = apiUrl + parameters;
            System.out.println("uri : "+uri);
            URL url = new URL(uri);
			InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
			JSONObject object = (JSONObject)JSONValue.parse(isr);
			JSONArray infoList = (JSONArray) object.get("list");
			
			if(infoList != null) {
				for(int j=0; j < infoList.size(); j++) {
					if(j == 7 ) {
						JSONObject infoObject = (JSONObject) infoList.get(j);						
			            String thstrm = infoObject.get("thstrm").toString();
			            responseData.put("allocationAvg", thstrm);
			            System.out.println("주당 배당수익률 : "+thstrm);
					}else if(j == 11) {
						JSONObject infoObject = (JSONObject) infoList.get(j);	
			            System.out.println("주당 배당금1 : "+infoObject.get("thstrm").toString());					
			            int thstrm = Integer.parseInt(infoObject.get("thstrm").toString().replace(",", ""));
			            responseData.put("allocationAmount", thstrm);
			            responseData.put("cnt", cnt);
					}
		        }
			}
			
    		return ResponseEntity.ok(responseData);
                    	
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }
	
	
	@PostMapping("/perform")
    public ResponseEntity<Map<String, Object>> getCorpPerform(HttpServletRequest request, @RequestBody Map<String, Object> commandMap) throws Exception{
		commandMap = init(request, commandMap);
		
        try {
        	String corpCode = commandMap.get("pCorpCode").toString();

        	

            Map<String, Object> responseData = new HashMap<String, Object>();
            
            List<Map<String,Object>> performList = appCorporationService.getCorpPerform(commandMap);
            List<Map<String,Object>> performYear = appCorporationService.getPerformYear(commandMap);
        	responseData.put("performList", performList);
			responseData.put("performYear", performYear);
    		return ResponseEntity.ok(responseData);
                    	
        } catch (Exception e) {
        	System.out.println("error occured : "+e);
            return ResponseEntity.badRequest().body(null);
        }
    }

}
