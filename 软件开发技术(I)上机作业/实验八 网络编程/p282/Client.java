package p282;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (var clientSocket = new Socket("localhost", 6666)) {
            System.out.println("与服务器已建立连接...");
            try (var inputStream = new DataInputStream(clientSocket.getInputStream());
                    var outputStream = new DataOutputStream(clientSocket.getOutputStream());
                    var scanner = new Scanner(System.in)) {
                while (true) {
                    System.out.print("输入边长r：");
                    outputStream.writeDouble(scanner.nextDouble());
                    System.out.println("结算结果：" + inputStream.readDouble());
                }
            } catch (Exception e) {
                System.out.println("与服务器断开连接...");
            }
        } catch (Exception e) {
            System.out.println("与服务器建立连接失败！" + e);
        }
    }
}
