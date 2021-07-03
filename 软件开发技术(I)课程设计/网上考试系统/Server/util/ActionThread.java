package Server.util;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import Data.ClientRequest;
import Data.ServerResponse;
import Server.excepitons.DbException;

/**
 * 会话线程，每个线程对应一次会话请求
 * 
 * @since 10
 */
public class ActionThread implements Runnable {
    private Socket socket;
    private DbBridge dbBridge;
    private boolean isLogined = false;
    private boolean isAdmin = false;
    private int userId;

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
                var data = request.getData();// 请求数据
                var response = new ServerResponse();
                try {
                    switch (request.getRequestType()) {// 针对不同请求类型
                        case Register:
                            register(data, response);
                            break;
                        case Login:
                            login(data, response);
                            break;
                        case SetScore:
                            setScore(data, response);
                            break;
                        case SetQuestion:
                            setQuestion(data, response);
                            break;
                        case GetScore:
                            getScore(data, response);
                            break;
                        case GetQuestion:
                            getQuestion(data, response);
                            break;
                        case GetRandomQuestions:
                            getRandomQuestions(data, response);
                            break;
                        case DeleteQuestion:
                            deleteQuestion(data, response);
                            break;
                        case GetQuestionList:
                            getQuestionList(response);
                            break;
                        case GetAllQuestions:
                            getAllQuestions(data, response);
                            break;
                        case UpdateQuestion:
                            updateQuestion(data, response);
                            break;
                        case AddQuestionList:
                            addQuestionList(data, response);
                            break;
                        case DeleteQuestionList:
                            deleteQuestionList(data, response);
                            break;
                        case RefreshTable:
                            refreshTable(data, response);
                            break;
                        default:
                            response.setFailReason("错误请求！");
                            break;
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
    private void register(Map<String, String> data, ServerResponse response) throws DbException, SQLException {
        if (dbBridge.register(data.get("name"), data.get("password"), Boolean.parseBoolean(data.get("isAdmin")))) {
            response.setSucceed(true);
        } else {
            response.setFailReason("数据库未更新！");
        }
    }

    private void login(Map<String, String> data, ServerResponse response) throws DbException, SQLException {
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

    private void setScore(Map<String, String> data, ServerResponse response) throws DbException, SQLException {
        if (!isLogined) {
            response.setFailReason("请先登录！");
            return;
        }
        if (dbBridge.setScore(userId, data.get("time"), Double.parseDouble(data.get("score")))) {
            response.setSucceed(true);
        } else {
            response.setFailReason("数据库未更新！");
        }
    }

    private void setQuestion(Map<String, String> data, ServerResponse response) throws DbException, SQLException {
        if (!isLogined) {
            response.setFailReason("请先登录！");
            return;
        }
        if (!isAdmin) {
            response.setFailReason("权限不足！");
            return;
        }
        if (dbBridge.setQuestion(Integer.parseInt(data.get("questionTableId")))) {
            response.setSucceed(true);
        } else {
            response.setFailReason("数据库未更新！");
        }
    }

    private void getScore(Map<String, String> data, ServerResponse response) throws DbException, SQLException {
        if (!isLogined) {
            response.setFailReason("请先登录！");
            return;
        }
        int queryUserId = Integer.parseInt(data.get("userId"));
        if (userId != queryUserId && !isAdmin) {
            response.setFailReason("权限不足！");
            return;
        }
        response.setResult(dbBridge.getScore(userId));
        response.setSucceed(true);
    }

    private void getQuestion(Map<String, String> data, ServerResponse response) throws DbException, SQLException {
        if (!isLogined) {
            response.setFailReason("请先登录！");
            return;
        }
        var result = new ArrayList<Map<String, String>>();
        result.add(
                dbBridge.getQuestion(Integer.parseInt(data.get("id")), Integer.parseInt(data.get("questionTableId"))));
        response.setResult(result);
        response.setSucceed(true);
    }

    private void getRandomQuestions(Map<String, String> data, ServerResponse response)
            throws DbException, SQLException {
        if (!isLogined) {
            response.setFailReason("请先登录！");
            return;
        }
        response.setResult(dbBridge.getRandomQuestions(Integer.parseInt(data.get("questionTableId"))));
        response.setSucceed(true);
    }

    private void getAllQuestions(Map<String, String> data, ServerResponse response) throws DbException, SQLException {
        if (!isLogined) {
            response.setFailReason("请先登录！");
            return;
        }
        response.setResult(dbBridge.getAllQuestions(Integer.parseInt(data.get("questionTableId"))));
        response.setSucceed(true);
    }

    private void deleteQuestion(Map<String, String> data, ServerResponse response) throws DbException, SQLException {
        if (!isLogined) {
            response.setFailReason("请先登录！");
            return;
        }
        if (!isAdmin) {
            response.setFailReason("权限不足！");
            return;
        }
        if (dbBridge.deleteQuestion(Integer.parseInt(data.get("id")), Integer.parseInt(data.get("questionTableId")))) {
            response.setSucceed(true);
        } else {
            response.setFailReason("数据库未更新！");
        }
    }

    private void getQuestionList(ServerResponse response) throws SQLException {
        if (!isLogined) {
            response.setFailReason("请先登录！");
            return;
        }
        response.setResult(dbBridge.getQuestionList());
        response.setSucceed(true);
    }

    private void addQuestionList(Map<String, String> data, ServerResponse response) throws DbException, SQLException {
        if (!isLogined) {
            response.setFailReason("请先登录！");
            return;
        }
        if (dbBridge.addQuestionList(data.get("questionTableName"))) {
            response.setSucceed(true);
        } else {
            response.setFailReason("数据库未更新！");
        }
    }

    private void deleteQuestionList(Map<String, String> data, ServerResponse response)
            throws NumberFormatException, SQLException {
        if (!isLogined) {
            response.setFailReason("请先登录！");
            return;
        }
        if (dbBridge.deleteQuestionList(Integer.parseInt(data.get("questionTableId")))) {
            response.setSucceed(true);
        } else {
            response.setFailReason("数据库未更新！");
        }
    }

    private void updateQuestion(Map<String, String> data, ServerResponse response) throws DbException, SQLException {
        if (!isLogined) {
            response.setFailReason("请先登录！");
            return;
        }
        if (!isAdmin) {
            response.setFailReason("权限不足！");
            return;
        }
        if (dbBridge.updateQuestion(data)) {
            response.setSucceed(true);
        } else {
            response.setFailReason("数据库未更新！");
        }
    }

    private void refreshTable(Map<String, String> data, ServerResponse response) throws DbException, SQLException {
        if (!isLogined) {
            response.setFailReason("请先登录！");
            return;
        }
        if (!isAdmin) {
            response.setFailReason("权限不足！");
            return;
        }
        dbBridge.refreshID("questions_" + data.get("questionTableId"));
        response.setSucceed(true);
    }
}
