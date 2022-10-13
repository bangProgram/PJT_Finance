package src.main.finance.cms.portfolio.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.mapper.CommonMapper;

@Service("portfolioService")
public class PortfolioServiceImpl implements PortfolioService {
	
	private CommonMapper commonMapper;
	
	private String namespace = "portfolioMapper.";
	
	@Override
	public Integer insertPortFolio(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"insertPortFolio");
	    Integer resultInt = (Integer) commonMapper.insert(param);
	    return resultInt;

	}
	
	@Override
	public List<Map<String, Object>> getPortFolioList(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getPortFolioList");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
	
	@Override
	public List<Map<String, Object>> getPortFolioDetail(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getPortFolioDetail");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}

}