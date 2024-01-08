package finance.cms.main;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;
import java.util.zip.InflaterInputStream;

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
	        String input = br.readLine();
			int total = 0;
	        for(int i=0; i<input.length(); i++) {
	        	total += numChk(input.charAt(i));
	        }
	        System.out.println(total);
	        
		}
		catch (Exception e) {
			System.out.println("error : IOexception");
		}
		
		ModelAndView mav = new ModelAndView();
		String resultUrl = "main/test.jsp";
		mav.setViewName(resultUrl);
		
		return mav;
		
	}
	
	public Double maxScore(String[] scList) {
		double max = 0.0;
		for (int i = 0; i < scList.length; i++) {
        	double score = Double.parseDouble(scList[i]);
            if(max < score) {
            	max = score;
            }
        }
		return max;
	}
	
	public static int numChk(Character input) {
		Character[][] str = new Character[][] {{},{},{},{'A','B','C'},{'D','E','F'},{'G','H','I'},{'J','K','L'},{'M','N','O'},{'P','Q','R','S'},{'T','U','V'},{'W','X','Y','Z'},{}};
		for(int i=0; i<str.length; i++) {
			for(int j=0; j<str[i].length; j++) {
				if(str[i][j] == input) {
					return i;
				}
			}
		}
		return 0;
	}
}
