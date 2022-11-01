package finance.cms.interest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import finance.cms.interest.service.InterestService;
import finance.cms.report.service.ReportService;


@Controller
public class InterestController {
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private InterestService interestService;

	@RequestMapping(value={"/interest"} , method = RequestMethod.GET)
	public ModelAndView goReport(@RequestParam Map<String, Object> commandMap) throws Exception{
		LocalDate now = LocalDate.now();
		int curYear = now.getYear();
		int curMonth = now.getMonthValue();
		
		String yearString = "";
		String quaterString = "";
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<Map<String, Object>> quaterList = reportService.getBsnsYearList(paramMap);
		paramMap.put("pReportCd", "11011");
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
		commandMap.put("pStartYear", (curYear-5));
		commandMap.put("pYearList", pYearList);
		commandMap.put("pQuaterList", pQuaterList);
		List<Map<String, Object>> yearReprtList = reportService.getReportList(commandMap);
		List<Map<String, Object>> quaterReprtList = reportService.getReportList(commandMap);
		
		System.out.println("JB : "+quaterList.toString());
		System.out.println("JB : "+yearString);
		
		ModelAndView mav = new ModelAndView();
	    String resultURL = "interest/interestList";
	    mav.addObject("yearReprtList", yearReprtList);
	    mav.addObject("quaterReprtList", quaterReprtList);
	    mav.addObject("yearString", yearString);
	    mav.addObject("yearList", yearList);
	    mav.addObject("quaterString", quaterString);
	    mav.addObject("quaterList", quaterList);
	    mav.addObject("pEndYear", curYear);
	    mav.addObject("pStartYear", (curYear-5));
	    mav.setViewName(resultURL);
	    
	    return mav;
	}
	
	@ResponseBody
	@RequestMapping(value={"/interest/add/cud"} , method = RequestMethod.GET)
	public Map<String, Object> getReportList(@RequestParam Map<String, Object> commandMap) throws Exception{
		
		LocalDate now = LocalDate.now();
		int curYear = now.getYear();		//2022
		int curMonth = now.getMonthValue();	//10
		
		System.out.println("JB : "+commandMap.toString());
		
		Integer resultInt = interestService.insertInterestCorp(commandMap);
		
	    Map<String, Object> result = new HashMap<String, Object>();
	    result.put("resultInt", resultInt);
	    
	    return result;
	}
}
