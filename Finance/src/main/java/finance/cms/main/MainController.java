package finance.cms.main;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
	
	@RequestMapping(value={"/main/privacyPolicy"} , method = RequestMethod.GET)
	public ModelAndView goPrivacyPolicy() {
	    return new ModelAndView("common/privacyPolicy.jsp");
	}
	
	@RequestMapping(value={"/test"} , method = RequestMethod.GET)
	public ModelAndView test(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> commandMap) throws Exception{
		try {
			System.out.println("입력하세요 >> ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			Scanner sc = new Scanner(System.in);
			int cnt, min = 0, max = 0;
			cnt = Integer.parseInt(br.readLine());
			String[] numList = br.readLine().split(" ");
			for(int i=0; i<cnt; i++) {
				int num = Integer.parseInt(numList[i]);
				if(i == 0) {
					min = num;
					max = num;
				}else {
					if(num > max) {
						max = num;
					}
					if(num < min) {
						min = num;
					}
				}
			}
			System.out.println(min+" "+max);
			sc.close();
		}
		catch (Exception e) {
			System.out.println("error : IOexception");
		}
		
		ModelAndView mav = new ModelAndView();
		String resultUrl = "main/test.jsp";
		mav.setViewName(resultUrl);
		
		return mav;
		
	}
}
