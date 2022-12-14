package finance.cms.corporation.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finance.common.mapper.CommonMapper;




@Service("corporationService")
public class CorporationServiceImpl implements CorporationService {
	
	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "corporationMapper.";
	
	@Override
	public Integer insertCorp(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"insertCorp");
	    Integer resultInt = (Integer) commonMapper.insert(param);
	    return resultInt;

	}
	
	@Override
	public List<Map<String, Object>> getCorporationGrowth(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getCorporationGrowth");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
	
	@Override
	public List<Map<String, Object>> getCorpList(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getCorpList");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
	
	@Override
	public List<Map<String, Object>> getCorpDetail(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getCorpDetail");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}

}