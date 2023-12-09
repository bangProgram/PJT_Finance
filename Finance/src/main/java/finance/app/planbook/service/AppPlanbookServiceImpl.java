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
	public Map<String, Object> getPlanDetailInfo(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getPlanDetailInfo");
	    Map<String, Object> result = commonMapper.get(param);
	    return result;
	}
	
	@Override
	public List<Map<String, Object>> getPlanDetailMemo(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getPlanDetailMemo");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;
	}
	
	@Override
	public Integer addPlanMemo(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"addPlanMemo");
	    Integer resultInt = (Integer) commonMapper.insert(param);
	    return resultInt;
	}
	
	@Override
	public Integer delPlanMemo(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"delPlanMemo");
	    Integer resultInt = (Integer) commonMapper.delete(param);
	    return resultInt;
	}
}
