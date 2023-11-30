package finance.app.interest.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import finance.common.mapper.CommonMapper;

@Service("appInterestService")
public class AppInterestServiceImpl implements AppInterestService{
	
	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "appInterestMapper.";
	
	@Override
	public List<Map<String, Object>> getInterListYear(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getInterListYear");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;
	}
	
	@Override
	public List<Map<String, Object>> getInterListHalf(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getInterListHalf");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;
	}
	
	@Override
	public Integer insertInterest(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"insertInterest");
	    Integer resultInt = (Integer) commonMapper.insert(param);
	    return resultInt;
	}
	
	@Override
	public Integer deleteInterest(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"deleteInterest");
	    Integer resultInt = (Integer) commonMapper.delete(param);
	    return resultInt;
	}
}
