package finance.cms.portfolio.service;

import java.util.List;
import java.util.Map;

public interface PortfolioService {

	public Integer insertPortFolio(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getPortFolioList(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getPortFolioDetail(Map<String, Object> param) throws Exception;

	public Integer insertPortfolioCorp(Map<String, Object> param) throws Exception;

	public Integer deletePortfolioCorp(Map<String, Object> param) throws Exception;
	
}
