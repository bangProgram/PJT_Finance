package finance.cms.main;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import finance.common.Controller.DefaultController;

@Controller
public class MainController extends DefaultController{

	@RequestMapping(value={"/main","/"} , method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest req, HttpServletResponse res, @RequestParam Map<String, Object> commandMap) {
		
		ModelAndView mav = new ModelAndView();
		
		String resultUrl = "main/main.jsp";
		mav.setViewName(resultUrl);
		
		return mav;
		
	}
	
	@RequestMapping(value={"/bplc/report"} , method = RequestMethod.GET)
	public ModelAndView goReport() {
	    return new ModelAndView("report/reportView");
	}
}
