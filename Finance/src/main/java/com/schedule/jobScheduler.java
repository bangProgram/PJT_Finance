package com.schedule;

import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.schedule.service.SchedulerService;

import finance.cms.dart.service.DartService;
import finance.common.Controller.CommonController;

@Service
public class jobScheduler {

	@Autowired(required=false)
	private DartService dartService;
	
	@Autowired(required=false)
	private CommonController commonController;
	
	// 매주 토요일 새벽 3시에 자동으로 시작하는 스케쥴러
	public boolean autoUpdate() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date now = new Date();
		String curDate = format.format(now);
		int year = Integer.parseInt(curDate.substring(0, 4));
		int month = Integer.parseInt(curDate.substring(4, 6));
		int day = Integer.parseInt(curDate.substring(6, 8));
		
		LocalDate date = LocalDate.of(year, month, day);
        System.out.println(date);  // // 2021-12-25
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        int dayOfWeekNumber = dayOfWeek.getValue();
        System.out.println(dayOfWeekNumber);
		
		Calendar cal = new GregorianCalendar();
		Date setDate = format.parse(curDate);
		cal.setTime(setDate);
		
		//월요일 일 경우 3일전 (금요일) , 일요일 일 경우 2일전 (금요일) 날짜 가져오기
		if(dayOfWeekNumber == 1) {
			cal.add(Calendar.DATE, -3);
		}else if(dayOfWeekNumber == 7){
			cal.add(Calendar.DATE, -2);
		}else{
			cal.add(Calendar.DATE, -1);
		}
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
		
		System.out.println("JB1 : "+url1);
		System.out.println("JB1 : "+url2);
		
		int resultInt = 0;
		for(int i=0; i< getPortListForSchedule.size(); i++) {
			Map<String, Object> param = new HashMap<String, Object>();
			String stockCd = getPortListForSchedule.get(i).get("STOCK_CODE").toString();
			param.put("STOCK_CODE", stockCd);
			try{
				if(mergeList != null) {
					for(int j=0; j < mergeList.size(); j++) {
						
			            JSONObject infoObject = (JSONObject) mergeList.get(j);
			            
			            //KRX 에서 가져온 STOCK_CODE
			            String ISU_CD = infoObject.get("ISU_CD").toString();
			            //KRX 에서 가져온 1. 종가 , 2. 상장 주식수
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
			            System.out.println("JB : "+resultInt+" 건 수정 완료");
			        }
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			 
		}
		
		return true;

	}
}
