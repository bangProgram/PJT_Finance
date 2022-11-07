package finance.cms.portfolio;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import finance.cms.portfolio.service.PortfolioService;
import finance.common.Controller.CommonController;


@Controller
public class PortfolioController {
	
	@Autowired
	private PortfolioService portfolioService;
	
	@Resource(name="CommonController")
	private CommonController commonController;

	
	@RequestMapping(value={"/portfolio"} , method = RequestMethod.GET)
	public ModelAndView goPortFolio(@RequestParam Map<String, Object> commandMap) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date now = new Date();
		String curDate = format.format(now);
		
		Map<String, Object> getPortfolio = portfolioService.getPortfolio(commandMap);
		List<Map<String, Object>> getPortCorpList = portfolioService.getPortCorpList(commandMap);
		
		JSONArray getPortCorpJson = commonController.convertListToJson(getPortCorpList);
		
		JSONParser parser = new JSONParser();
		for(int i=0; i< getPortCorpList.size(); i++) {
			String corpCd = getPortCorpList.get(i).get("CORP_CODE").toString();
			System.out.println("JB 확인 : " + "https://opendart.fss.or.kr/api/list.json?crtfc_key=fb1e1e5223c66ce1175f545ddd0ea9a15984528a&corp_code="+corpCd+"&bgn_de=20220101&end_de="+curDate+"&pblntf_ty=A&corp_cls=Y&page_no=1&page_count=1");
			
			JSONObject object = (JSONObject) parser.parse("https://opendart.fss.or.kr/api/list.json?crtfc_key=fb1e1e5223c66ce1175f545ddd0ea9a15984528a&corp_code="+corpCd+"&bgn_de=20220101&end_de="+curDate+"&pblntf_ty=A&corp_cls=Y&page_no=1&page_count=1");
			JSONArray infoList = (JSONArray) object.get("list");
			for(int j=0; j < infoList.size(); j++) {
	            System.out.println("======== object : " + j + " ========");
	            JSONObject infoObject = (JSONObject) infoList.get(i);
	            
	            System.out.println(infoObject.get("name"));
	            System.out.println(infoObject.get("age"));
	        }
		}
		
		
	    
	    ModelAndView mav = new ModelAndView();
	    String resultURL = "portfolio/portfolioList";
	    mav.addObject("getPortfolio", getPortfolio);
	    mav.addObject("getPortCorpJson", getPortCorpJson);
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
