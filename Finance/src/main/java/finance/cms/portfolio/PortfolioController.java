package finance.cms.portfolio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import finance.cms.corporation.service.CorporationService;
import finance.cms.portfolio.service.PortfolioService;
import finance.common.Controller.CommonController;


@Controller
public class PortfolioController {
	
	@Autowired
	private PortfolioService portfolioService;
	
	@Autowired
	private CorporationService corporationService;
	
	@Resource(name="CommonController")
	private CommonController commonController;

	
	@RequestMapping(value={"/portfolio"})
	public ModelAndView goPortFolio(@RequestParam Map<String, Object> commandMap) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date now = new Date();
		String curDate = format.format(now);
		
		Calendar cal = new GregorianCalendar();
		Date setDate = format.parse(curDate);
		cal.setTime(setDate);
		cal.add(Calendar.DATE, -1);
		String befDate = format.format(cal.getTime());
		
		commandMap.put("curUserId", "SYSTEM_JB");
		
		Map<String, Object> getPortfolio = portfolioService.getPortfolio(commandMap);
		List<Map<String, Object>> getPortCorpList = portfolioService.getPortCorpList(commandMap);
		
		String infoURL = "https://opendart.fss.or.kr/api/list.json?";
		String crtfcKey = "fb1e1e5223c66ce1175f545ddd0ea9a15984528a";
		
		for(int i=0; i< getPortCorpList.size(); i++) {
			try{
				String corpCd = getPortCorpList.get(i).get("CORP_CODE").toString();
				
				URL url = new URL(infoURL+"crtfc_key="+crtfcKey+"&corp_code="+corpCd+"&bgn_de=20220101&end_de="+curDate+"&pblntf_ty=A&page_no=1&page_count=1" );
				InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
				JSONObject object = (JSONObject)JSONValue.parse(isr);
				JSONArray infoList = (JSONArray) object.get("list");
				
				if(infoList != null) {
					for(int j=0; j < infoList.size(); j++) {
			            JSONObject infoObject = (JSONObject) infoList.get(j);
			            
			            String reprtNm = infoObject.get("report_nm").toString();
			            String reprtNo = infoObject.get("rcept_no").toString();
			            getPortCorpList.get(i).put("REPRT_NM", reprtNm);
			            getPortCorpList.get(i).put("REPRT_NO", reprtNo);
			        }
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			 
		}
		
		JSONArray getPortCorpJson = commonController.convertListToJson(getPortCorpList);
		
		System.out.println("JB 끝 : "+ getPortCorpJson.toString());
		
		
		
	    ModelAndView mav = new ModelAndView();
	    String resultURL = "portfolio/portfolioList.jsp";
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
		
		commandMap.put("curUserId", "SYSTEM_JB");
		
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("CORP_CODE", commandMap.get("CORP_CODE").toString());
		paramMap.put("pEndYear", curYear);
		paramMap.put("pStartYear", (curYear-6));
		
		List<Map<String, Object>> getCorporation = corporationService.getCorporationGrowth(paramMap);
		
		for(int i=0; i<getCorporation.size(); i++) {
			String accId = getCorporation.get(i).get("ACCOUNT_ID").toString();
			String avrGrwoth = getCorporation.get(i).get("AVR_GROWTH").toString();
			
			if(accId.equals("ifrs-full_Revenue")) {
				commandMap.put("AVG_REVENUE_GROWTH", avrGrwoth);
			}else if(accId.equals("dart_OperatingIncomeLoss")) {
				commandMap.put("AVG_OPERAT_GROWTH", avrGrwoth);
			}else {
				commandMap.put("AVG_PROFIT_GROWTH", avrGrwoth);
			}
		}
		Integer resultInt = portfolioService.insertPortfolioCorp(commandMap);
		
	    Map<String, Object> result = new HashMap<String, Object>();
	    result.put("resultInt", resultInt);
	    
	    return result;
	}
	
	@RequestMapping(value={"/portfolio/del/cud"} , method = RequestMethod.POST)
	public ModelAndView delPortfolioCorp(@RequestParam Map<String, Object> commandMap) throws Exception{
		
		LocalDate now = LocalDate.now();
		int curYear = now.getYear();		//2022
		int curMonth = now.getMonthValue();	//10
		
		System.out.println("JB : "+commandMap.toString());
		String[] delCorpList = {};
		if(!commandMap.get("corpCds").equals("") && !commandMap.get("corpCds").equals(null) ) {
			delCorpList = commandMap.get("corpCds").toString().split(",");
		}
		
		int delCorpCnt = delCorpList.length;
		System.out.println("JB2 : "+delCorpCnt);
		commandMap.put("delCorpCnt", delCorpCnt);
		commandMap.put("delCorpList", delCorpList);
		
		Integer resultInt = portfolioService.deletePortfolioCorp(commandMap);
		
	    Map<String, Object> result = new HashMap<String, Object>();
	    result.put("resultInt", resultInt);
	    
	    ModelAndView mav = new ModelAndView();
	    String resultURL = "redirect:/portfolio";
	    mav.setViewName(resultURL);
	    
	    return mav;
	}
	
	@ResponseBody
	@RequestMapping(value={"/portfolio/regasset/cud"} , method = RequestMethod.POST)
	public Map<String, Object> regPortfolioAsset(@RequestParam Map<String, Object> commandMap) throws Exception{commandMap = commonController.init(commandMap);
		
		LocalDate now = LocalDate.now();
		
		System.out.println("JB : "+commandMap.toString());
		
		Integer resultInt = portfolioService.insertPortfolioAsset(commandMap);
		Map<String, Object> resultData = portfolioService.getPortfolio(commandMap);
		
	    Map<String, Object> result = new HashMap<String, Object>();
	    result.put("resultInt", resultInt);
	    result.put("resultData", resultData);
	    
	    return result;
	}
	
	@RequestMapping(value={"/portfolio/detail"})
	public ModelAndView portfolioDetail(@RequestParam Map<String, Object> commandMap) throws Exception{commandMap = commonController.init(commandMap);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String curDate = format.format(now);
		
		System.out.println("JB : "+commandMap.toString());
		commandMap.put("curUserId", "SYSTEM_JB");
		Map<String, Object> resultData = portfolioService.getPortCorp(commandMap);
		List<Map<String, Object>> resultList = portfolioService.getPortCorpDetailList(commandMap);
		
	    Map<String, Object> result = new HashMap<String, Object>();
	    
	    ModelAndView mav = new ModelAndView();
	    String resultURL = "portfolio/portfolioDetail.jsp";
	    mav.setViewName(resultURL);
	    mav.addObject("curDate", curDate);
	    mav.addObject("resultData", resultData);
	    mav.addObject("resultList", resultList);
	    
	    return mav;
	}
	
	@RequestMapping(value={"/portfolio/detail/cud"} , method = RequestMethod.POST)
	public ModelAndView portfolioDetailCUD(@RequestParam Map<String, Object> commandMap) throws Exception{commandMap = commonController.init(commandMap);
		
		LocalDate now = LocalDate.now();
		
		String mode = commandMap.get("mode").toString();
		commandMap.put("curUserId", "SYSTEM_JB");
		System.out.println("JB : "+commandMap.toString());
		Integer resultInt = 0; 
		
		if(mode.equals("add")) {
			resultInt = portfolioService.addPortfolioDetail(commandMap);
		}else {
			resultInt = portfolioService.delPortfolioDetail(commandMap);
		}
		
	    Map<String, Object> result = new HashMap<String, Object>();
	    
	    ModelAndView mav = new ModelAndView();
	    String resultURL = "redirect:/portfolio/detail?pCorpCd="+commandMap.get("pCorpCd").toString();
	    mav.setViewName(resultURL);
	    
	    return mav;
	}
	
	@ResponseBody
	@RequestMapping(value={"/portfolio/regOpinonAmt/ajax"} , method = RequestMethod.POST)
	public Map<String, Object> regOpinonAmt(@RequestParam Map<String, Object> commandMap) throws Exception{commandMap = commonController.init(commandMap);
		
		LocalDate now = LocalDate.now();
		
		System.out.println("JB : "+commandMap.toString());
		commandMap.put("curUserId", "SYSTEM_JB");
		
		Integer resultInt = portfolioService.insertPortfolioCorp(commandMap);
		
	    Map<String, Object> result = new HashMap<String, Object>();
	    result.put("resultInt", resultInt);
	    
	    return result;
	}
}
