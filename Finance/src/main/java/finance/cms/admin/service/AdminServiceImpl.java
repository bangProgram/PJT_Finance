package finance.cms.admin.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finance.common.mapper.CommonMapper;




@Service("adminService")
public class AdminServiceImpl implements AdminService {
	
	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "adminMapper.";
	
	@Override
	public Integer mergeReport(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"mergeReport");
	    Integer resultInt = (Integer) commonMapper.insert(param);
	    return resultInt;

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
	
	@Override
	public List<Map<String, Object>> getCorpListForMerge(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getCorpListForMerge");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
	
	@Override
	public Integer mergeCorpDetail(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"mergeCorpDetail");
	    Integer resultInt = (Integer) commonMapper.insert(param);
	    return resultInt;

	}
	
	@Override
	public Integer mergeMainBplc(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"mergeMainBplc");
	    Integer resultInt = (Integer) commonMapper.insert(param);
	    return resultInt;

	}
}