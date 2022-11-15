package finance.common.Controller;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

@Repository("CommonController")
public class CommonController {

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
