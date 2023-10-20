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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import finance.cms.interest.service.InterestService;
import finance.cms.member.service.MemberService;
import finance.cms.report.service.ReportService;
import finance.common.Controller.CommonController;
import finance.common.Controller.DefaultController;
import finance.common.mapper.CommonMapper;
import finance.common.util.SecurityJavaConfig;


@Controller
public class MemberController extends DefaultController{
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
		
	
	@RequestMapping(value={"/web/member/cud"})
	public ModelAndView memberCUD(HttpServletRequest request, HttpServletResponse response , @RequestParam Map<String, Object> commandMap) throws Exception{ commandMap = init(request, commandMap);
	
		String passWd = commandMap.get("password").toString();
		Integer resultInt = 0;
		if(!passWd.equals("") && passWd != null) {

			String passWd_encode = bCryptPasswordEncoder.encode(passWd);
			commandMap.put("password", passWd_encode);
			resultInt = memberService.createMember(commandMap);
		}
		
	    if(resultInt == 1) {
	    	return getMessageModel("msgAndRedirect", "회원가입이 완료되었습니다.", "/main");
	    }else {
	    	return getMessageModel("msgAndRedirect", "회원가입에 실패했습니다", "/main");
	    }
	    
	}
	
	@ResponseBody
	@RequestMapping(value={"/web/member/passwordChk"} , method = RequestMethod.POST)
	public Map<String, Object> passwordChk(HttpServletRequest request, HttpServletResponse response , @RequestParam Map<String, Object> commandMap) throws Exception{ commandMap = init(request, commandMap);
		
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
