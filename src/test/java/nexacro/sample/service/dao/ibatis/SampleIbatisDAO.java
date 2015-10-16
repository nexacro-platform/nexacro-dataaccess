package nexacro.sample.service.dao.ibatis;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import nexacro.sample.vo.SampleVO;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.nexacro.spring.dao.ibatis.NexacroIbatisMetaDataProvider;

/**
 * <pre>
 * Test를 위한 DAO Sample Class
 * </pre>
 * 
 * @ClassName   : SampleDAO.java
 * @Description : Sample DAO Class
 * @author djkim
 * @since 2012. 1. 31.
 * @version 1.0
 * @see
 * @Modification Information
 * 
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2012. 1. 31.     djkim     최초 생성
 * </pre>
 */
@Repository("sampleIbatisDAO")
public class SampleIbatisDAO extends SqlMapClientDaoSupport {

//	// meta data 획득. proxy 처리.
//	@Resource(name = "sampleIbatisDAO")
//	private SampleIbatisDAO self;
	
	@Resource(name = "ibatisMetaDataProvider")
	private NexacroIbatisMetaDataProvider ibatisMetaDataProvider;
	
	@Resource(name = "sqlMapClient")
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	public List<SampleVO> selectSampleVoList(SampleVO searchVO) {
		return list("sampleDAO.selectSampleVOList", searchVO);
	}

	public List<Map> selectSampleVoMap(SampleVO searchVO) {
//		return self.list("sampleDAO.selectSampleVOMap", searchVO);
		return list("sampleDAO.selectSampleVOMap", searchVO);
	}
	
	public List list(String queryId, Object obj) {
		List<?> list = getSqlMapClientTemplate().queryForList(queryId, obj);
		if (list == null || list.size() == 0) {
			list = getMetaData(queryId, obj, list);
		}
		return list;
	}

	private List<?> getMetaData(String queryId, Object parameterObject, List originalResult) {
		return (List<?>) ibatisMetaDataProvider.doGetQueryMetaData(this, new Object[]{queryId, parameterObject});
	}
}
