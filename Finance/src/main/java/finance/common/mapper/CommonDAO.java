package finance.common.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public abstract class CommonDAO extends SqlSessionDaoSupport{
	
	@Resource(name = "sqlSession")
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.getSqlSessionFactory();
	}

	/**
	 * 리스트조회
	 */
	public <E> List<E> selectList(String queryId, Object paramObject) {
		return getSqlSession().selectList(queryId,paramObject);
	}
	
	/**
	 * 등록
	 */
	public int insert(String queryId, Object paramObject) {
		return getSqlSession().insert(queryId,paramObject);
	}
}
