package finance.app.corporation.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface AppCorporationService {
	public List<Map<String, Object>> getCorpList(Map<String, Object> param) throws Exception;
}
