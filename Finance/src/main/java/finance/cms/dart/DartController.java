package finance.cms.dart;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import finance.cms.dart.service.DartService;
import finance.cms.interest.service.InterestService;
import finance.cms.portfolio.service.PortfolioService;
import finance.common.Controller.CommonController;

@Controller
public class DartController {

	@Autowired
	private DartService dartService;
	
	@Autowired
	private PortfolioService portfolioService;
	
	@Resource(name="CommonController")
	private CommonController commonController;
	
	public boolean updateKRX() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date now = new Date();
		String curDate = format.format(now);
		
		Calendar cal = new GregorianCalendar();
		Date setDate = format.parse(curDate);
		cal.setTime(setDate);
		cal.add(Calendar.DATE, -1);
		String befDate = format.format(cal.getTime());
		Map<String, Object> paramtest = new HashMap<String, Object> ();
		System.out.println("test1111");
		paramtest.put("test", "test");
		List<Map<String,Object>> getPortListForSchedule = dartService.getPortListForSchedule(paramtest);
		
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
		
		int resultInt = 0;
		for(int i=0; i< getPortListForSchedule.size(); i++) {
			Map<String, Object> param = new HashMap<String, Object>();
			String stockCd = getPortListForSchedule.get(i).get("STOCK_CODE").toString();
			param.put("STOCK_CODE", stockCd);
			try{
				if(mergeList != null) {
					for(int j=0; j < mergeList.size(); j++) {
						
			            JSONObject infoObject = (JSONObject) mergeList.get(j);
			            
			            //KRX ?????? ????????? STOCK_CODE
			            String ISU_CD = infoObject.get("ISU_CD").toString();
			            //KRX ?????? ????????? 1. ?????? , 2. ?????? ?????????
		            	String TDD_CLSPRC = infoObject.get("TDD_CLSPRC").toString();
			            String LIST_SHRS = infoObject.get("LIST_SHRS").toString();
			            
			            if(ISU_CD.equals(stockCd)) {
			            	param.put("TDD_CLSPRC", TDD_CLSPRC);
			            	param.put("LIST_SHRS", LIST_SHRS);
			            	resultInt += dartService.updatePortForSchedule(param);
			            }else {
			            	continue;
			            }
			            
			            /***
			            **	OutBlock_1	Block	repeat: multi
			        	**	BAS_DD			string()	????????????
			        	**	ISU_CD			string()	????????????
			        	**	ISU_NM			string()	?????????
			        	**	MKT_NM			string()	????????????
			        	**	SECT_TP_NM		string()	?????????
			        	**	TDD_CLSPRC		string()	??????
			        	**	CMPPREVDD_PRC	string()	??????
			        	**	FLUC_RT			string()	?????????
			        	**	TDD_OPNPRC		string()	??????
			        	**	TDD_HGPRC		string()	??????
			        	**	TDD_LWPRC		string()	??????
			        	**	ACC_TRDVOL		string()	?????????
			        	**	ACC_TRDVAL		string()	????????????
			        	**	MKTCAP			string()	????????????
			        	**	LIST_SHRS		string()	???????????????
			            ***/
			            System.out.println("JB : "+resultInt+" ??? ?????? ??????");
			        }
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			 
		}
		
		return true;

	}
}
