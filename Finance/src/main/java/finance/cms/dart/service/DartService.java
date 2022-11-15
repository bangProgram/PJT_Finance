package finance.cms.dart.service;

import java.util.List;
import java.util.Map;

public interface DartService {

	public Map<String, Object> getDart(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getPortListForSchedule(Map<String, Object> param) throws Exception;

	public Integer updatePortForSchedule(Map<String, Object> param) throws Exception;
}
