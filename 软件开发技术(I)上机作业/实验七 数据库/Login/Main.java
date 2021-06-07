package Login;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            var win = new MainFrame();
            System.out.println("学号：3200608080，姓名：陈欣阳");
        } catch (ClassNotFoundException e) {
            System.out.println("驱动加载失败！" + e);
        } catch (SQLException e) {
            System.out.println("数据库连接失败！" + e);
        }
    }
}
