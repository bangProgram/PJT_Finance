package finance.cms.dart.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import finance.cms.portfolio.service.PortfolioService;
import finance.common.mapper.CommonMapper;

@Service("dartService")
public class DartServiceImpl implements DartService {

	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "dartMapper.";
	
	@Override
	public Map<String, Object> getDart(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getDart");
	    Map<String, Object> resultList = commonMapper.get(param);
	    return resultList;

	}
	
	@Override
	public List<Map<String, Object>> getPortListForSchedule(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getPortListForSchedule");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;
	}
	
	@Override
	public Integer updatePortForSchedule(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"updatePortForSchedule");
	    Integer resultInt = (Integer) commonMapper.update(param);
	    return resultInt;
	}
	
}
