package finance.cms.portfolio;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import finance.cms.portfolio.service.PortfolioService;


@Controller
public class PortfolioController {
	
	@Autowired
	private PortfolioService portfolioService;
	
	@RequestMapping(value={"/portfolio"} , method = RequestMethod.GET)
	public ModelAndView goPortFolio(@RequestParam Map<String, Object> commandMap) throws Exception {
		List<Map<String, Object>> resultList = portfolioService.getPortFolioList(commandMap);
	    
	    ModelAndView mav = new ModelAndView();
	    String resultURL = "portfolio/portfolioList";
	    mav.addObject("resultList", resultList);
	    mav.setViewName(resultURL);
	    
	    return mav;
	}
	
	@ResponseBody
	@RequestMapping(value={"/portfolio/add/cud"} , method = RequestMethod.POST)
	public Map<String, Object> addPortfolioCorp(@RequestParam Map<String, Object> commandMap) throws Exception{
		
		LocalDate now = LocalDate.now();
		int curYear = now.getYear();		//2022
		int curMonth = now.getMonthValue();	//10
		
		System.out.println("JB : "+commandMap.toString());
		
		Integer resultInt = portfolioService.insertPortfolioCorp(commandMap);
		
	    Map<String, Object> result = new HashMap<String, Object>();
	    result.put("resultInt", resultInt);
	    
	    return result;
	}
}
