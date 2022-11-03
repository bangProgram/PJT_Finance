package finance.common.mapper;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public abstract class CommonDAO{
	
	@Inject
	private SqlSession sql;

	/**
	 * 리스트조회
	 */
	public <E> List<E> selectList(String queryId, Object paramObject) {
		return sql.selectList(queryId,paramObject);
	}
	
	/**
	 * 등록
	 */
	public int insert(String queryId, Object paramObject) {
		return sql.insert(queryId,paramObject);
	}
	
	/**
	 * 삭제
	 */
	public int delete(String queryId, Object paramObject) {
		return sql.delete(queryId,paramObject);
	}
}
