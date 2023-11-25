package finance.app.util.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import finance.common.mapper.CommonMapper;

@Service("appUtilServiceImpl")
public class AppUtilServiceImpl implements AppUtilService {
	
	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "appUtilMapper.";
	
	@Override
	public List<Map<String, Object>> getYearList(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getYearList");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}

}
