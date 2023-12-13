package finance.app.assetmamage.service;

import java.util.List;
import java.util.Map;

public interface AppAssetmanageService {
	public Map<String, Object> getAssetAmount(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getAssetList(Map<String, Object> param) throws Exception;
	
	
	public List<Map<String, Object>> getAssetProportion(Map<String, Object> param) throws Exception;
	
	public Integer mergeAssetAmount(Map<String, Object> param) throws Exception;

}
