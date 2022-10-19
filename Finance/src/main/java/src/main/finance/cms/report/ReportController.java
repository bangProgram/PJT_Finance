package src.main.finance.cms.report;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import src.main.finance.cms.report.service.ReportService;

@Controller
public class ReportController {
	
	private ReportService reportService;

	@RequestMapping(value={"/report"} , method = RequestMethod.GET)
	public ModelAndView goReport(@RequestParam Map<String, Object> commandMap) throws Exception{
		LocalDate now = LocalDate.now();
		int curYear = now.getYear();
		
		System.out.println("JB : "+curYear);
		
		ModelAndView mav = new ModelAndView();
	    String resultURL = "report/reportList";
	    mav.addObject("curYear", curYear);
	    mav.setViewName(resultURL);
	    
	    return mav;
	}
	
	@RequestMapping(value={"/report/select"} , method = RequestMethod.GET)
	public ModelAndView getReportList(@RequestParam Map<String, Object> commandMap) throws Exception{
		
		LocalDate now = LocalDate.now();
		int curYear = now.getYear();
		
		System.out.println("JB : "+curYear);
		Integer pStartYear = (Integer) commandMap.get("pStartYear");
		Integer pEndYear = (Integer) commandMap.get("pEndYear");
		Integer chkYear = pEndYear;
		List<Integer> pYearList = new ArrayList<Integer>();
		
		for(int i=1; pStartYear <= chkYear; i++ ) {
			pYearList.add(chkYear);
			chkYear -= i;
		}
		
		commandMap.put("pYearList", pYearList);
		
		Integer chkAccRate = (Integer) commandMap.get("chkAccRate");
		
		List<Map<String, Object>> searchList = reportService.getReportSearch(commandMap);
		List<String> chkCorpList = new ArrayList<String>();
		
		String corpCode = "";
		int amountRate = 0;
		boolean chkResult = false;
		
		for(int i=0; i<searchList.size(); i++) {
			
			if(searchList.get(i).get("ACC_NET_AMOUNT_RATE") != null) amountRate = (int) searchList.get(i).get("ACC_NET_AMOUNT_RATE");
			
			if(corpCode == "") {
				if(amountRate >= chkAccRate) {
					chkResult = true;
				}else {
					corpCode = (String) searchList.get(i).get("CORP_CODE");
					chkResult = false;
					break;
				}
			} else {
				chkResult = false;
				break;
			}

			chkCorpList.add((String) searchList.get(i).get("CORP_CODE"));
			corpCode = "";
		}
		
		commandMap.put("chkCorpList", chkCorpList);
		List<Map<String, Object>> resultList = reportService.getReportList(commandMap);
	    
	    ModelAndView mav = new ModelAndView();
	    String resultURL = "report/reportList";
	    mav.addObject("resultList", resultList);
	    mav.setViewName(resultURL);
	    
	    return mav;
	}
	
	@RequestMapping(value = "/report/detail/view", method = RequestMethod.POST)
	public ModelAndView getReportDetail(@RequestParam Map<String, Object> commandMap) throws Exception {

	    List<Map<String, Object>> resultList = reportService.getReportDetailList(commandMap);
	    
	    ModelAndView mav = new ModelAndView();
	    String resultURL = "report/detail/repDetview";
	    mav.addObject("resultList", resultList);
	    mav.setViewName(resultURL);
	    
	    return mav;
	}
}
