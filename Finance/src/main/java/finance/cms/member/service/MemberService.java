package finance.cms.member.service;

import java.util.List;
import java.util.Map;

public interface MemberService {

	public Integer insertMember(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getCorpList(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getCorpDetail(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getCorporationGrowth(Map<String, Object> param) throws Exception;

	
}
