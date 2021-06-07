package JDBCExam;

import java.sql.*;

public class ConnectExam {
    public static void main(String[] args) {
        Connection con;
        Statement sql;
        ResultSet rs;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");// 加载内置Derby数据库驱动
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection("jdbc:derby:test");// 连接数据库
            sql = con.createStatement();
            rs = sql.executeQuery("SELECT * FROM cat");// 查询表
            while (rs.next()) {
                String number = rs.getString(1);// 得到记录（行）的列值
                String name = rs.getString(2);
                String price = rs.getString(3);
                System.out.print(number + '|');
                System.out.print(name + '|');
                System.out.println(price + '|');
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
