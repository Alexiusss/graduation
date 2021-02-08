package ru.voting_system.util;

import org.ehcache.core.spi.service.ServiceFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JpaUtil {

    @PersistenceContext
    private EntityManager em;

    public void clear2ndLevelHibernateCache() {
        Session s = (Session) em.getDelegate();
        SessionFactory sf = s.getSessionFactory();
        sf.getCache().evictAllRegions();
    }

}
