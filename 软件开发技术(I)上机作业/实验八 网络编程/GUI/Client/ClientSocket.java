package GUI.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import GUI.QueryRequest;

/**
 * 客户端Socket通讯
 * 
 * @since 1.2
 * @author TagBug {@link https://github.com/tagbug}
 */
public class ClientSocket {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    /**
     * 默认构造方法
     */
    ClientSocket() {
        socket = null;
        inputStream = null;
        outputStream = null;
    }

    /**
     * 通过Socket与服务器建立连接，同时初始化Object流，用以传输对象数据
     * 
     * @param host 服务器主机
     * @param port 端口
     * @throws UnknownHostException
     * @throws IOException
     * @throws IllegalStateException 当尝试建立重复连接时抛出
     */
    public void connect(String host, int port) throws UnknownHostException, IOException, IllegalStateException {
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
     * 对服务器发送查询请求包，并等待服务器返回的{@code ArrayList<String>}结果
     * <p>
     * 注意：调用此方法会堵塞线程
     * 
     * @param queryRequest 封装的查询请求对象
     * @return 服务器返回的处理结果
     * @throws IllegalStateException 当{@code 未与服务器建立连接}或者{@code 处理返回结果时内部代码错误}时抛出
     * @throws IOException
     */
    @SuppressWarnings("unchecked") // 取消对泛型强制类型转换的警告
    public ArrayList<String> query(QueryRequest queryRequest) throws IllegalStateException, IOException {
        if (socket == null)
            throw new IllegalStateException("还未与服务器建立连接！");
        try {
            outputStream.writeObject(queryRequest);
            // 由于泛型类型擦除，编译器会报出警告，但是这里已经知道这个行为是安全的，直接忽略掉即可
            return (ArrayList<String>) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("内部代码错误！" + e);
        }
    }
}
