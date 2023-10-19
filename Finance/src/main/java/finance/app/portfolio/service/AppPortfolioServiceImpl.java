package finance.app.portfolio.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import finance.cms.member.MemberVO;
import finance.common.mapper.CommonMapper;

@Service("appPortfolioService")
public class AppPortfolioServiceImpl implements AppPortfolioService{
	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "appPortfolioMapper.";
	
	@Override
	public Integer createPortfolio(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"createPortfolio");
	    Integer resultInt = (Integer) commonMapper.insert(param);
	    return resultInt;
	}
	
	@Override
	public Integer updatePortfolio(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"updatePortfolio");
	    Integer resultInt = (Integer) commonMapper.update(param);
	    return resultInt;
	}
	
	@Override
	public Map<String, Object> getPortAmount(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getPortAmount");
	    Map<String, Object> result = commonMapper.get(param);
	    return result;
	}
	
	@Override
	public Map<String, Object> getPortfolioToJson(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getPortfolioToJson");
	    Map<String, Object> result = commonMapper.get(param);
	    return result;
	}
	
	@Override
	public Integer insertPortfolioAsset(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"insertPortfolioAsset");
	    Integer resultInt = (Integer) commonMapper.update(param);
	    return resultInt;
	}
}
