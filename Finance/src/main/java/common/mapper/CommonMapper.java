package common.mapper;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("CommonMapper")
public class CommonMapper extends SqlSessionDaoSupport{
	
	/**
	 * mapperId 등록
	 */
	public String mapperId(Map<String, Object> param) {
		String mId = (String) param.get("mId");
		System.out.println("Query ID : "+mId);
		return mId;
	}
	
	/**
	 * 리스트조회
	 */
	public List<Map<String,Object>> getList(Map<String,Object> param) throws Exception{

		return getSqlSession().selectList(mapperId(param),param);
	}
	
	/**
	 * 등록
	 */
	public Object insert(Map<String,Object> param) throws Exception{
		return getSqlSession().insert(mapperId(param),param);
	}
}
