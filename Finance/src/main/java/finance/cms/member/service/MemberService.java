package finance.cms.member.service;

import java.util.List;
import java.util.Map;

import finance.cms.member.MemberVO;

public interface MemberService {

	public Integer createMember(Map<String, Object> param) throws Exception;
	
	public MemberVO getMemberVO(Map<String, Object> param) throws Exception;
	
	public Integer updateErrorCnt(Map<String, Object> param) throws Exception;
	
	public Map<String, Object> getMember(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getCorpList(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getCorpDetail(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getCorporationGrowth(Map<String, Object> param) throws Exception;

	
}
