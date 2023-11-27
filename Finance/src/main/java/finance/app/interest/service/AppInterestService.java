package finance.app.interest.service;

import java.util.List;
import java.util.Map;

public interface AppInterestService {
	public List<Map<String, Object>> getInterListYear(Map<String, Object> param) throws Exception;
	public List<Map<String, Object>> getInterListHalf(Map<String, Object> param) throws Exception;
}
