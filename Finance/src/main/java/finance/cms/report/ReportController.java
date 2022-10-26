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
		int curMonth = now.getMonthValue();
		
		String yearString = "";
		List<String> yearList = new ArrayList<String>();
		List<String> quaterList = new ArrayList<String>();
		String tmp = "";
		String[] qTmp = {" 4Q"," 2Q"};
		
		if(curMonth >= 9) {
			for(int i=0; i<3; i++) {
				for(int j=0;j<2;j++) {
					if(i == 0 && j == 0) continue;
					quaterList.add(Integer.toString(curYear-i)+qTmp[j]);
				}
			}
		}else {
			for(int i=1; i<=3; i++) {
				for(int j=0;j<2;j++) {
					if(i == 3 && j == 2) continue;
					quaterList.add(Integer.toString(curYear-i)+qTmp[j]);
				}
			}
		}
		
		
		tmp = "";
		for(int i=1; i<=5; i++ ) {
			tmp = Integer.toString(curYear-i);
			
			if(i==1) {
				yearList.add(tmp);
				yearString += tmp;
			}else {
				yearList.add(tmp);
				yearString += "," + tmp;
			}
		}
		
		System.out.println("JB : "+quaterList.toString());
		System.out.println("JB : "+yearString);
		
		ModelAndView mav = new ModelAndView();
	    String resultURL = "report/reportList";
	    mav.addObject("yearString", yearString);
	    mav.addObject("yearList", yearList);
	    mav.addObject("quaterList", quaterList);
	    mav.setViewName(resultURL);
	    
	    return mav;
	}
	
	@ResponseBody
	@RequestMapping(value={"/report/select"} , method = RequestMethod.POST)
	public Map<String, Object> getReportList(@RequestParam Map<String, Object> commandMap) throws Exception{
		
		LocalDate now = LocalDate.now();
		int curYear = now.getYear();		//2022
		int curMonth = now.getMonthValue();	//10
		
		System.out.println("JB1 : "+commandMap.toString());
		System.out.println("JB2 : "+commandMap.get("pAccountIds").toString());
		System.out.println("JB3 : "+commandMap.get("pYearList").toString());
		
		String[] pAccountIds = commandMap.get("pAccountIds").toString().split(",");
		String[] chkYearList = commandMap.get("chkYearList").toString().split(",");
		String[] pYearList = commandMap.get("pYearList").toString().split(",");
		
		commandMap.put("pAccountCnt", pAccountIds.length);
		commandMap.put("pAccountIds", pAccountIds);
		commandMap.put("chkYearList", chkYearList);
		commandMap.put("pYearList", pYearList);
		
		List<Map<String, Object>> searchList = reportService.getReportSearch(commandMap);
		List<String> chkCorpList = new ArrayList<String>();
		for(int i=0; i<searchList.size(); i++) {
			chkCorpList.add(searchList.get(i).get("CORP_CODE").toString());
		}
		
		commandMap.put("chkCorpList", chkCorpList); List<Map<String, Object>>
		resultList = reportService.getReportList(commandMap);
		
	    Map<String, Object> result = new HashMap<String, Object>();
	    result.put("resultList", resultList);
	    
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
