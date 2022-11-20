package finance.cms.report.service;

import java.util.List;
import java.util.Map;

public interface ReportService {

	public List<Map<String, Object>> getReportList(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getReportDetailList(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getReprtCorpCode(Map<String, Object> param) throws Exception;

	public List<Map<String, Object>> getBsnsYearList(Map<String, Object> param) throws Exception;

	
}
