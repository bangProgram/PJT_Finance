package finance.common.Controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import finance.app.login.service.AppLoginService;

@Controller
public class DefaultController extends CommonController{

	@Autowired
	private AppLoginService appLoginService;

	public Map<String, Object> init(HttpServletRequest request, Map<String, Object> map) throws Exception{
		// 적절한 방법으로 HttpServletRequest 객체를 얻어옵니다.
		HttpSession session = request.getSession(true);
		
		System.out.println("JB commandMap Param: \n"+map.toString());
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		while(it.hasNext()) {
			String key = it.next();
			if(map.get(key) == null) {
				returnMap.put(key, "");
			}else {
				returnMap.put(key, map.get(key).toString().replace("<", "&lt;").replace(">", "&gt;").toString());
			}
		}
		
		String userId = "";
		//(웹용)로그인 사용자 체크
		
		if(appLoginService.isLogin()) {
			userId = appLoginService.getCurUserId();
			System.out.println("현재 로그인 된 ID : "+userId);
			returnMap.put("curUserId", userId);
		}
		return returnMap;
	}
}
