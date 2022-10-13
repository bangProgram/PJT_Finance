package src.main.finance.main.report;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import src.main.finance.main.report.service.ReportService;

@Controller
public class ReportController {
	
	private ReportService reportService;

	@RequestMapping(value={"/report"} , method = RequestMethod.GET)
	public ModelAndView goReport() {
	    return new ModelAndView("report/reportView");
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createPost(@RequestParam Map<String, Object> map) {
	    ModelAndView mav = new ModelAndView();

	    String bookId = reportService.create(map);
	    if (bookId == null) {
	        mav.setViewName("redirect:/create");
	    }else {
	        mav.setViewName("redirect:/detail?bookId=" + bookId); 
	    }  

	    return mav;
	}
}
