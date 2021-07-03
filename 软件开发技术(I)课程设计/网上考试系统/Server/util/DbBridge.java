package Server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import Server.excepitons.DbException;

/**
 * 数据库交互
 * 
 * @since 10
 */
public class DbBridge {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/TestSystem?useSSL=false";// 不使用SSL连接
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = null;

    private static final String REGISTER_QUERY = "INSERT INTO users VALUES(?,?,?,?)";
    private static final String LOGIN_QUERY = "SELECT * FROM users WHERE name=? AND password=?";
    private static final String USER_QUERY = "SELECT * FROM users WHERE id=?";
    private static final String SCORE_QUERY = "SELECT * FROM scores WHERE userId=?";
    private static final String RECODE_SCORE_QUERY = "INSERT INTO scores VALUES(?,?,?,?,?)";
    private static final String QUESTION_LIST_QUERY = "SELECT * FROM questions";
    private static final String ADD_QUESTION_LIST_QUERY1 = "INSERT INTO questions VALUES(?,?)";
    private static final String ADD_QUESTION_LIST_QUERY2 = "CREATE TABLE questions_%s(id int primary key,text text,imgSrc text,chooseA text,chooseB text,chooseC text,chooseD text,answer char(1))";
    private static final String DELETE_QUESTION_LIST_QUERY1 = "DROP TABLE questions_%s";
    private static final String DELETE_QUESTION_LIST_QUERY2 = "DELETE FROM questions WHERE id=?";
    private static final String[] REFRESH_LIST_QUERY = new String[] { "SET @i=0", "UPDATE %s SET `id`=(@i:=@i+1)",
            "ALTER TABLE %s AUTO_INCREMENT=0" };
    private static final String QUESTION_QUERY = "SELECT * FROM questions_%s WHERE id=?";
    private static final String RECODE_QUESTION_QUERY = "INSERT INTO questions_%s VALUES(?,?,?,?,?,?,?,?)";
    private static final String DELETE_QUESTION_QUERY = "DELETE FROM questions_%s WHERE id=?";
    private static final String UPDATE_QUESTION_QUERY = "UPDATE questions_%s SET text=?,imgSrc=?,chooseA=?,chooseB=?,chooseC=?,chooseD=?,answer=? WHERE id=?";
    private static final String MAX_ID_QUERY = "SELECT MAX(id) FROM ";

    private Connection conn;

    /**
     * 初始化，加载MySQL驱动并连接到目标数据库
     * 
     * @throws ClassNotFoundException 加载MySQL驱动失败
     * @throws SQLException           与目标数据库建立连接失败
     */
    public DbBridge() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);// 加载MySQL驱动--JDBC4.0前需要
        conn = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWORD);// 连接目标数据库
    }

    /**
     * 用户注册操作
     * 
     * @param name     用户名--不能重复
     * @param password 密码
     * @param isAdmin  是否是管理员
     * @return 是否成功
     * @throws DbException  用户名重复/字段为空
     * @throws SQLException 数据库异常
     */
    public boolean register(String name, String password, boolean isAdmin) throws DbException, SQLException {
        if (name == null || name.isEmpty())
            throw new DbException("用户名不能为空！");
        if (password == null || password.isEmpty())
            throw new DbException("密码不能为空！");
        int id = name.hashCode();
        var stat = conn.prepareStatement(REGISTER_QUERY);
        stat.setInt(1, id);
        stat.setString(2, name);
        stat.setBoolean(3, isAdmin);
        stat.setString(4, password);
        try {
            return stat.executeUpdate() == 1;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {// 主键重复
                throw new DbException("用户名重复！");
            }
            throw e;
        }
    }

    /**
     * 用户登录操作
     * 
     * @param name     用户名
     * @param password 密码
     * @return 用户信息（id+userName+isAdmin）
     * @throws DbException  字段为空
     * @throws SQLException 数据库异常
     */
    public Map<String, String> login(String name, String password) throws DbException, SQLException {
        if (name == null || name.isEmpty())
            throw new DbException("用户名不能为空！");
        if (password == null || password.isEmpty())
            throw new DbException("密码不能为空！");
        var stat = conn.prepareStatement(LOGIN_QUERY);
        stat.setString(1, name);
        stat.setString(2, password);
        var rs = stat.executeQuery();
        var result = new HashMap<String, String>();
        if (rs.next()) {
            result.put("id", rs.getString("id"));
            result.put("userName", rs.getString("name"));
            result.put("isAdmin", rs.getBoolean("isAdmin") ? "True" : "False");
            return result;
        }
        return null;
    }

    /**
     * <strong>私有方法</strong>
     * <p>
     * 获取userId对应的用户信息
     * 
     * @param userId 用户编号
     * @return 用户信息结果集
     * @throws DbException  用户不存在
     * @throws SQLException 数据库异常
     */
    private ResultSet getUserInfo(int userId) throws DbException, SQLException {
        if (userId <= 0)
            throw new DbException("用户不存在！");
        var stat = conn.prepareStatement(USER_QUERY);
        stat.setInt(1, userId);
        return stat.executeQuery();
    }

    /**
     * <strong>私有方法</strong>
     * <p>
     * 获取指定表中ID的最大值
     * 
     * @param tableName 表名
     * @return MAX(ID)
     * @throws DbException  字段为空
     * @throws SQLException 数据库异常
     */
    private int getMaxID(String tableName) throws DbException, SQLException {
        if (tableName == null || tableName.isEmpty())
            throw new DbException("表名不能为空！");
        var stat = conn.createStatement();
        var rs = stat.executeQuery(MAX_ID_QUERY + tableName);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    /**
     * 刷新主键自增序列
     * 
     * @param tableName
     * @throws DbException
     * @throws SQLException
     */
    public void refreshID(String tableName) throws DbException, SQLException {
        if (tableName == null || tableName.isEmpty())
            throw new DbException("表名不能为空！");
        try {
            conn.setAutoCommit(false);
            conn.createStatement().execute(REFRESH_LIST_QUERY[0]);
            conn.createStatement().execute(REFRESH_LIST_QUERY[1].replaceFirst("[%][s]", tableName));
            conn.createStatement().execute(REFRESH_LIST_QUERY[2].replaceFirst("[%][s]", tableName));
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        }
    }

    /**
     * 查询考试分数操作
     * 
     * @param userId 用户编号
     * @return 考试分数信息数组
     * @throws SQLException 数据库异常
     */
    public ArrayList<Map<String, String>> getScore(int userId) throws SQLException {
        if (userId <= 0)
            return null;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        var stat = conn.prepareStatement(SCORE_QUERY);
        stat.setInt(1, userId);
        var rs = stat.executeQuery();
        var result = new ArrayList<Map<String, String>>();
        while (rs.next()) {
            var map = new HashMap<String, String>();
            map.put("id", rs.getString("userId"));
            map.put("userName", rs.getString("userName"));
            map.put("time", ft.format(rs.getTimestamp("time")));
            map.put("score", rs.getString("score"));
            result.add(map);
        }
        return result;
    }

    /**
     * 录入成绩操作
     * 
     * @param userId 用户编号
     * @param time   考试时间
     * @param score  考试分数
     * @return 是否成功
     * @throws DbException  用户不存在
     * @throws SQLException 数据库异常
     */
    public boolean setScore(int userId, String time, double score) throws DbException, SQLException {
        if (userId <= 0)
            throw new DbException("用户不存在！");
        var userInfo = getUserInfo(userId);
        if (!userInfo.next())
            throw new DbException("用户不存在！");
        var stat = conn.prepareStatement(RECODE_SCORE_QUERY);
        stat.setInt(1, getMaxID("scores") + 1);
        stat.setInt(2, userId);
        stat.setString(3, userInfo.getString("name"));
        stat.setString(4, time);
        stat.setDouble(5, score);
        return stat.executeUpdate() == 1;
    }

    /**
     * 获取题目库列表
     * 
     * @return 题目库信息
     * @throws SQLException 数据库异常
     */
    public ArrayList<Map<String, String>> getQuestionList() throws SQLException {
        var stat = conn.prepareStatement(QUESTION_LIST_QUERY);
        var rs = stat.executeQuery();
        var result = new ArrayList<Map<String, String>>();
        while (rs.next()) {
            var map = new HashMap<String, String>();
            map.put("id", rs.getString("id"));
            map.put("questionTableName", rs.getString("questionTableName"));
            result.add(map);
        }
        return result;
    }

    /**
     * 增加题目库
     * 
     * @param questionTableName 题目库名
     * @return
     * @throws SQLException
     */
    public boolean addQuestionList(String questionTableName) throws DbException, SQLException {
        int max = getMaxID("questions");
        var stat = conn.prepareStatement(ADD_QUESTION_LIST_QUERY1);
        stat.setInt(1, max + 1);
        stat.setString(2, questionTableName);
        if (stat.executeUpdate() == 1) {
            stat = conn.prepareStatement(ADD_QUESTION_LIST_QUERY2.replaceFirst("[%][s]", String.valueOf(max + 1)));
            stat.execute();
            return true;
        }
        return false;
    }

    /**
     * 删除题目库
     * 
     * @param questionTableId
     * @return
     * @throws SQLException
     */
    public boolean deleteQuestionList(int questionTableId) throws SQLException {
        var stat = conn.prepareStatement(DELETE_QUESTION_LIST_QUERY1.replaceFirst("[%][s]", "" + questionTableId));
        var stat2 = conn.prepareStatement(DELETE_QUESTION_LIST_QUERY2);
        stat2.setInt(1, questionTableId);
        stat.execute();
        return stat2.executeUpdate() == 1;
    }

    /**
     * 获取指定编号的题目
     * 
     * @param id 题目编号
     * @return 题目信息
     * @throws DbException  题目不存在
     * @throws SQLException 数据库异常
     */
    public Map<String, String> getQuestion(int id, int questionTableId) throws DbException, SQLException {
        if (id <= 0)
            throw new DbException("题目不存在！");
        var stat = conn.prepareStatement(QUESTION_QUERY.replaceFirst("[%][s]", "" + questionTableId));
        stat.setInt(1, id);
        var rs = stat.executeQuery();
        if (!rs.next())
            throw new DbException("题目不存在！");
        var result = new HashMap<String, String>();
        result.put("id", rs.getString("id"));
        result.put("text", rs.getString("text"));
        result.put("imgSrc", rs.getString("imgSrc"));
        result.put("chooseA", rs.getString("chooseA"));
        result.put("chooseB", rs.getString("chooseB"));
        result.put("chooseC", rs.getString("chooseC"));
        result.put("chooseD", rs.getString("chooseD"));
        result.put("answer", rs.getString("answer"));
        return result;
    }

    /**
     * 根据题库题目总数随机获取n个题目 max<=10时，获取max道题目 10<max<=20时，获取10~max道题目
     * max>20时，获取2/max~MAX_COUNT道题目，但不超过MAX_COUNT道题
     * 
     * @return 题目信息数组
     * @throws DbException  内部异常--逻辑错误/未知错误
     * @throws SQLException 数据库异常
     */
    public ArrayList<Map<String, String>> getRandomQuestions(int questionTableId) throws DbException, SQLException {
        int MAX_COUNT = 30;
        try {
            int max = getMaxID("questions_" + questionTableId);
            int n = 0;
            var tmpRandom = new Random(System.currentTimeMillis() - (long) (Math.PI * 1000));
            if (max <= 10) {
                n = max;
            } else if (max <= 20) {
                n = tmpRandom.nextInt(max - 9) + 10;
            } else {
                if (max / 2 > MAX_COUNT) {
                    n = MAX_COUNT;
                } else {
                    n = tmpRandom.nextInt(MAX_COUNT - max / 2 + 1) + max / 2;
                }
            }
            var randomSet = new HashSet<Integer>();
            var result = new ArrayList<Map<String, String>>();
            // 如果 n <= max/2，就生成 n 个随机题目ID
            // 如果 n > max/2，就生成 max-n 个随机数，生成除这些随机数外的题目ID
            if (n <= max / 2) {
                Random ran = new Random(System.currentTimeMillis());
                for (int i = 0; i < n; i++) {
                    int num = ran.nextInt(max) + 1;
                    while (!randomSet.add(num % max + 1)) {
                        num++;
                    }
                }
                for (var num : randomSet) {
                    result.add(getQuestion(num, questionTableId));
                }
            } else {
                Random ran = new Random(System.currentTimeMillis());
                for (int i = 0; i < max - n; i++) {
                    int num = ran.nextInt(max) + 1;
                    while (!randomSet.add(num % max + 1)) {
                        num++;
                    }
                }
                for (int i = 0; i < max; i++) {
                    if (!randomSet.contains(i + 1))
                        result.add(getQuestion(i + 1, questionTableId));
                }
            }
            return result;
        } catch (DbException e) {
            e.printStackTrace();
            throw new DbException("服务器内部异常！");
        }
    }

    public ArrayList<Map<String, String>> getAllQuestions(int questionTableId) throws SQLException, DbException {
        try {
            int max = getMaxID("questions_" + questionTableId);
            var result = new ArrayList<Map<String, String>>();
            for (int i = 0; i < max; i++) {
                result.add(getQuestion(i + 1, questionTableId));
            }
            return result;
        } catch (DbException e) {
            e.printStackTrace();
            throw new DbException("服务器内部异常！");
        }
    }

    /**
     * 录入题目操作
     * 
     * @param oriData 题目数据
     * @return 是否成功
     * @throws DbException  题目字段数量不匹配
     * @throws SQLException 数据库异常
     */
    public boolean setQuestion(int questionTableId) throws DbException, SQLException {
        var stat = conn.prepareStatement(RECODE_QUESTION_QUERY.replaceFirst("[%][s]", String.valueOf(questionTableId)));
        stat.setInt(1, getMaxID("questions_" + questionTableId) + 1);
        for (int i = 0; i < 7; i++) {
            stat.setString(i + 2, "");
        }
        return stat.executeUpdate() == 1;
    }

    /**
     * 删除题目操作
     * 
     * @param id 题目ID
     * @return 是否成功
     * @throws DbException  题目不存在
     * @throws SQLException 数据库异常
     */
    public boolean deleteQuestion(int id, int questionTableId) throws DbException, SQLException {
        if (id <= 0)
            throw new DbException("题目不存在！");
        var stat = conn.prepareStatement(DELETE_QUESTION_QUERY.replaceFirst("[%][s]", "" + questionTableId));
        stat.setInt(1, id);
        return stat.executeUpdate() == 1;
    }

    /**
     * 更新题目操作
     * 
     * @param oriData 题目数据
     * @return 是否成功
     * @throws DbException  题目信息异常
     * @throws SQLException 数据库异常
     */
    public boolean updateQuestion(Map<String, String> oriData) throws DbException, SQLException {
        String[] data;
        try {
            data = new String[] { oriData.get("text"), oriData.get("imgSrc"), oriData.get("chooseA"),
                    oriData.get("chooseB"), oriData.get("chooseC"), oriData.get("chooseD"), oriData.get("answer"),
                    oriData.get("id"), oriData.get("questionTableId") };
        } catch (Exception e) {
            throw new DbException("录入的题目信息异常！");
        }
        var stat = conn.prepareStatement(UPDATE_QUESTION_QUERY.replaceFirst("[%][s]", data[data.length - 1]));
        for (int i = 0; i < data.length - 1; i++) {
            stat.setString(i + 1, data[i]);
        }
        return stat.executeUpdate() == 1;
    }

    /**
     * 单元测试
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            var dbBridge = new DbBridge();
            try {
                try {
                    if (dbBridge.register("李四", "123456", false)) {
                        System.out.println("注册成功！");
                    } else {
                        System.out.println("注册失败！");
                    }
                } catch (DbException e) {
                    System.out.println(e.getMessage());
                }
                var userInfo = dbBridge.login("李四", "123456");
                if (userInfo == null) {
                    System.out.println("登录失败，用户名或密码错误！");
                } else {
                    System.out.println("登录成功！用户ID：" + userInfo.get("id") + " 用户姓名：" + userInfo.get("userName")
                            + " 用户权限：" + (userInfo.get("isAdmin").equals("0") ? "考生" : "管理员"));
                }
                // System.out.println(dbBridge.getMaxID("scores"));
                // dbBridge.setScore(774889, "2021-07-01 11:10:23", 90.5);
                // // dbBridge.addQuestionList("Java训练");
                // dbBridge.setQuestion(new String[] { "下列哪个叙述是正确的？", "", "final类可以有子类",
                // "abstract类中只可以有abstract方法",
                // "abstract类中可以有非abstract方法，但该方法不可以用final修饰", "不知道", "C", "1" });
                // // dbBridge.addQuestionList("交通法训练");
                // dbBridge.setQuestion(new String[] { "遇到这种情况的路口，以下做法正确的是什么？", "", "沿左侧车道掉头",
                // "该路口不能掉头",
                // "选择中间车道掉头", "在路口内掉头", "B", "2" });
                // dbBridge.setQuestion(
                // new String[] { "这个路口允许车辆怎样行驶？",
                // "https://sucimg.itc.cn/sblog/j68555cf0bbb4e98082241f252860d42f",
                // "向左转弯", "直行", "直行或向右转弯", "向右转弯", "B", "2" });
                dbBridge.refreshID("scores");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getErrorCode());
            } catch (DbException e) {
                System.out.println(e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("加载数据库驱动失败");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("与目标数据库建立连接失败");
            e.printStackTrace();
        }
    }
}
