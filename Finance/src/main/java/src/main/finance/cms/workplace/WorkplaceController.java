package src.main.finance.cms.workplace;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WorkplaceController {
	
	@RequestMapping(value={"/bplc/view"} , method = RequestMethod.GET)
	public ModelAndView create() {
	    return new ModelAndView("workplace/bplcView");
	}
}

