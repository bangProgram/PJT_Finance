package finance.app.transaction.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import finance.common.mapper.CommonMapper;

@Service("appTransactionService")
public class AppTransactionServiceImpl implements AppTransactionService {
	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "appTransactionMapper.";

	@Override
	public List<Map<String, Object>> getTransRecord(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getTransRecord");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
}
