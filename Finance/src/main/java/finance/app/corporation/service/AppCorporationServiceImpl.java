package finance.app.corporation.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import finance.common.mapper.CommonMapper;

public class AppCorporationServiceImpl implements AppCorporationService {
	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "appCorporationMapper.";
	
	@Override
	public List<Map<String, Object>> getCorpList(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getCorpList");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
}
