package finance.cms.portfolio.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finance.common.mapper.CommonMapper;




@Service("portfolioService")
public class PortfolioServiceImpl implements PortfolioService {
	
	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "portfolioMapper.";
	
	@Override
	public Integer insertPortFolio(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"insertPortFolio");
	    Integer resultInt = (Integer) commonMapper.insert(param);
	    return resultInt;

	}
	
	@Override
	public Map<String, Object> getPortfolio(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getPortfolio");
	    Map<String, Object> resultList = commonMapper.get(param);
	    return resultList;

	}
	
	@Override
	public Map<String, Object> getPortAmount(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getPortAmount");
	    Map<String, Object> resultList = commonMapper.get(param);
	    return resultList;

	}
	
	@Override
	public Map<String, Object> getPortCorp(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getPortCorp");
	    Map<String, Object> resultList = commonMapper.get(param);
	    return resultList;

	}
	
	@Override
	public List<Map<String, Object>> getPortCorpList(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getPortCorpList");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
	
	
	@Override
	public List<Map<String, Object>> getPortCorpDetailList(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getPortCorpDetailList");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;
	}
	
	@Override
	public Integer mergePortfolioCorp(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"mergePortfolioCorp");
	    Integer resultInt = (Integer) commonMapper.insert(param);
	    return resultInt;
	}
	
	@Override
	public Integer deletePortfolioCorp(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"deletePortfolioCorp");
	    Integer resultInt = (Integer) commonMapper.delete(param);
	    return resultInt;
	}
	
	@Override
	public Integer insertPortfolioAsset(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"insertPortfolioAsset");
	    Integer resultInt = (Integer) commonMapper.insert(param);
	    return resultInt;
	}
	
	@Override
	public Integer addPortfolioDetail(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"addPortfolioDetail");
	    Integer resultInt = (Integer) commonMapper.insert(param);
	    return resultInt;
	}
	
	@Override
	public Integer delPortfolioDetail(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"delPortfolioDetail");
	    Integer resultInt = (Integer) commonMapper.delete(param);
	    return resultInt;
	}
	
	@Override 
	public Integer insertPortfolioOpinonAmt(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"insertPortfolioOpinonAmt");
	    Integer resultInt = (Integer) commonMapper.insert(param);
	    return resultInt;
	}
}