package Client.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import Data.ClientRequest;
import Data.ServerResponse;

/**
 * 客户端网络接口
 */
public class ClientSocket {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private String host;
    private int port;

    /**
     * 默认构造方法
     */
    public ClientSocket() {
        socket = null;
        inputStream = null;
        outputStream = null;
    }

    /**
     * 配置服务器域名和端口
     * 
     * @param host
     * @param port
     */
    public void config(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 通过Socket与服务器建立连接，同时初始化Object流，用以传输对象数据
     * 
     * @throws UnknownHostException  域名解析失败
     * @throws IOException           连接失败
     * @throws IllegalStateException 当尝试建立重复连接时抛出
     */
    public void connect() throws UnknownHostException, IOException, IllegalStateException {
        if (socket == null || socket.isClosed()) {
            socket = new Socket(host, port);
            try {
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                socket.close();
                throw e;
            }
        } else {
            throw new IllegalStateException("不能重复建立连接！");
        }
    }

    /**
     * 强制与服务器建立连接，当通道意外坍塌无法检测时使用
     * 
     * @throws UnknownHostException 域名解析失败
     * @throws IOException          连接失败
     */
    public void enforceConnect() throws UnknownHostException, IOException {
        socket = new Socket(host, port);
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            socket.close();
            throw e;
        }
    }

    /**
     * 对服务器发送查询请求包，并等待服务器返回的{@code ArrayList<String>}结果
     * <p>
     * 注意：调用此方法会堵塞线程
     * 
     * @param queryRequest 封装的查询请求对象
     * @return 服务器返回的处理结果
     * @throws IllegalStateException 当{@code 未与服务器建立连接}或者{@code 处理返回结果时内部代码错误}时抛出
     * @throws IOException           连接异常
     */
    public synchronized ServerResponse query(ClientRequest queryRequest) throws IllegalStateException, IOException {
        if (socket == null)
            throw new IllegalStateException("还未与服务器建立连接！");
        try {
            outputStream.writeObject(queryRequest);
            return (ServerResponse) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("内部代码错误！" + e);
        }
    }
}
