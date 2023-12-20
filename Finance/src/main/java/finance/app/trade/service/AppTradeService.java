package finance.app.trade.service;

import java.util.List;
import java.util.Map;

public interface AppTradeService {
	public List<Map<String, Object>> initTradeList(Map<String, Object> param) throws Exception;
	
	public Integer addTrade(Map<String, Object> param) throws Exception;

	public Integer delTrade(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getTradeRecord(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getTradeCorpList(Map<String, Object> param) throws Exception;

	public Integer addTradeCorpDetail(Map<String, Object> param) throws Exception;
	
	public Integer delTradeCorpDetail(Map<String, Object> param) throws Exception;
	
	public Map<String, Object> getTradeCorpDetailInfo(Map<String, Object> param) throws Exception;
}
