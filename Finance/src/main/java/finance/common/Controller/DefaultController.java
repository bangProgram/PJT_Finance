package finance.common.Controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;

@Controller
public class DefaultController extends CommonController{

	public Map<String, Object> init( Map<String, Object> map) throws Exception{
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
		//로그인 기능 만들기 전까지 사용하도록
		returnMap.put("curUserId", "SYSTEM_JB");
		return returnMap;
	}
}
