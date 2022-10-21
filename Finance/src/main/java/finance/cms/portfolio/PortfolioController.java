package finance.cms.portfolio;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import finance.cms.portfolio.service.PortfolioService;


@Controller
public class PortfolioController {
	
	private PortfolioService portfolioService;
	
	@RequestMapping(value={"/port"} , method = RequestMethod.GET)
	public ModelAndView goPortFolio(@RequestParam Map<String, Object> commandMap) throws Exception {
		List<Map<String, Object>> resultList = portfolioService.getPortFolioList(commandMap);
	    
	    ModelAndView mav = new ModelAndView();
	    String resultURL = "portfolio/portfolioList";
	    mav.addObject("resultList", resultList);
	    mav.setViewName(resultURL);
	    
	    return mav;
	}
	
	
}
