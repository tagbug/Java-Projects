package InternetExample;

import java.net.*;
import java.io.*;
import java.util.*;

public class URLExample {
    public static void main(String[] args) {
        Scanner scanner;
        URL url;
        Thread readURL;
        Look look = new Look();
        System.out.println("输入URL资源：");
        scanner = new Scanner(System.in);
        String source = scanner.nextLine();
        try {
            url = new URL(source);
            look.setURL(url);
            readURL = new Thread(look);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        readURL = new Thread(look);
        readURL.start();
        scanner.close();
    }
}

class Look implements Runnable {
    URL url;

    public void setURL(URL url) {
        this.url = url;
    }

    @Override
    public void run() {
        try {
            InputStream in = url.openStream();
            byte[] b = new byte[10240];
            int n = -1;
            while ((n = in.read(b)) != -1) {
                String str = new String(b, 0, n);
                System.out.println(str);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}