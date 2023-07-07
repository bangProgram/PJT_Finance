package finance.cms.report;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import finance.cms.report.service.ReportService;
import finance.common.Controller.DefaultController;


@Controller
public class ReportController extends DefaultController{
	
	@Autowired
	private ReportService reportService;
	
	

	@RequestMapping(value={"/cms/report"} , method = RequestMethod.GET)
	public ModelAndView goReport(@RequestParam Map<String, Object> commandMap) throws Exception{ commandMap = init(commandMap);
		LocalDate now = LocalDate.now();
		int curYear = now.getYear();
		int curMonth = now.getMonthValue();
		
		String yearString = "";
		String quaterString = "";
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pReportCd", "0302");
		List<Map<String, Object>> quaterList = reportService.getBsnsYearList(paramMap);
		paramMap.put("pReportCd", "0301");
		List<Map<String, Object>> yearList = reportService.getBsnsYearList(paramMap);
		
		
		for(int i=0; i<6; i++ ) {
			if(i==0) {
				yearString += yearList.get(i).get("BSNS_YEAR");
				quaterString += quaterList.get(i).get("HAEDER_NM");
			}else {
				yearString += "," + yearList.get(i).get("BSNS_YEAR");
				quaterString += "," + quaterList.get(i).get("HAEDER_NM");
			}
		}
		
		System.out.println("JB : "+quaterList.toString());
		System.out.println("JB : "+yearString);
		
		ModelAndView mav = new ModelAndView();
	    String resultURL = "cms/report/reportList.jsp";
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
	@RequestMapping(value={"/cms/report/select"} , method = RequestMethod.POST)
	public Map<String, Object> getReportList(@RequestParam Map<String, Object> commandMap) throws Exception{ commandMap = init(commandMap);
		
		LocalDate now = LocalDate.now();
		int curYear = now.getYear();		//2022
		int curMonth = now.getMonthValue();	//10
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pReportCd", "0302");
		List<Map<String, Object>> quaterList = reportService.getBsnsYearList(paramMap);
		paramMap.put("pReportCd", "0301");
		List<Map<String, Object>> yearList = reportService.getBsnsYearList(paramMap);
		
		String[] pAccountIds = {}; 
		if(!commandMap.get("pAccountIds").equals("") && !commandMap.get("pAccountIds").equals(null) ) {
			pAccountIds = commandMap.get("pAccountIds").toString().split(",");
		}
		
		String chkYear = commandMap.get("chkYearList").toString();
		String[] chkYearList = {};
		if(!chkYear.equals("") && chkYear != null) {
			chkYearList = chkYear.split(",");
		}
		String[] pYearList = new String [6];
		String[] pQuaterList = new String [6];
		
		System.out.println("JB 확인 : "+chkYearList + " / "+ chkYearList.length);
		for(int i=0; i<6; i++ ) {
			pYearList[i] = yearList.get(i).get("BSNS_YEAR").toString();			
			pQuaterList[i] = quaterList.get(i).get("HAEDER_NM").toString();			
		}
		commandMap.put("pAccountCnt", pAccountIds.length);
		commandMap.put("pAccountIds", pAccountIds);
		commandMap.put("chkYearList", chkYearList);
		commandMap.put("pYearList", pYearList);
		commandMap.put("pQuaterList", pQuaterList);
		
//		if(commandMap.get("pCorpName") != null && commandMap.get("pCorpName") != "") {
//			
//		}else {
//			List<Map<String, Object>> searchList = reportService.getReprtCorpCode(commandMap);
//			List<String> chkCorpList = new ArrayList<String>();
//			for(int i=0; i<searchList.size(); i++) {
//				chkCorpList.add(searchList.get(i).get("CORP_CODE").toString());
//			}
//			
//			commandMap.put("chkCorpList", chkCorpList); 
//		}
//		
		List<Map<String, Object>> resultList = reportService.getReportList(commandMap);
		
	    Map<String, Object> result = new HashMap<String, Object>();
	    result.put("resultList", resultList);
	    
	    return result;
	}
	
	@RequestMapping(value = "/cms/report/detail/view", method = RequestMethod.POST)
	public ModelAndView getReportDetail(@RequestParam Map<String, Object> commandMap) throws Exception { commandMap = init(commandMap);

	    List<Map<String, Object>> resultList = reportService.getReportDetailList(commandMap);
	    
	    ModelAndView mav = new ModelAndView();
	    String resultURL = "cms/report/detail/repDetview";
	    mav.addObject("resultList", resultList);
	    mav.setViewName(resultURL);
	    
	    return mav;
	}
}
