package finance.app.portfolio.service;

import java.util.Map;

import finance.cms.member.MemberVO;

public interface AppPortfolioService {
	public Integer createPortfolio(Map<String, Object> param) throws Exception;
	
	public Integer updatePortfolio(Map<String, Object> param) throws Exception;
	
	public Map<String, Object> getPortAmount(Map<String, Object> param) throws Exception;
	
	public Map<String, Object> getPortfolioToJson(Map<String, Object> param) throws Exception;
	
	public Integer insertPortfolioAsset(Map<String, Object> param) throws Exception;
}
