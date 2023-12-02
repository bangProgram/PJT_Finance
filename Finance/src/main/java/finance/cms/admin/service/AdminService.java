package finance.cms.admin.service;

import java.util.List;
import java.util.Map;

public interface AdminService {

	public Integer mergeReport(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getCorpList(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getCorpDetail(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getCorporationGrowth(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getCorpListForMerge(Map<String, Object> param) throws Exception;

	public Integer mergeCorpDetail(Map<String, Object> param) throws Exception;
	
	public Integer mergeMainBplc(Map<String, Object> param) throws Exception;
}
