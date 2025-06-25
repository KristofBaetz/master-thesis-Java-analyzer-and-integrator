package org.baetz.christoph.database;


import org.mariadb.jdbc.Connection;
import org.mariadb.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class NewsArticleDatabase {

    public static ResultSet readQuery(String query) {

        Properties connConfig = new Properties();
        connConfig.setProperty("user", "username placeholder");
        connConfig.setProperty("password", "password placeholder");

        try (Connection conn = (Connection) DriverManager.getConnection("jdbc:mariadb://<host>/<database>", connConfig)) {

            PreparedStatement prep = conn.prepareStatement(
                    query,
                    Statement.RETURN_GENERATED_KEYS);

            if (!prep.execute()) {
                return null;
            }

            return prep.getResultSet();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet readQuery(String query, List<String> list) {

        Properties connConfig = new Properties();
        connConfig.setProperty("user", "username placeholder");
        connConfig.setProperty("password", "username placeholder");

        try (Connection conn = (Connection) DriverManager.getConnection("jdbc:mariadb://<host>/<database>", connConfig)) {

            PreparedStatement prep = conn.prepareStatement(
                    query,
                    Statement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < list.size(); i++) {
                prep.setString(i + 1, list.get(i));
            }

            if (!prep.execute()) {
                return null;
            }

            return prep.getResultSet();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet insertQuery(String query, List<String> values){

        Properties connConfig = new Properties();
        connConfig.setProperty("user", "username placeholder");
        connConfig.setProperty("password", "username placeholder");

        try (Connection conn = (Connection) DriverManager.getConnection("jdbc:mariadb://<host>/<database>", connConfig)) {

            PreparedStatement prep = conn.prepareStatement(
                    query,
                    Statement.RETURN_GENERATED_KEYS);

            if (!prep.execute()) {
                return null;
            }

            return prep.getResultSet();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
