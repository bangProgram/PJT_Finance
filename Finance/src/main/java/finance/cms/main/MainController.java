package finance.cms.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import finance.cms.member.MemberVO;
import finance.cms.member.service.MemberService;
import finance.common.Controller.DefaultController;

@Controller
public class MainController extends DefaultController{
	
	@Autowired
	MemberService memberservice;

	@RequestMapping(value={"/main","/"} , method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> commandMap) throws Exception{
		
		HttpSession session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		
		MemberVO vo = (MemberVO) session.getAttribute(SESSION_MEMBER);
		if(vo != null) {
			System.out.println("getMaxInactiveInterval= "+ session.getMaxInactiveInterval());
			System.out.println("creationTime= "+ session.getCreationTime());
			System.out.println("lastAccessTime= "+ session.getLastAccessedTime());
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", vo.getUser_id());
			Map<String,Object> member = memberservice.getMember(param);
			mav.addObject("member", member);
		}
		
		
		String resultUrl = "main/main.jsp";
		
		mav.setViewName(resultUrl);
		
		return mav;
		
	}
	
	@RequestMapping(value={"/bplc/report"} , method = RequestMethod.GET)
	public ModelAndView goReport() {
	    return new ModelAndView("report/reportView");
	}
}
