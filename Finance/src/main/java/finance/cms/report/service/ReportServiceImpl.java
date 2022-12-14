package finance.cms.report.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finance.common.mapper.CommonMapper;




@Service("ReportService")
public class ReportServiceImpl implements ReportService {
	
	
	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "reportMapper.";
	
	@Override
	public List<Map<String, Object>> getBsnsYearList(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getBsnsYearList");
	    List<Map<String, Object>> resultList = commonMapper.getList(param);
	    return resultList;

	}
	
	@Override
	public List<Map<String, Object>> getReprtCorpCode(Map<String, Object> param) throws Exception {
	    param.put("mId", namespace+"getReprtCorpCode");
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