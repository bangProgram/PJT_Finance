package finance.cms.portfolio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
		
		String infoURL = "https://opendart.fss.or.kr/api/list.json?";
		String crtfcKey = "fb1e1e5223c66ce1175f545ddd0ea9a15984528a";
		
		System.out.println("JB tlwkr");
		for(int i=0; i< getPortCorpList.size(); i++) {
			try{
				String corpCd = getPortCorpList.get(i).get("CORP_CODE").toString();
				
				URL url = new URL(infoURL+"crtfc_key="+crtfcKey+"&corp_code="+corpCd+"&bgn_de=20220101&end_de="+curDate+"&pblntf_ty=A&page_no=1&page_count=1" );
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/json");
				StringBuffer sb = new StringBuffer();
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				while(br.ready()) {
					sb.append(br.readLine());
				}
				JSONObject objData = (JSONObject)new JSONParser().parse(sb.toString());
				JSONArray infoList = (JSONArray) objData.get("list");
				if(infoList != null) {
					for(int j=0; j < infoList.size(); j++) {
			            JSONObject infoObject = (JSONObject) infoList.get(j);
			            
			            String reprtNm = infoObject.get("report_nm").toString();
			            String reprtNo = infoObject.get("rcept_no").toString();
			            System.out.println("JB : "+reprtNo + " / " + reprtNm);
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
		
		System.out.println("JB : "+commandMap.toString());
		
		Integer resultInt = portfolioService.insertPortfolioCorp(commandMap);
		
	    Map<String, Object> result = new HashMap<String, Object>();
	    result.put("resultInt", resultInt);
	    
	    return result;
	}
}
