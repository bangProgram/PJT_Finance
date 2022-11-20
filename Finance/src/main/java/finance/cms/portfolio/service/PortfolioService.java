package finance.cms.portfolio.service;

import java.util.List;
import java.util.Map;

public interface PortfolioService {

	public Integer insertPortFolio(Map<String, Object> param) throws Exception;

	public Map<String, Object> getPortfolio(Map<String, Object> param) throws Exception;
	
	public Map<String, Object> getPortCorp(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getPortCorpList(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getPortCorpDetailList(Map<String, Object> param) throws Exception;

	public Integer mergePortfolioCorp(Map<String, Object> param) throws Exception;

	public Integer deletePortfolioCorp(Map<String, Object> param) throws Exception;

	public Integer insertPortfolioAsset(Map<String, Object> param) throws Exception;

	public Integer addPortfolioDetail(Map<String, Object> param) throws Exception;
	
	public Integer delPortfolioDetail(Map<String, Object> param) throws Exception;

	public Integer insertPortfolioOpinonAmt(Map<String, Object> param) throws Exception;

	public Map<String, Object> getPortAmount(Map<String, Object> param) throws Exception;

	
}
