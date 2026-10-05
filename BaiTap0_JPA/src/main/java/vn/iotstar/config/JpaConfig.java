package vn.iotstar.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

@PersistenceContext
public class JpaConfig {
    // Khởi tạo Factory 1 lần duy nhất để không quá tải Database
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa-hibernate-sqlserver");

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}