package GUI.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import GUI.QueryRequest;

public class ClientSocket {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    ClientSocket() {
        socket = null;
        inputStream = null;
        outputStream = null;
    }

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

    @SuppressWarnings("unchecked")
    public ArrayList<String> query(QueryRequest queryRequest) throws IllegalStateException, IOException {
        if (socket == null)
            throw new IllegalStateException("还未与服务器建立连接！");
        try {
            outputStream.writeObject(queryRequest);
            return (ArrayList<String>) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("内部代码错误！" + e);
        }
    }
}
