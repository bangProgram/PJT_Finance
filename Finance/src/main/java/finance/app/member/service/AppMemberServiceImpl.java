package finance.app.member.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import finance.cms.member.MemberVO;
import finance.common.mapper.CommonMapper;

@Service("appMemberService")
public class AppMemberServiceImpl implements AppMemberService {

	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "memberMapper.";
	
	@Override
	public Integer createMember(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"insertMember");
	    Integer resultInt = (Integer) commonMapper.insert(param);
	    return resultInt;
	}
	
	@Override
	public MemberVO getMemberVO(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getMemberVO");
	    MemberVO memberVO = (MemberVO) commonMapper.getObject(param);
	    return memberVO;

	}
	
	@Override
	public Integer updateErrorCnt(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"updateErrorCnt");
	    Integer resultInt = (Integer) commonMapper.update(param);
	    return resultInt;

	}
	
	@Override
	public Map<String, Object> getMemberToJson(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getMemberToJson");
	    Map<String, Object> result = commonMapper.get(param);
	    return result;

	}
}
