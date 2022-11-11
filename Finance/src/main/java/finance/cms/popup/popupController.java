package finance.cms.popup;

import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import finance.cms.dart.service.DartService;
import finance.cms.interest.service.InterestService;
import finance.cms.portfolio.service.PortfolioService;
import finance.common.Controller.CommonController;

@Controller
public class popupController {
	
	@Autowired
	private PortfolioService portfolioService;
	
	@Autowired
	private InterestService interestService;
	
	@Resource(name="CommonController")
	private CommonController commonController;
	
	@RequestMapping(value={"/popup/dart/report/list"} , method = RequestMethod.GET)
	public ModelAndView getDartReportList(@RequestParam Map<String, Object> commandMap) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date now = new Date();
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		cal.add(cal.YEAR, -2); //세달 전
		
		String bgndate = sdf.format(cal.getTime());
		String endDate = format.format(now);
		String corpCd = commandMap.get("corpCd").toString();
		
		String infoURL = "https://opendart.fss.or.kr/api/list.json?";
		String crtfcKey = "fb1e1e5223c66ce1175f545ddd0ea9a15984528a";
		
		List<Map<String, Object>> getDartReportList = new ArrayList<Map<String, Object>> ();
		try{
			
			URL url = new URL(infoURL+"crtfc_key="+crtfcKey+"&corp_code="+corpCd+"&bgn_de="+bgndate+"&end_de="+endDate+"&page_no=1&page_count=100");
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
		            
		            paramMap.put("SEQ", j+1);
		            paramMap.put("CORP_NM", corp_name);
		            paramMap.put("REPRT_NM", report_nm);
		            paramMap.put("REPRT_NO", rcept_no);
		            paramMap.put("FLR_NM", flr_nm);
		            paramMap.put("REPORT_DT", rcept_dt);
		            
		            getDartReportList.add(j, paramMap);
		        }
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
			 
		JSONArray getDartReportJson = commonController.convertListToJson(getDartReportList);
		
		
		
	    ModelAndView mav = new ModelAndView();
	    String resultURL = "dart/dartReportList.popup";
	    mav.addObject("getDartReportJson", getDartReportJson);
	    mav.setViewName(resultURL);
	    
	    return mav;
	}
	
	@RequestMapping(value={"/popup/{webPath}/memo"} , method = RequestMethod.GET)
	public ModelAndView getMemoPopup(@PathVariable String webPath, @RequestParam Map<String, Object> commandMap) throws Exception {
		
		Map<String, Object> resultData = new HashMap<String, Object> ();
		
		commandMap.put("curUserId", "SYSTEM_JB");  
		
		if(webPath.equals("interest")) {
			resultData = interestService.getInterest(commandMap);
		}else if(webPath.equals("portfolio")){
			resultData = portfolioService.getPortfolio(commandMap);
		}
		
		
	    ModelAndView mav = new ModelAndView();
	    String resultURL = "memo/memoPopup.popup";
	    mav.addObject("resultData", resultData);
	    mav.addObject("webPath", webPath);
	    mav.setViewName(resultURL);
	    
	    return mav;
	}
	
	@ResponseBody
	@RequestMapping(value={"/popup/memo/cud"} , method = RequestMethod.POST)
	public Map<String, Object> regMemoCUD(@RequestParam Map<String, Object> commandMap) throws Exception{
		
		String webPath = commandMap.get("webPath").toString();
		commandMap.put("curUserId", "SYSTEM_JB");
		
		Integer resultInt = 0;
		
		System.out.println("JB : "+commandMap.toString());
		
		if(webPath.equals("interest")) {
			resultInt = interestService.insertInterestCorp(commandMap);
		}else if(webPath.equals("portfolio")){
			resultInt = portfolioService.insertPortfolioCorp(commandMap);
		}
		
	    Map<String, Object> result = new HashMap<String, Object>();
	    result.put("resultInt", resultInt);
	    
	    return result;
	}
}
