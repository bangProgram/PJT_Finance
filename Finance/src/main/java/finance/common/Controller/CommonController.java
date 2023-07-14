package finance.common.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.ModelAndView;

@Controller
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
	
	protected final ModelAndView getMessageModel(String msgType, String msg, String redirectUrl ) {
		
		ModelAndView model = new ModelAndView("common/messagePage.jsp");
		model.addObject("msgType", msgType);
		model.addObject("msg", msg);
		model.addObject("redirectUrl", redirectUrl);
		 
		return model;
	}
}
