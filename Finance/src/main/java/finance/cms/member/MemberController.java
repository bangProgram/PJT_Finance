package finance.cms.member;

import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import finance.cms.interest.service.InterestService;
import finance.cms.report.service.ReportService;
import finance.common.Controller.CommonController;
import finance.common.Controller.DefaultController;
import finance.common.mapper.CommonMapper;


@Controller
public class MemberController extends DefaultController{
	
	@Autowired
	private ReportService reportService;
		
	@RequestMapping(value={"/web/member/cud"} , method = RequestMethod.GET)
	public ModelAndView memberCUD(@RequestParam Map<String, Object> commandMap) throws Exception{ commandMap = init(commandMap);
		
		ModelAndView mav = new ModelAndView();
	    String resultURL = "main/main.jsp";
	    mav.setViewName(resultURL);
	    
	    return mav;
	}
	
	@ResponseBody
	@RequestMapping(value={"/web/member/passwordChk"} , method = RequestMethod.POST)
	public Map<String, Object> passwordChk(@RequestParam Map<String, Object> commandMap) throws Exception{ commandMap = init(commandMap);
		
		String password = "";
		String chkPassword = "";
		
		password = commandMap.get("password").toString();
		chkPassword = commandMap.get("chkPassword").toString();
		
		boolean chkRst = false;
		
		if(password.equals(chkPassword)) {
			chkRst = true;
		}
		
	    Map<String, Object> result = new HashMap<String, Object>();
	    result.put("chkRst", chkRst);
	    
	    return result;
	}
	
	
}
