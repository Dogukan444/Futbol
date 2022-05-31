package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public Connection connect() {
        Connection c = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fb", "postgres", "2035");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return c;
    }

}
