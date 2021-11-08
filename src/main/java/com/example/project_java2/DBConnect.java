package com.example.project_java2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static com.example.project_java2.Config.*;

public final class DBConnect {

    private static Connection conn = null;

    public static Connection getConnection() throws ClassNotFoundException{

        if (conn == null) {
            String driverName = "org.mariadb.jdbc.Driver";
            Class.forName(driverName);
            try {
                conn = DriverManager.getConnection(HOST_NAME, USER_NAME, PW);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

}
