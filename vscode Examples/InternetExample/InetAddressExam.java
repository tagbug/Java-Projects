package InternetExample;

import java.net.*;

public class InetAddressExam {
    public static void main(String[] args) {
        try {
            InetAddress address1 = InetAddress.getByName("www.sina.com.cn");
            System.out.println(address1.toString());
            InetAddress address2 = InetAddress.getByName("localhost");
            System.out.println(address2.toString());
            InetAddress localAddress = InetAddress.getLocalHost();
            System.out.println(localAddress.toString());
        } catch (UnknownHostException e) {
            System.out.println(e.toString());
        }
    }
}
