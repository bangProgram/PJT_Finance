package finance.cms.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@RequestMapping(value={"/main","/"} , method = RequestMethod.GET)
	public ModelAndView main() {
	    return new ModelAndView("main/main.jsp");
	}
	
	@RequestMapping(value={"/bplc/report"} , method = RequestMethod.GET)
	public ModelAndView goReport() {
	    return new ModelAndView("report/reportView.jsp");
	}
}
