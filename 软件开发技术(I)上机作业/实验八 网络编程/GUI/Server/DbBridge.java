package GUI.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 处理与数据库的通讯，此类是线程安全的
 * 
 * @since 10
 * @author TagBug {@link https://github.com/tagbug}
 */
public class DbBridge {
    private Connection conn;
    private static final String[] VALUES = { "学号", "姓名", "出生日期", "Java成绩" };
    private static final String idQuery = "SELECT * FROM students WHERE id = ?";
    private static final String nameQuery = "SELECT * FROM students WHERE name = ?";

    /**
     * 默认构造方法，建立与数据库的连接
     * 
     * @throws ClassNotFoundException 加载数据库驱动失败
     * @throws SQLException           建立连接失败
     */
    DbBridge() throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        conn = DriverManager.getConnection("jdbc:derby:test");
    }

    /**
     * <strong>内部方法</strong>
     * <p>
     * 将ResultSet处理为{@code ArrayList<string>}，使用VALUES常量
     * 
     * @param rs 数据库操作返回的结果集
     * @return 处理后的字符串数组
     */
    private synchronized ArrayList<String> returnResult(ResultSet rs) throws SQLException {
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
    }

    /**
     * 通过学号查询
     * 
     * @param id 学号
     * @return 查询结果
     */
    public synchronized ArrayList<String> queryById(String id) throws SQLException {
        var preStat = conn.prepareStatement(idQuery);
        preStat.setString(1, id);
        try (var rs = preStat.executeQuery()) {
            return returnResult(rs);
        }
    }

    /**
     * 通过姓名查询
     * 
     * @param name 姓名
     * @return 查询结果
     */
    public synchronized ArrayList<String> queryByName(String name) throws SQLException {
        var preStat = conn.prepareStatement(nameQuery);
        preStat.setString(1, name);
        try (var rs = preStat.executeQuery()) {
            return returnResult(rs);
        }
    }
}
