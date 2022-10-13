package src.main.finance.cms.report;

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
	public ModelAndView goReport() {
	    return new ModelAndView("report/reportView");
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
