package finance.app.planbook.service;

import java.util.List;
import java.util.Map;

public interface AppPlanbookService {

	public List<Map<String, Object>> getPlanbookList(Map<String, Object> param) throws Exception;
	
	public Map<String, Object> getPlanbookDetail(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getInterListHalf(Map<String, Object> param) throws Exception;
	
	public Integer addInterest(Map<String, Object> param) throws Exception;

	public Integer delInterest(Map<String, Object> param) throws Exception;
}
