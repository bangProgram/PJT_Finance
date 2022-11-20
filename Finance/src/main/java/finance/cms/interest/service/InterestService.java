package finance.cms.interest.service;

import java.util.List;
import java.util.Map;

public interface InterestService {

	public List<Map<String, Object>> getReportList(Map<String, Object> param) throws Exception;

	public Integer insertInterestCorp(Map<String, Object> param) throws Exception;

	public Integer deleteInterestCorp(Map<String, Object> param) throws Exception;

	public Map<String, Object> getInterest(Map<String, Object> param) throws Exception;

}
