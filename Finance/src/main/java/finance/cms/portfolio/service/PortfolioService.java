package finance.cms.portfolio.service;

import java.util.List;
import java.util.Map;

public interface PortfolioService {

	public Integer insertPortFolio(Map<String, Object> param) throws Exception;

	public Map<String, Object> getPortfolio(Map<String, Object> param) throws Exception;
	
	public Map<String, Object> getPortCorp(Map<String, Object> param) throws Exception;
	
	public List<Map<String, Object>> getPortCorpList(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getPortFolioDetail(Map<String, Object> param) throws Exception;

	public Integer insertPortfolioCorp(Map<String, Object> param) throws Exception;

	public Integer deletePortfolioCorp(Map<String, Object> param) throws Exception;

	public Integer inertPortfolioAsset(Map<String, Object> param) throws Exception;

	
}
