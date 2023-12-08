package finance.app.assetmamage.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import finance.common.mapper.CommonMapper;

@Service("appAssetmanageService")
public class AppAssetmanageServiceImpl implements AppAssetmanageService {
	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "appAssetmanageMapper.";
	
	@Override
	public List<Map<String, Object>> getAssetList(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getAssetList");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
	
	@Override
	public List<Map<String, Object>> getAssetRecord(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getAssetRecord");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}

}