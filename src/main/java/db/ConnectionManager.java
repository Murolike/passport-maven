package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Component;


@Component
public class ConnectionManager {

    private final SessionFactory sessionFactory;

    public ConnectionManager() {
        this.sessionFactory = new MetadataSources(new StandardServiceRegistryBuilder().configure("META-INF/hibernate.cfg.xml").build())
                .buildMetadata()
                .buildSessionFactory();
    }

    public Session getSession() {
        return this.sessionFactory.openSession();
    }
}
