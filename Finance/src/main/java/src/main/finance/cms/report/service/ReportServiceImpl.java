package src.main.finance.cms.report.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.mapper.CommonMapper;

@Service("reportService")
public class ReportServiceImpl implements ReportService {
	
	@Autowired
 	ReportDao reportDao;
	
	private CommonMapper commonMapper;
	
	private String namespace = "reportMapper.";
	
	@Override
	public String create(Map<String, Object> map) {
	    int affectRowCount = reportDao.insert(map);
	    if (affectRowCount ==  1) {
	        return map.get("book_id").toString();
	    }
	    return null;

	}
	
	@Override
	public List<Map<String, Object>> getReportSearch(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getReportSearch");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
	
	@Override
	public List<Map<String, Object>> getReportList(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getReportList");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
	
	@Override
	public List<Map<String, Object>> getReportDetailList(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getReportDetailList");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}

}