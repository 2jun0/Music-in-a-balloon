package com.musicinaballoon;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCleaner {

    private final List<String> tables = new ArrayList<>();

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void findDatabaseTables() {
        List<Object[]> tableInfos = entityManager.createNativeQuery("SHOW TABLES").getResultList();

        for (Object[] tableInfo : tableInfos) {
            String tableName = (String) tableInfo[0];
            tables.add(tableName);
        }
    }

    public void clear() {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(this::truncate);
    }

    private void truncate(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();

        statement.execute("SET REFERENTIAL_INTEGRITY FALSE");
        for (String table : tables) {
            statement.execute(String.format("TRUNCATE TABLE %s", table));
            statement.execute(String.format("ALTER TABLE %s ALTER COLUMN id RESTART WITH 1", table));
        }
        statement.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }
}
