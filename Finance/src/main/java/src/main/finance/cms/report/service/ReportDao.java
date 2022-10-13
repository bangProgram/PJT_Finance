package src.main.finance.cms.report.service;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDao {
	 @Autowired
	 SqlSessionTemplate sqlSessionTemplate;
 
	 public int insert(Map<String, Object> map) {
		  return this.sqlSessionTemplate.insert("report.insert", map);
	 }
}