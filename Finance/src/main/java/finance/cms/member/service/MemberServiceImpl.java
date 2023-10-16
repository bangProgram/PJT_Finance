package finance.cms.member.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finance.cms.member.MemberVO;
import finance.common.mapper.CommonMapper;




@Service("memberService")
public class MemberServiceImpl implements MemberService {
	
	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "memberMapper.";
	
	@Override
	public Integer createMember(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"createMember");
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
	public Map<String, Object> getMember(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getMember");
	    Map<String, Object> result = commonMapper.get(param);
	    return result;

	}
	
	@Override
	public List<Map<String, Object>> getCorporationGrowth(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getCorporationGrowth");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
	
	@Override
	public List<Map<String, Object>> getCorpList(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getCorpList");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
	
	@Override
	public List<Map<String, Object>> getCorpDetail(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getCorpDetail");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}

}