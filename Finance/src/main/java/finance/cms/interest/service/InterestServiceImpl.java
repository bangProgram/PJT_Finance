package finance.cms.interest.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finance.common.mapper.CommonMapper;




@Service("InterestService")
public class InterestServiceImpl implements InterestService {
	
	
	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "interestMapper.";
	
	@Override
	public List<Map<String, Object>> getReportList(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getReportList");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
	
	@Override
	public Integer insertInterestCorp(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"insertInterestCorp");
	    Integer resultInt = (Integer) commonMapper.insert(param);
	    return resultInt;
	}
	
	@Override
	public Integer deleteInterestCorp(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"deleteInterestCorp");
	    Integer resultInt = (Integer) commonMapper.delete(param);
	    return resultInt;
	}
	

}