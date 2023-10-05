package finance.app.member.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
}
