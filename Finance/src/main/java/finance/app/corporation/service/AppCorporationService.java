package finance.app.corporation.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

public interface AppCorporationService {
	public List<Map<String, Object>> getCorpListYear(Map<String, Object> param) throws Exception;
	public List<Map<String, Object>> getCorpListHalf(Map<String, Object> param) throws Exception;
	public List<Map<String, Object>> getCorpPerform(Map<String, Object> param) throws Exception;
}
