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
import java.util.List;
import java.util.Map;

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

	
	@RequestMapping(value={"/portfolio"} , method = RequestMethod.GET)
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
		
		String infoKospi = "http://data-dbg.krx.co.kr/svc/apis/sto/stk_bydd_trd.json?" ;
		String infoKodaq = "http://data-dbg.krx.co.kr/svc/apis/sto/ksq_bydd_trd.json?" ;
		String AUTH_KEY = "41FFA4AE82714836B6246480F25D11C1B2A090D0";
		
		URL url1 = new URL(infoKospi+"AUTH_KEY="+AUTH_KEY+"&basDd="+befDate );
		InputStreamReader isr1 = new InputStreamReader(url1.openConnection().getInputStream(), "UTF-8");
		JSONObject object1 = (JSONObject)JSONValue.parse(isr1);
		JSONArray infoList1 = (JSONArray) object1.get("OutBlock_1");
		
		URL url2 = new URL(infoKodaq+"AUTH_KEY="+AUTH_KEY+"&basDd="+befDate );
		InputStreamReader isr2 = new InputStreamReader(url2.openConnection().getInputStream(), "UTF-8");
		JSONObject object2 = (JSONObject)JSONValue.parse(isr2);
		JSONArray infoList2 = (JSONArray) object2.get("OutBlock_1");
		
		JSONArray mergeList = commonController.mergeJsonArray(infoList1,infoList2);
		
		for(int i=0; i< getPortCorpList.size(); i++) {
			try{
				String corpCd = getPortCorpList.get(i).get("CORP_CODE").toString();
				String stockCd = getPortCorpList.get(i).get("STOCK_CODE").toString();
				
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
				
				if(mergeList != null) {
					for(int j=0; j < mergeList.size(); j++) {
			            JSONObject infoObject = (JSONObject) mergeList.get(j);
			            
			            //KRX 에서 가져온 STOCK_CODE
			            String ISU_CD = infoObject.get("ISU_CD").toString();
			            //KRX 에서 가져온 1. 종가 , 2. 상장 주식수
		            	String TDD_CLSPRC = infoObject.get("TDD_CLSPRC").toString();
			            String LIST_SHRS = infoObject.get("LIST_SHRS").toString();
			            
			            if(ISU_CD.equals(stockCd)) {
			            	getPortCorpList.get(i).put("TDD_CLSPRC", TDD_CLSPRC);
				            getPortCorpList.get(i).put("LIST_SHRS", LIST_SHRS);
			            }else {
			            	continue;
			            }
			            
			            /***
			            **	OutBlock_1	Block	repeat: multi
			        	**	BAS_DD			string()	기준일자
			        	**	ISU_CD			string()	종목코드
			        	**	ISU_NM			string()	종목명
			        	**	MKT_NM			string()	시장구분
			        	**	SECT_TP_NM		string()	소속부
			        	**	TDD_CLSPRC		string()	종가
			        	**	CMPPREVDD_PRC	string()	대비
			        	**	FLUC_RT			string()	등락률
			        	**	TDD_OPNPRC		string()	시가
			        	**	TDD_HGPRC		string()	고가
			        	**	TDD_LWPRC		string()	저가
			        	**	ACC_TRDVOL		string()	거래량
			        	**	ACC_TRDVAL		string()	거래대금
			        	**	MKTCAP			string()	시가총액
			        	**	LIST_SHRS		string()	상장주식수
			            ***/
			            System.out.println("JB : "+ISU_CD + " / "+stockCd + " = " + ISU_CD.equals(stockCd) + " // TDD_CLSPRC : "+TDD_CLSPRC);
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
	public Map<String, Object> regPortfolioAsset(@RequestParam Map<String, Object> commandMap) throws Exception{
		
		LocalDate now = LocalDate.now();
		
		System.out.println("JB : "+commandMap.toString());
		
		Integer resultInt = portfolioService.inertPortfolioAsset(commandMap);
		Map<String, Object> resultData = portfolioService.getPortfolio(commandMap);
		
	    Map<String, Object> result = new HashMap<String, Object>();
	    result.put("resultInt", resultInt);
	    result.put("resultData", resultData);
	    
	    return result;
	}
	
	@RequestMapping(value={"/portfolio/detail"} , method = RequestMethod.POST)
	public ModelAndView portfolioDetail(@RequestParam Map<String, Object> commandMap) throws Exception{
		
		LocalDate now = LocalDate.now();
		int curYear = now.getYear();		//2022
		int curMonth = now.getMonthValue();	//10
		
		System.out.println("JB : "+commandMap.toString());
		
		List<Map<String, Object>> resultList = portfolioService.getPortCorpDetailList(commandMap);
		
	    Map<String, Object> result = new HashMap<String, Object>();
	    
	    ModelAndView mav = new ModelAndView();
	    String resultURL = "portfolio/portfolioDetail.jsp";
	    mav.setViewName(resultURL);
	    mav.addObject("resultList", resultList);
	    
	    return mav;
	}
}
