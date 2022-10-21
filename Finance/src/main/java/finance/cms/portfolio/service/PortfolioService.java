package finance.cms.portfolio.service;

import java.util.List;
import java.util.Map;

public interface PortfolioService {

	Integer insertPortFolio(Map<String, Object> param) throws Exception;

	List<Map<String, Object>> getPortFolioList(Map<String, Object> param) throws Exception;

	List<Map<String, Object>> getPortFolioDetail(Map<String, Object> param) throws Exception;

	
}
