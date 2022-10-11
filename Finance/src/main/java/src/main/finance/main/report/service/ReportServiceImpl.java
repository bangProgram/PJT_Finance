package src.main.finance.main.report.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("reportService")
public class ReportServiceImpl implements ReportService {
	
	@Autowired
 	ReportDao reportDao;
	
	@Override
	public String create(Map<String, Object> map) {
	    int affectRowCount = this.reportDao.insert(map);
	    if (affectRowCount ==  1) {
	        return map.get("book_id").toString();
	    }
	    return null;

	}

}