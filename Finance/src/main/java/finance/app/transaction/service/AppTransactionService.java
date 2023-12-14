package finance.app.transaction.service;

import java.util.List;
import java.util.Map;

public interface AppTransactionService {

	public List<Map<String, Object>> getTransRecord(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getTransCorpList(Map<String, Object> param) throws Exception;
}
