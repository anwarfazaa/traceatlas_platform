package org.traceatlas.platform.database.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.traceatlas.platform.database.entities.RestAPI;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Load configuration and mappings
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            configuration.addAnnotatedClass(RestAPI.class);

            // Build SessionFactory
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
