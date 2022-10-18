package src.main.finance.cms.report;

import java.time.LocalDate;
import java.util.Date;
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
		
	    return new ModelAndView("report/reportList");
	}
	
	@RequestMapping(value={"/report/select"} , method = RequestMethod.GET)
	public ModelAndView getReportList(@RequestParam Map<String, Object> commandMap) throws Exception{
		
		LocalDate now = LocalDate.now();
		int curYear = now.getYear();
		String pStartYear = (String) commandMap.get("pStartYear");
		String pEndYear = (String) commandMap.get("pEndYear");
		
		List<Map<String, Object>> searchList = reportService.getReportSearch(commandMap);
		for(int i=0; i<searchList.size(); i++) {
			String corpCode = "";
			int amountRate = 0;
			if(searchList.get(i).get("CORP_CODE") != null) corpCode = (String) searchList.get(i).get("CORP_CODE");
			if(searchList.get(i).get("ACC_NET_AMOUNT_RATE") != null) amountRate = (int) searchList.get(i).get("ACC_NET_AMOUNT_RATE");
			if(amountRate > 0) {
				
			}else {
				
			}
		}
		
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
