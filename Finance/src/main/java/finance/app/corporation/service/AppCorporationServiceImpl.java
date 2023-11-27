package finance.app.corporation.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import finance.common.mapper.CommonMapper;

@Service("appCorporationService")
public class AppCorporationServiceImpl implements AppCorporationService {
	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "appCorporationMapper.";
	
	@Override
	public List<Map<String, Object>> getCorpListYear(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getCorpListYear");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
	
	@Override
	public List<Map<String, Object>> getCorpListHalf(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getCorpListHalf");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
}
