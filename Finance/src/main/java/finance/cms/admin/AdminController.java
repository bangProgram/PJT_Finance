package finance.cms.admin;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import finance.cms.admin.service.AdminService;
import finance.cms.interest.service.InterestService;
import finance.common.Controller.CommonController;
import finance.common.Controller.DefaultController;
import freemarker.template.utility.StringUtil;

@Controller
public class AdminController extends DefaultController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private InterestService interestService;
	
	
	@RequestMapping(value={"/cms/admin/exUp"} , method = RequestMethod.GET)
	public ModelAndView goReport(@RequestParam Map<String, Object> commandMap, HttpServletResponse response, HttpServletRequest request) throws Exception{ 
		commandMap = init(request, commandMap);
		Map<String, ?> flashMap =RequestContextUtils.getInputFlashMap(request);
		
		String exUpEnd = "false";
		
		if(null != flashMap) {
			exUpEnd = flashMap.get("exUpEnd").toString();
		}
		System.out.println("JB upchk : "+exUpEnd);
		if(exUpEnd.equals("true")) {
			response.setContentType("text/html; charset=UTF-8");
			 
			PrintWriter out = response.getWriter();
			 
			out.println("<script>alert('엑셀업로드가 완료되었습니다.');</script>");
			 
			out.flush();
		}
		
	
		ModelAndView mav = new ModelAndView();
	    String resultURL = "cms/admin/exUpView.jsp";
	    mav.setViewName(resultURL);
	    
	    return mav;
	}
	
	@RequestMapping(value={"/cms/admin/exUp/cud"} , method = RequestMethod.POST)
	  public ModelAndView handleFileUpload(@RequestParam Map<String, Object> commandMap, @RequestParam("file") MultipartFile file, RedirectAttributes reAttr, HttpServletRequest request, HttpServletResponse response) throws Exception{ 
		
		try {
			DataFormatter dataFormatter = new DataFormatter();
			commandMap = init(request, commandMap);
			InputStream inputStream = file.getInputStream();
			//Workbook workbook = WorkbookFactory.create(inputStream);
			// 엑셀 파일 읽기 로직을 구현합니다.
			int insertCnt = 0;
			
			//손익보고서 insert parameter
			Map<String, Object> paramMap = new HashMap<String, Object>();
			
			//필수값 5코드
			String BSNS_YEAR = commandMap.get("BSNS_YEAR").toString();
			String REPRT_CODE = commandMap.get("REPRT_CODE").toString();
			String REPRT_NM = commandMap.get("REPRT_NM").toString();
			String SJ_DIV = commandMap.get("SJ_DIV").toString();
			String SJ_NM = commandMap.get("SJ_NM").toString();
			paramMap.put("BSNS_YEAR", BSNS_YEAR);
			paramMap.put("REPRT_CODE", REPRT_CODE);
			paramMap.put("REPRT_NM", REPRT_NM);
			paramMap.put("SJ_DIV", SJ_DIV);
			paramMap.put("SJ_NM", SJ_NM);
			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(0); // 해당 엑셀파일의 시트(Sheet) 수
			int rows = sheet.getPhysicalNumberOfRows(); // 해당 시트의 행의 개수
			for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
				XSSFRow row = sheet.getRow(rowIndex); // 각 행을 읽어온다
				if (row != null) {
					String accountNm = row.getCell(10).getStringCellValue().replace("ifrs_", "ifrs-full_");
					if(	//20230705 EPS 넣으려고 임시로 주석처리
							//accountNm.equals("ifrs-full_Revenue") || accountNm.equals("dart_OperatingIncomeLoss") || accountNm.equals("ifrs-full_ProfitLoss") || 
							accountNm.equals("ifrs-full_BasicEarningsLossPerShare")
						) {
						int cells = row.getPhysicalNumberOfCells();
						for (int columnIndex = 0; columnIndex <= cells; columnIndex++) {
							XSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
							String value = "";
							if(cell != null) {
								switch (cell.getCellType()) {
								case NUMERIC:
									value = cell.getNumericCellValue() + "";
									break;
								case STRING:
									value = cell.getStringCellValue() + "";
									break;
								case BLANK:
									value = ""; // 빈 값을 빈 문자열로 처리하거나 필요에 따라 다른 처리를 수행하세요.
									break;
								case ERROR:
									value = cell.getErrorCellValue() + "";
									break;
								}
								/*
								 * COL_0 재무제표종류	
								 * COL_1 종목코드 = [회사코드]	
								 * COL_2 회사명	
								 * COL_3 시장구분	
								 * COL_4 업종	
								 * COL_5 업종명	
								 * COL_6 결산월	
								 * COL_7 결산기준일	
								 * COL_8 보고서종류	
								 * COL_9 통화	
								 * COL_10 항목코드	
								 * COL_11 항목명	
								 * COL_12 당기 1분기 3개월	
								 * COL_13 당기 1분기 누적	
								 * COL_14 전기 1분기 3개월	
								 * COL_15 전기 1분기 누적                          
							 */
								if(columnIndex == 1) {
									value = value.replace("[", "").replace("]", "");
								}else if(columnIndex == 7) {
									String[] strList = cell.toString().split("-");
									String month = strList[1].replace("월", "");
									if( Integer.parseInt(strList[1].replace("월", "")) < 10) {
										month = "0"+month;
									}
									String clsDate = strList[2]+"-"+month+"-"+strList[0];
									System.out.println("test2 : "+ clsDate);
									value = clsDate + "";
								}else if(columnIndex == 10) {
									value = value.replace("ifrs_", "ifrs-full_");
								}
								paramMap.put("COL_"+columnIndex, value);
		                      	}
		                  	}
		                  	System.out.println(paramMap.toString());
		                  	Integer resultInt = adminService.mergeReport(paramMap);
		                  	insertCnt++;
		        	  	}
		          	}
		      	}
				System.out.println("총 "+insertCnt+" 건 입력");
				return getMessageModel("msgAndRedirect", "엑셀업로드가 완료되었습니다.", "/cms/admin/exUp");
		  	} catch (IOException e) {
		  		e.printStackTrace();
		  		return getMessageModel("msgAndRedirect", e.toString(), "/cms/admin/exUp");
		  	}
//	    	ModelAndView mav = new ModelAndView();
//	    	reAttr.addFlashAttribute("exUpEnd","true");
//	    	String resultURL = "redirect:/cms/admin/exUp"; // 성공 페이지로 리다이렉트합니다.
//	    	mav.setViewName(resultURL);
//	    	return mav;
	  	}
}
