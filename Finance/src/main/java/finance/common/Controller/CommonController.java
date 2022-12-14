package finance.common.Controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

@Repository("CommonController")
public class CommonController {
	
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

	public static JSONArray convertListToJson(List<Map<String, Object>> list) {
		JSONArray jsonArray = new JSONArray();
		for(Map<String, Object> map : list) {
			jsonArray.add(convertMapToJson(map));
		}
		return jsonArray;
	}
	
	public static JSONObject convertMapToJson(Map<String, Object> map) {
		JSONObject json = new JSONObject();
		for(Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			json.put(key, value);
		}
		return json;
	}
	
	public static JSONArray mergeJsonArray(JSONArray list1, JSONArray list2) {
		JSONArray jsonArray = list1;
		for(int i=0; i<list2.size(); i++) {
			jsonArray.add(list2.get(i));
		}
		return jsonArray;
	}
}
