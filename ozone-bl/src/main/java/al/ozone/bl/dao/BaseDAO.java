package al.ozone.bl.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * Bridge class between myBatis and Spring.
 * All DAOs will extend this class.
 * 
 * @author Ermal Aliraj
 */
public abstract class BaseDAO extends SqlSessionDaoSupport {

    public BaseDAO() {
        System.out.println();
    }
}



