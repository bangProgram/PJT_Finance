package finance.cms.admin;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
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
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;


import finance.cms.admin.service.AdminService;
import finance.cms.interest.service.InterestService;
import finance.common.Controller.CommonController;
import finance.common.Controller.DefaultController;
import freemarker.template.utility.StringUtil;


import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


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
							accountNm.equals("ifrs-full_Revenue") || accountNm.equals("dart_OperatingIncomeLoss") || accountNm.equals("ifrs-full_ProfitLoss") || 
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
	
	
	@RequestMapping(value={"/cms/admin/xml/cud"} , method = RequestMethod.POST)
	  public ModelAndView handleXMLUpload(@RequestParam Map<String, Object> commandMap, @RequestParam("file") MultipartFile file, RedirectAttributes reAttr, HttpServletRequest request, HttpServletResponse response) throws Exception{ 
		
		try {
            // XML 파일 로드
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file.getInputStream());

            // 루트 요소 얻기
            Element root = document.getDocumentElement();
            

            // "list" 요소 가져오기
            NodeList list = root.getElementsByTagName("list");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                	Map<String,Object> param = new HashMap<String, Object>();
                    Element element = (Element) node;
                    String corpCode = element.getElementsByTagName("corp_code").item(0).getTextContent();
                    String corpName = element.getElementsByTagName("corp_name").item(0).getTextContent();
                    String stockCode = element.getElementsByTagName("stock_code").item(0).getTextContent();
                    String modifyDate = element.getElementsByTagName("modify_date").item(0).getTextContent();

                    // 파싱한 데이터 활용 또는 출력
                    System.out.print("Corp Code: " + corpCode);
                    System.out.print(" Corp Name: " + corpName);
                    System.out.print(" Stock Code: " + stockCode);
                    System.out.print(" Modify Date: " + modifyDate + "\n");
                    param.put("CORP_CODE", corpCode);
                    param.put("CORP_NAME", corpName);
                    param.put("STOCK_CODE", stockCode);
                    param.put("MODIFY_DATE", modifyDate);
                    
                    adminService.mergeMainBplc(param);
                }
            }
			return getMessageModel("msgAndRedirect", "XML업로드가 완료되었습니다.", "/cms/admin/exUp");
        } catch (Exception e) {
            e.printStackTrace();
            return getMessageModel("msgAndRedirect", e.toString(), "/cms/admin/exUp");
        }
  	}
	
	
	
	@RequestMapping(value={"/cms/admin/mergeCorp"} , method = RequestMethod.GET)
	public ModelAndView mergeCorpDetail(@RequestParam Map<String, Object> commandMap, HttpServletResponse response, HttpServletRequest request) throws Exception{ 
		commandMap = init(request, commandMap);
		
		String gubn = commandMap.get("GUBN").toString();
		String stLimit = commandMap.get("stLimit").toString();
		String edLimit = commandMap.get("edLimit").toString();

		List<Map<String,Object>> getCorpList = adminService.getCorpListForMerge(commandMap);
		
		String apiUrl = "https://opendart.fss.or.kr/api/company.json";
	    // 파라미터 설정
		System.out.println("사업장 : "+getCorpList.size()+" ( "+stLimit +" ~ "+edLimit+" ) 작업시작");
		if(getCorpList != null) {
			for(int i=0; i<getCorpList.size(); i++) {
				String corpCode = getCorpList.get(i).get("CORP_CODE").toString();
				String parameters = "?crtfc_key="+Opk+"&corp_code="+corpCode;

			    // URL과 파라미터 조합
			    String uri = apiUrl + parameters;
			    System.out.println("uri : "+uri);
			    URL url = new URL(uri);
				InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
				JSONObject object = (JSONObject)JSONValue.parse(isr);
				Map<String, Object> paramMap = new HashMap<String, Object>();
				
				/*
				 * corp_code 기업코드
				 * corp_name 정식회사명칭  
				 * corp_name_eng 영문명칭 영문정식회사명칭 
				 * stock_name 종목명(상장사) 또는 약식명칭(기타법인) 
				 * stock_code 상장회사인 경우 주식의 종목코드 상장회사의 종목코드(6자리) 
				 * ceo_nm 대표자명 대표자명 
				 * corp_cls 법인구분 법인구분 : Y(유가), K(코스닥), N(코넥스), E(기타) 
				 * jurir_no 법인등록번호  
				 * bizr_no 사업자등록번호 
				 * adres 주소  
				 * hm_url 홈페이지  
				 * ir_url IR홈페이지
				 * phn_no 전화번호  
				 * fax_no 팩스번호  
				 * induty_code 업종코드  
				 * est_dt 설립일(YYYYMMDD) 
				 * acc_mt 결산월(MM) 
				 */
				
				String CORP_CODE = object.get("corp_code").toString();
				String CORP_NAME = object.get("corp_name").toString();
				String STOCK_NAME = object.get("stock_name").toString();
				String STOCK_CODE = object.get("stock_code").toString();
				String CEO_NM = object.get("ceo_nm").toString();
				String CORP_CLS = object.get("corp_cls").toString();
				String JURIR_NO = object.get("jurir_no").toString();
				String BIZR_NO = object.get("bizr_no").toString();
				String ADRES = object.get("adres").toString();
				String HM_URL = object.get("hm_url").toString();
				String IR_URL = object.get("ir_url").toString();
				String PHN_NO = object.get("phn_no").toString();
				String FAX_NO = object.get("fax_no").toString();
				String INDUTY_CODE = object.get("induty_code").toString();
				String EST_DT = object.get("est_dt").toString();
				String ACC_MT = object.get("acc_mt").toString();
				paramMap.put("CORP_CODE", CORP_CODE);
				paramMap.put("CORP_NAME", CORP_NAME);
				paramMap.put("STOCK_NAME", STOCK_NAME);
				paramMap.put("STOCK_CODE", STOCK_CODE);
				paramMap.put("CEO_NM", CEO_NM);
				paramMap.put("CORP_CLS", CORP_CLS);
				paramMap.put("JURIR_NO", JURIR_NO);
				paramMap.put("BIZR_NO", BIZR_NO);
				paramMap.put("ADRES", ADRES);
				paramMap.put("HM_URL", HM_URL);
				paramMap.put("IR_URL", IR_URL);
				paramMap.put("PHN_NO", PHN_NO);
				paramMap.put("FAX_NO", FAX_NO);
				paramMap.put("INDUTY_CODE", INDUTY_CODE);
				paramMap.put("EST_DT", EST_DT);
				paramMap.put("ACC_MT", ACC_MT);
				
				adminService.mergeCorpDetail(paramMap);

			}
		}
	    
		return getMessageModel("msgAndRedirect", "엑셀업로드가 완료되었습니다.", "/cms/admin/exUp");
	}
	
	
}
