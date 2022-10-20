package src.main.finance.cms.report.service;

import java.util.List;
import java.util.Map;

public interface ReportService {

	public String create(Map<String, Object> map);

	public List<Map<String, Object>> getReportList(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getReportDetailList(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getReportSearch(Map<String, Object> param) throws Exception;

	
}
