package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 处理与数据库的通讯
 * 
 * @since 10
 * @author TagBug
 */
public class DbBridge {
    private Connection conn;
    private static final String registerQuery = "INSERT INTO users VALUES(?,?,?)";// 预处理
    private static final String passwordQuery = "SELECT password FROM users WHERE id=?";// 预处理

    /**
     * 默认构造方法，建立与数据库的连接
     * 
     * @throws ClassNotFoundException 加载数据库驱动失败
     * @throws SQLException           建立连接失败
     */
    DbBridge() throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");// 加载Derby数据库驱动
        conn = DriverManager.getConnection("jdbc:derby:test;create=true");// 连接到test数据库，如果不存在则新建一个
    }

    /**
     * 注册，将信息存入数据库
     * 
     * @param id        用户ID
     * @param password  用户密码
     * @param birthDate 出生日期
     * @return 是否成功
     */
    public boolean register(String id, String password, String birthDate) throws SQLException {
        var preStat = conn.prepareStatement(registerQuery);// 预处理SQL语句
        preStat.setString(1, id);
        preStat.setString(2, password);
        preStat.setString(3, birthDate);
        if (preStat.executeUpdate() != 0) {
            return true;
        }
        return false;
    }

    /**
     * 登录，读取与ID对应的密码，检测给出的密码与数据库中的密码是否一致 (在实际运用中一般是将密码做Hash处理，将Hash值作比较，而非直接明文)
     * 
     * @param id       用户ID
     * @param password 用户密码
     * @return 是否成功
     */
    public boolean login(String id, String password) throws SQLException {
        var preStat = conn.prepareStatement(passwordQuery);// 预处理SQL语句
        preStat.setString(1, id);
        try (ResultSet rs = preStat.executeQuery()) {
            if (rs.next()) {
                if (rs.getString(1).equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }
}
