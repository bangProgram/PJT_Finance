package finance.app.util.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;


public interface AppUtilService  {
	public List<Map<String, Object>> getYearList(Map<String, Object> param) throws Exception;

}
