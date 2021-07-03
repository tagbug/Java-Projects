package Server.excepitons;

/**
 * 用户操作类数据库异常
 */
public class DbException extends Exception {
    public DbException(String message) {
        super(message);
    }
}
