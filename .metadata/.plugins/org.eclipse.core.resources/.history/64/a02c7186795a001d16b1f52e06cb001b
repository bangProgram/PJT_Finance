package finance.common.mapper;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository("CommonMapper")
public class CommonMapper extends CommonDAO{
	
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

		return selectList(mapperId(param),param);
	}
	
	/**
	 * 등록
	 */
	public Object insert(Map<String,Object> param) throws Exception{
		return insert(mapperId(param),param);
	}
}
