package finance.cms.corporation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import finance.common.Controller.DefaultController;

@Controller
public class CorporationController extends DefaultController {
	
	@RequestMapping(value={"/bplc/view"} , method = RequestMethod.GET)
	public ModelAndView create() {
	    return new ModelAndView("workplace/bplcView");
	}
}

