package finance.cms.interest;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import finance.cms.interest.service.InterestService;
import finance.cms.report.service.ReportService;
import finance.common.Controller.CommonController;
import finance.common.Controller.DefaultController;
import finance.common.mapper.CommonMapper;


@Controller
public class InterestController extends DefaultController{
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private InterestService interestService;
	
	@RequestMapping(value={"/cms/interest"} , method = RequestMethod.GET)
	public ModelAndView goReport(HttpServletRequest request, HttpServletResponse response , @RequestParam Map<String, Object> commandMap) throws Exception{ commandMap = init(request, commandMap);
		LocalDate now = LocalDate.now();
		int curYear = now.getYear();
		int curMonth = now.getMonthValue();
		
		String yearString = "";
		String quaterString = "";
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pReportCd", "0302");
		List<Map<String, Object>> quaterList = reportService.getBsnsYearList(paramMap);
		paramMap.put("pReportCd", "0304");
		List<Map<String, Object>> yearList = reportService.getBsnsYearList(paramMap);
		String[] pYearList = new String [5];
		String[] pQuaterList = new String [5];
		
		for(int i=0; i<5; i++ ) {
			pYearList[i] = yearList.get(i).get("BSNS_YEAR").toString();			
			pQuaterList[i] = quaterList.get(i).get("HAEDER_NM").toString();	
			
			if(i==0) {
				yearString += yearList.get(i).get("BSNS_YEAR");
				quaterString += quaterList.get(i).get("HAEDER_NM");
			}else {
				yearString += "," + yearList.get(i).get("BSNS_YEAR");
				quaterString += "," + quaterList.get(i).get("HAEDER_NM");
			}
		}
		
		commandMap.put("pEndYear", curYear);
		commandMap.put("pStartYear", (curYear-6));
		commandMap.put("pYearList", pYearList);
		commandMap.put("pQuaterList", pQuaterList);
		commandMap.put("pReportCd", "11012");
		List<Map<String, Object>> quaterReprtList = interestService.getReportList(commandMap);
		commandMap.put("pReportCd", "11011");
		List<Map<String, Object>> yearReprtList = interestService.getReportList(commandMap);
		
		System.out.println("JB : "+quaterList.toString());
		System.out.println("JB : "+yearString);
		
		JSONArray quaterReprtJson =  convertListToJson(quaterReprtList);
		JSONArray yearReprtJson = convertListToJson(yearReprtList);
		
		ModelAndView mav = new ModelAndView();
	    String resultURL = "cms/interest/interestList.jsp";
	    mav.addObject("yearReprtList", yearReprtList);
	    mav.addObject("quaterReprtList", quaterReprtList);
	    mav.addObject("yearReprtJson", yearReprtJson);
	    mav.addObject("quaterReprtJson", quaterReprtJson);
	    mav.addObject("yearString", yearString);
	    mav.addObject("yearList", yearList);
	    mav.addObject("quaterString", quaterString);
	    mav.addObject("quaterList", quaterList);
	    mav.addObject("pEndYear", curYear);
	    mav.addObject("pStartYear", (curYear-6));
	    mav.setViewName(resultURL);
	    
	    return mav;
	}
	
	@ResponseBody
	@RequestMapping(value={"/cms/interest/add/cud"} , method = RequestMethod.POST)
	public Map<String, Object> addInterestCorp(HttpServletRequest request, HttpServletResponse response , @RequestParam Map<String, Object> commandMap) throws Exception{ commandMap = init(request, commandMap);
		
		LocalDate now = LocalDate.now();
		int curYear = now.getYear();		//2022
		int curMonth = now.getMonthValue();	//10
		
		Integer resultInt = interestService.insertInterestCorp(commandMap);
		
	    Map<String, Object> result = new HashMap<String, Object>();
	    result.put("resultInt", resultInt);
	    
	    return result;
	}
	
	@RequestMapping(value={"/cms/interest/del/cud"} , method = RequestMethod.POST)
	public ModelAndView delInterestCorp(HttpServletRequest request, HttpServletResponse response , @RequestParam Map<String, Object> commandMap) throws Exception{ commandMap = init(request, commandMap);
		
		LocalDate now = LocalDate.now();
		int curYear = now.getYear();		//2022
		int curMonth = now.getMonthValue();	//10
		
		System.out.println("JB : "+commandMap.toString());
		
		Integer resultInt = interestService.deleteInterestCorp(commandMap);
		System.out.println("jN");
		
	    Map<String, Object> result = new HashMap<String, Object>();
	    result.put("resultInt", resultInt);
	    
	    ModelAndView mav = new ModelAndView();
	    String resultURL = "redirect:/interest";
	    mav.setViewName(resultURL);
	    
	    return mav;
	}
	
}
