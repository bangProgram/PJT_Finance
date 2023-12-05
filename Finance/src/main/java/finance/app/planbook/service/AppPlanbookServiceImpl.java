package finance.app.planbook.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import finance.common.mapper.CommonMapper;

@Service("appPlanbookService")
public class AppPlanbookServiceImpl implements AppPlanbookService{
	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "appPlanbookMapper.";
	
	@Override
	public List<Map<String, Object>> getPlanbookList(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getPlanbookList");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;
	}
	
	@Override
	public Map<String, Object> getPlanbookDetail(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getPlanbookDetail");
	    Map<String, Object> result = commonMapper.get(param);
	    return result;
	}
	
	@Override
	public List<Map<String, Object>> getInterListHalf(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getInterListHalf");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;
	}
	
	@Override
	public Integer addInterest(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"addInterest");
	    Integer resultInt = (Integer) commonMapper.insert(param);
	    return resultInt;
	}
	
	@Override
	public Integer delInterest(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"delInterest");
	    Integer resultInt = (Integer) commonMapper.delete(param);
	    return resultInt;
	}
}
