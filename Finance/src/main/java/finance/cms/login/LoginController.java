package finance.cms.login;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.mvel2.sh.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import finance.cms.member.MemberVO;
import finance.cms.member.service.MemberService;
import finance.common.Controller.DefaultController;
import finance.common.util.SecurityJavaConfig;

@Controller
public class LoginController extends DefaultController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
		
	
	@RequestMapping(value={"/web/login"})
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response , @RequestParam Map<String, Object> commandMap) throws Exception{ 
		commandMap = init(request, commandMap);
		
		Map<String, Object> param = new HashMap<String, Object>();
		HttpSession session = request.getSession(true);
		
		String inputPassWd = commandMap.get("password").toString();
		MemberVO memberVO = memberService.getMemberVO(commandMap);
		
		//사용자 정보 있을경우 진입
		if(memberVO != null) {
			//입력받은 비밀번호와 사용자 비밀번호가 일치 할 경우 진입
			System.out.println(inputPassWd +" : 입력 / 저장 : "+memberVO.getPassword());
			if(bCryptPasswordEncoder.matches(inputPassWd, memberVO.getPassword())) {
				session.setAttribute(SESSION_MEMBER, memberVO);
			}else{
				memberService.updateErrorCnt(commandMap);
				return getMessageModel("msgAndRedirect", "사용자 정보가 일치하지 않습니다.", "/main");
			}
		}
		
		
		
		ModelAndView mav = new ModelAndView();
	    String resultURL = "redirect:/main";
	    mav.setViewName(resultURL);
	    
	    return mav;
	}
	
	@RequestMapping(value={"/web/logout"})
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response , @RequestParam Map<String, Object> commandMap) throws Exception{ commandMap = init(request, commandMap);
		
		Map<String, Object> param = new HashMap<String, Object>();
		HttpSession session = request.getSession(true);
		
		MemberVO vo = (MemberVO) session.getAttribute(SESSION_MEMBER);
		
		//사용자 정보 있을경우 진입
		if(vo != null) {
			session.invalidate();
		}else {
			return getMessageModel("msgAndRedirect", "사용자 정보가 없습니다.", "/main");
		}
		
		ModelAndView mav = new ModelAndView();
	    String resultURL = "redirect:/main";
	    mav.setViewName(resultURL);
	    
	    return mav;
	}
}
