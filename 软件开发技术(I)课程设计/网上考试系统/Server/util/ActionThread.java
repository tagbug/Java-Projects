package Server.util;

import Data.*;
import Server.excepitons.*;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

/**
 * 会话线程，每个线程对应一次会话请求
 *
 * @since 10
 */
public class ActionThread implements Runnable {
    private final Socket socket;
    private final DbBridge dbBridge;
    private boolean isLogined = false;
    private boolean isAdmin = false;
    private int userId;
    private ServerResponse response;
    private Map<String,String> data;

    /**
     * 函数式接口，方法体须返回boolean的执行结果
     */
    @FunctionalInterface
    private interface BooleanReturned {
        boolean get() throws DbException, SQLException;
    }

    /**
     * 默认构造方法
     *
     * @param socket   独立socket连接
     * @param dbBridge 数据库桥
     */
    public ActionThread(Socket socket, DbBridge dbBridge) {
        this.socket = socket;
        this.dbBridge = dbBridge;
        System.out.println("IP:" + socket.getInetAddress() + ",客户" + hashCode() + "已建立连接...");
    }

    @Override
    public void run() {
        // 注意：通过Socket建立Object流时，服务端和客户端的输入输出流顺序应相反，否则会导致死锁，详见Object流的JavaDoc
        try (var inputStream = new ObjectInputStream(socket.getInputStream());
             var outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            while (true) {
                var request = (ClientRequest) inputStream.readObject();// 读取客户端请求
                data = request.getData();// 请求数据
                response = new ServerResponse();
                try {
                    // 针对不同请求类型
                    switch (request.getRequestType()) {
                        case Register -> register();
                        case Login -> login();
                        case SetScore -> setScore();
                        case SetQuestion -> setQuestion();
                        case GetScore -> getScore();
                        case GetQuestion -> getQuestion();
                        case GetRandomQuestions -> getRandomQuestions();
                        case DeleteQuestion -> deleteQuestion();
                        case GetQuestionList -> getQuestionList();
                        case GetAllQuestions -> getAllQuestions();
                        case UpdateQuestion -> updateQuestion();
                        case AddQuestionList -> addQuestionList();
                        case DeleteQuestionList -> deleteQuestionList();
                        case RefreshTable -> refreshTable();
                        default -> response.setFailReason("错误请求！");
                    }
                } catch (DbException e) {
                    // 用户操作错误
                    response.setFailReason(e.getMessage());
                } catch (SQLException e) {
                    // 数据库内部错误
                    e.printStackTrace();
                    response.setFailReason("服务器内部错误！");
                } catch (Exception e) {
                    // 发生意料之外的错误
                    e.printStackTrace();
                    response.setFailReason("错误请求！");
                } finally {
                    outputStream.writeObject(response);// 回应客户端
                }
            }
        } catch (Exception e) {// 其他未知错误
            e.printStackTrace();
            System.out.println("由于：" + e + ",客户" + hashCode() + "已断开连接...");
        }
    }

    // 私有方法串，处理具体的客户端请求

    private void failWithNotUpdated(BooleanReturned method) throws DbException, SQLException {
        if (!method.get()) {
            throw new DbException("数据库未更新！");
        }
        response.setSucceed(true);
    }

    private void register() throws DbException, SQLException {
        failWithNotUpdated(() -> dbBridge.register(data.get("name"), data.get("password"), Boolean.parseBoolean(data.get("isAdmin"))));
    }

    private void login() throws DbException, SQLException {
        var userInfo = dbBridge.login(data.get("name"), data.get("password"));
        if (userInfo == null) {
            response.setFailReason("用户名或密码不正确！");
        } else {
            try {
                isLogined = true;
                userId = Integer.parseInt(userInfo.get("id"));
                isAdmin = Boolean.parseBoolean(userInfo.get("isAdmin"));
                var result = new ArrayList<Map<String, String>>();
                result.add(userInfo);
                response.setResult(result);
                response.setSucceed(true);
            } catch (Exception e) {
                // 格式转换出错
                e.printStackTrace();
                response.setFailReason("服务器内部错误！");
            }
        }
    }

    private void loginRequired() throws DbException {
        if (!isLogined) {
            throw new DbException("请先登录！");
        }
    }
    private void adminRequired() throws DbException {
        if (!isAdmin) {
            throw new DbException("权限不足！");
        }
    }

    private void setScore() throws DbException, SQLException {
        loginRequired();
        failWithNotUpdated(() -> dbBridge.setScore(userId, data.get("time"), Double.parseDouble(data.get("score"))));
    }

    private void setQuestion() throws DbException, SQLException {
        loginRequired();
        adminRequired();
        failWithNotUpdated(() -> dbBridge.setQuestion(Integer.parseInt(data.get("questionTableId"))));
    }

    private void getScore() throws SQLException, DbException {
        loginRequired();
        int queryUserId = Integer.parseInt(data.get("userId"));
        if (userId != queryUserId && !isAdmin) {
            response.setFailReason("权限不足！");
            return;
        }
        response.setResult(dbBridge.getScore(userId));
        response.setSucceed(true);
    }

    private void getQuestion() throws DbException, SQLException {
        loginRequired();
        var result = new ArrayList<Map<String, String>>();
        result.add(dbBridge.getQuestion(Integer.parseInt(data.get("id")), Integer.parseInt(data.get("questionTableId"))));
        response.setResult(result);
        response.setSucceed(true);
    }

    private void getRandomQuestions() throws DbException, SQLException {
        loginRequired();
        response.setResult(dbBridge.getRandomQuestions(Integer.parseInt(data.get("questionTableId"))));
        response.setSucceed(true);
    }

    private void getAllQuestions() throws DbException, SQLException {
        loginRequired();
        response.setResult(dbBridge.getAllQuestions(Integer.parseInt(data.get("questionTableId"))));
        response.setSucceed(true);
    }

    private void deleteQuestion() throws DbException, SQLException {
        loginRequired();
        adminRequired();
        failWithNotUpdated(() -> dbBridge.deleteQuestion(Integer.parseInt(data.get("id")), Integer.parseInt(data.get("questionTableId"))));
    }

    private void getQuestionList() throws SQLException, DbException {
        loginRequired();
        response.setResult(dbBridge.getQuestionList());
        response.setSucceed(true);
    }

    private void addQuestionList() throws DbException, SQLException {
        loginRequired();
        failWithNotUpdated(()->dbBridge.addQuestionList(data.get("questionTableName")));
    }

    private void deleteQuestionList() throws SQLException, DbException {
        loginRequired();
        failWithNotUpdated(()->dbBridge.deleteQuestionList(Integer.parseInt(data.get("questionTableId"))));
    }

    private void updateQuestion() throws DbException, SQLException {
        loginRequired();
        adminRequired();
        failWithNotUpdated(()->dbBridge.updateQuestion(data));
    }

    private void refreshTable() throws DbException, SQLException {
        loginRequired();
        adminRequired();
        dbBridge.refreshID("questions_" + data.get("questionTableId"));
        response.setSucceed(true);
    }
}
