package src.main.finance.main.report;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportController {

	@RequestMapping(value={"/report"} , method = RequestMethod.GET)
	public ModelAndView goReport() {
	    return new ModelAndView("report/reportView");
	}
}
