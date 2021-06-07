package GUI.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DbBridge {
    private Connection conn;
    private static final String[] VALUES = { "学号", "姓名", "出生日期", "Java成绩" };
    private static final String idQuery = "SELECT * FROM students WHERE id = ?";
    private static final String nameQuery = "SELECT * FROM students WHERE name = ?";

    DbBridge() throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        conn = DriverManager.getConnection("jdbc:derby:test");
    }

    private synchronized ArrayList<String> returnResult(ResultSet rs) {
        try {
            var result = new ArrayList<String>();
            while (rs.next()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 4; i++) {
                    sb.append(VALUES[i]);
                    sb.append('：');
                    sb.append(rs.getString(i + 1));
                    sb.append('\t');
                }
                result.add(sb.toString());
            }
            return result;
        } catch (SQLException e) {
        }
        return new ArrayList<>();
    }

    public synchronized ArrayList<String> queryById(String id) {
        try {
            var preStat = conn.prepareStatement(idQuery);
            preStat.setString(1, id);
            try (var rs = preStat.executeQuery()) {
                return returnResult(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public synchronized ArrayList<String> queryByName(String name) {
        try {
            var preStat = conn.prepareStatement(nameQuery);
            preStat.setString(1, name);
            try (var rs = preStat.executeQuery()) {
                return returnResult(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
