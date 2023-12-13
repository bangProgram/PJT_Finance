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
	public Map<String, Object> getAssetAmount(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getAssetAmount");
	    Map<String, Object> result = commonMapper.get(param);
	    return result;
	}
	
	@Override
	public List<Map<String, Object>> getAssetList(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getAssetList");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
	
	
	@Override
	public List<Map<String, Object>> getAssetProportion(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getAssetProportion");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
	
	@Override
	public Integer mergeAssetAmount(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"mergeAssetAmount");
	    Integer resultInt = (Integer) commonMapper.update(param);
	    return resultInt;
	}

}
