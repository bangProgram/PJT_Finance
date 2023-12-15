package finance.app.trade.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import finance.common.mapper.CommonMapper;

@Service("appTradeactionService")
public class AppTradeServiceImpl implements AppTradeService {
	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "appTradeMapper.";

	@Override
	public List<Map<String, Object>> initTradeList(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"initTradeList");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;
	}
	
	@Override
	public Integer addTrade(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"addTrade");
	    Integer resultInt = (Integer) commonMapper.insert(param);
	    return resultInt;
	}
	
	@Override
	public Integer delTrade(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"delTrade");
	    Integer resultInt = (Integer) commonMapper.delete(param);
	    return resultInt;
	}

	@Override
	public List<Map<String, Object>> getTradeRecord(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getTradeRecord");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
	
	@Override
	public List<Map<String, Object>> getTradeCorpList(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getTradeCorpList");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
	
	@Override
	public Integer addTradeCorpDetail(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"addTradeCorpDetail");
	    Integer resultInt = (Integer) commonMapper.insert(param);
	    return resultInt;
	}
	
	@Override
	public Integer delTradeCorpDetail(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"delTradeCorpDetail");
	    Integer resultInt = (Integer) commonMapper.insert(param);
	    return resultInt;
	}
}
