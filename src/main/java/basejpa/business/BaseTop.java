package basejpa.business;

import basejpa.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Optional;

import static basejpa.util.DSUtil.trueThrow;

public abstract class BaseTop<ID, EN, DAO extends BaseDao<EN, ID>> implements BaseEx {
    @Autowired
    protected DataSource dataSource;
    @Autowired
    protected EntityManager entityManager;
    @Autowired
    protected DAO dao;

    protected EN getById(ID id) {
        trueThrow(null == id, bizEx("传入ID为空"));
        Optional<EN> optional = dao.findById(id);
        trueThrow(!optional.isPresent(), bizEx("传入ID错误"));
        return optional.get();
    }
}