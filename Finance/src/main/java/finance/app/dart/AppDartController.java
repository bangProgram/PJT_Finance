package finance.app.dart;

import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import finance.common.Controller.DefaultController;

@RestController // controller임을 알려주는 표시
@RequestMapping("/appApi/dart") 
public class AppDartController extends DefaultController {
	
	@PostMapping("/reportList")
	public ResponseEntity<Map<String, Object>> getDartReportList(HttpServletRequest request, @RequestBody Map<String, Object> commandMap) throws Exception { 
		commandMap = init(request, commandMap);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date now = new Date();
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		cal.add(cal.YEAR, -5); //5년치 데이터
		
		String bgndate = sdf.format(cal.getTime());
		String endDate = format.format(now);
		String corpCd = commandMap.get("corpCd").toString();
		String reportTy = "";
		if(commandMap.get("reportTy") != null) {
			reportTy = commandMap.get("reportTy").toString();
		}
		
		String infoURL = "https://opendart.fss.or.kr/api/list.json?";
		String crtfcKey = "fb1e1e5223c66ce1175f545ddd0ea9a15984528a";
		String params = "crtfc_key="+crtfcKey+"&corp_code="+corpCd+"&bgn_de="+bgndate+"&end_de="+endDate+"&page_no=1&page_count=100";
		if(!reportTy.equals("")) {
			params += "&pblntf_ty="+reportTy;
		}
		
		List<Map<String, Object>> getDartReportList = new ArrayList<Map<String, Object>> ();
		Map<String, Object> responseData = new HashMap<String, Object>();
		try{
			
			URL url = new URL(infoURL+params);
			InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
			JSONObject object = (JSONObject)JSONValue.parse(isr);
			JSONArray infoList = (JSONArray) object.get("list");
			
			if(infoList != null) {
				for(int j=0; j < infoList.size(); j++) {
		            JSONObject infoObject = (JSONObject) infoList.get(j);
		            Map<String, Object> paramMap = new HashMap<String, Object>();
					
		            String corp_name = infoObject.get("corp_name").toString();
		            String report_nm = infoObject.get("report_nm").toString();
		            String rcept_no = infoObject.get("rcept_no").toString();
		            String flr_nm = infoObject.get("flr_nm").toString();
		            String rcept_dt = infoObject.get("rcept_dt").toString();
		            
		            paramMap.put("seq", j+1);
		            paramMap.put("corpNm", corp_name);
		            paramMap.put("reportNm", report_nm);
		            paramMap.put("reportNo", rcept_no);
		            paramMap.put("flrNm", flr_nm);
		            paramMap.put("reportDt", rcept_dt);
		            
		            getDartReportList.add(j, paramMap);
		        }
			}
			responseData.put("message", "사업보고서 조회성공");
			responseData.put("dartReportList", getDartReportList);
			return ResponseEntity.ok(responseData);
		}catch(Exception e) {
			responseData.put("message", e.toString());
			return ResponseEntity.badRequest().body(responseData);
		}
			 

	}

}
