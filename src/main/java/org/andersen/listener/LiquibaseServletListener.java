package org.andersen.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.andersen.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class LiquibaseServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Connection jdbcConnection = DatabaseConfig.getConnection();
            DatabaseConnection databaseConnection = new JdbcConnection(jdbcConnection);

            Database database = new PostgresDatabase();
            database.setConnection(databaseConnection);

            Liquibase liquibase = new Liquibase(DatabaseConfig.getProperty("changeLogFile"), new ClassLoaderResourceAccessor(), database);
            liquibase.update("");

        } catch (SQLException | liquibase.exception.LiquibaseException e) {
            e.printStackTrace();
        }
    }
}
