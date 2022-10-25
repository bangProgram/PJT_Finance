package finance.cms.report;

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

import finance.cms.report.service.ReportService;


@Controller
public class ReportController {
	
	@Autowired
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
	
	@ResponseBody
	@RequestMapping(value={"/report/select"} , method = RequestMethod.POST)
	public Map<String, Object> getReportList(@RequestParam Map<String, Object> commandMap) throws Exception{
		
		LocalDate now = LocalDate.now();
		int curYear = now.getYear();
		
		System.out.println("JB1 : "+commandMap.toString());
		System.out.println("JB2 : "+commandMap.get("pAccountIds").toString());
		System.out.println("JB3 : "+commandMap.get("pYearList").toString());
		
		String[] pAccountIds = commandMap.get("pAccountIds").toString().split(",");
		String[] pYearList = commandMap.get("pYearList").toString().split(",");
		String[] chkYearList = commandMap.get("chkYearList").toString().split(",");
		
		commandMap.put("pAccountCnt", pAccountIds.length);
		commandMap.put("pAccountIds", pAccountIds);
		commandMap.put("pYearList", pYearList);
		commandMap.put("chkYearList", chkYearList);
		
		List<Map<String, Object>> searchList = reportService.getReportSearch(commandMap);
		List<String> chkCorpList = new ArrayList<String>();
		for(int i=0; i<searchList.size(); i++) {
			chkCorpList.add(searchList.get(i).get("CORP_CODE").toString());
		}
		
		commandMap.put("chkCorpList", chkCorpList); List<Map<String, Object>>
		resultList = reportService.getReportList(commandMap);
		
	    Map<String, Object> result = new HashMap<String, Object>();
	    result.put("resultList", resultList);
	    result.put("pYearList", pYearList);
	    
	    return result;
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
