package InternetExample.Thread;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Socket mySocket = null;
        DataInputStream in = null;
        DataOutputStream out = null;
        Thread readData;
        Read read = null;
        try {
            mySocket = new Socket();
            read = new Read();
            readData = new Thread(read);
            System.out.println("输入服务器IP：");
            String IP = scanner.nextLine();
            System.out.println("输入端口号：");
            int port = scanner.nextInt();
            if (mySocket.isConnected()) {
            } else {
                InetAddress address = InetAddress.getByName(IP);
                InetSocketAddress socketAddress = new InetSocketAddress(address, port);
                mySocket.connect(socketAddress);
                in = new DataInputStream(mySocket.getInputStream());
                out = new DataOutputStream(mySocket.getOutputStream());
                read.setIn(in);
                readData.start();
            }
        } catch (Exception e) {
            System.out.println("服务器已断开：" + e);
        }
        System.out.println("输入圆的半径（放弃请输入N）：");
        while (scanner.hasNext()) {
            double radius = 0;
            try {
                radius = scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println(e);
                System.exit(0);
            }
            try {
                out.writeDouble(radius);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        scanner.close();
    }
}

class Read implements Runnable {
    DataInputStream in;

    public void setIn(DataInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        double result = 0;
        while (true) {
            try {
                result = in.readDouble();
                System.out.println("圆的面积：" + result);
                System.out.println("输入圆的半径（放弃请输入N）：");
            } catch (IOException e) {
                System.out.println("与服务器已断开：" + e);
                break;
            }
        }
    }

}