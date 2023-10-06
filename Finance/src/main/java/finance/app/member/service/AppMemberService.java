package finance.app.member.service;

import java.util.Map;

import finance.cms.member.MemberVO;

public interface AppMemberService {

	public Integer createMember(Map<String, Object> param) throws Exception;
	
	public MemberVO getMemberVO(Map<String, Object> param) throws Exception;

	public Integer updateErrorCnt(Map<String, Object> param) throws Exception;
	
	public Map<String, Object> getMemberForJson(Map<String, Object> param) throws Exception;
}
