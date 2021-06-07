package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbBridge {
    private Connection conn;
    private static final String registerQuery = "INSERT INTO users VALUES(?,?,?)";
    private static final String passwordQuery = "SELECT password FROM users WHERE id=?";

    DbBridge() throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        conn = DriverManager.getConnection("jdbc:derby:test");
    }

    public boolean register(String id, String password, String birthDate) {
        try {
            var preStat = conn.prepareStatement(registerQuery);
            preStat.setString(1, id);
            preStat.setString(2, password);
            preStat.setString(3, birthDate);
            if (preStat.executeUpdate() != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean login(String id, String password) {
        try {
            var preStat = conn.prepareStatement(passwordQuery);
            preStat.setString(1, id);
            try (ResultSet rs = preStat.executeQuery()) {
                if (rs.next()) {
                    if (rs.getString(1).equals(password)) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
