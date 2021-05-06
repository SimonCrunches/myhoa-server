package org.technopolis.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {

    @Bean
    @Profile("local")
    public DataSource localDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:5432/postgres")
                .username("postgres")
                .password("140297")
                .build();
    }

    @Bean
    @Profile("heroku")
    public DataSource herokuDataSource() throws URISyntaxException, SQLException {
        final URI dbUri = new URI(System.getenv("DATABASE_URL"));

        final String username = dbUri.getUserInfo().split(":")[0];
        final String password = dbUri.getUserInfo().split(":")[1];
        final String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':'
                + dbUri.getPort() + dbUri.getPath()
                + "?sslmode=require";

        return new SimpleDriverDataSource(DriverManager.getDriver(dbUrl), dbUrl, username, password);
    }

}
