package Base64Decoder;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

public class Main {
    public static void main(String[] args) {
        File file = new File("data.dat");
        // var decoder = Base64.getDecoder();
        // var data = decoder.decode(
        // "nPcJkIXoZynY7IOUstgZMGnrtbNGXcqbzTycEXMKlJ51EAflIZU8FyDU3c6fxPZKQ+JKGI1RyqzwkvCfAvjzcQExvXTQLJ3bXztlqKEn72gOgo0xqo0QpQltqfjyoKceeG4VHXijxxnt8zaZ5s8xftg8777+qZcpEVLff9kyAjhWel9C57w647wMzINiXxIYQHlZrxvmsWSsaQvdAqrwgCSueL3aBzO\/mqUYQJwl2Lkdki5PRTov825eADzg8rGstcCnySf8J2QUZFC3j6Z2V+d9c1Qj9Wq50xWXVd1FTuaLex2BFlN3xI+nFdjlHJnDYpImE3o\/MMBk3+uF2T+fZVQrpVUWVRAcILUwGhVbfg4mMI4tw+I4A9t3P6mXVZ1Gt\/ixQrPHExkUpU5erz2S5hk6n0OjE1lCnn3IAnKlmWAYhZM1kmGe7cwb2IfS7S\/tYXkVOfmT50IiF9EMABiiiaYVtpgdFxWyt6IWy0+AraQmxrhOOBtTkiTb4CKs\/rUd1TbOFCftyIc4w6H\/thD\/o0SjxdKlgtElusN9J2kBGcnX4KaW7LYQEevB3gxA3feHNYM\/GqiS3XlO057O\/DDeleMZ4SFLh8CXcgPLV13KqqV5DadMQyx1\/UcwyNAUZaRDOathctkk4GAXJSFCMhODjqqm4GsDlb6K0+C7ukV026ncLREhzvS0\/TCloM+eoQc2Uji688oWUsyAZ62RTGDE1L3qmxVWJSjOtCqMsjX3WCaytFZjda\/Uv\/kHYqRa7WxTPyXBIge3BTJTCOgk0D4TAE6ldr+eGEeScbhLwNBQxZSnDlWq1Z6jKsNldeJXFBHLPpo8PY6rzO87Jv8GqoAY+Bsej0ZPLfhx+T8PRULFXmRjlf9gFw5RAFEWo99uYaYlu1NfoY0USr9iGBSe5Nvgw9XgiqvWkA9vog2k9hNdGyFLYWIfRL0qy1M7XqMCoag5zpHoUxmx4HIlIYbnfbOCAIwSdycVCmHB1xVx6HQdCB7KZt5ej\/1iU2jc1LxH86i03vuy+yGSwKemRUZxBlBS4tLuGD4z\/aLa3Up4vDa0ACpf6YnR0VAevxOralh\/Pf8U1WShC0LmglKS8EM9IAaFFxfOACp\/8CjJhklTPxrM07bvtEPPy0xRb2PtZ+VmzqoXknYEa0LZ2wbELR9p2mYRlKIo8zNJzQJky5wDPC740OouDlouTTclbOQMrNu+OrYvpR3ox+WmjeWEXyk1uD4Art+kFf8phjcv0hhh6y7pH66+XTonvYrfeKhd29BEbWjVCQrBijg48TTx+f2ZsqSpQiBggAqH54pt2fGhv58IJNA7zNg+AEAkZ0tAVV0yKD6Ip3zZxM0Oh3UmWEONAnstw+SaRzddWiId6\/A6Vao14H27T3wit5c7TNldgYZAFY+pdDfS91OU27yks61LrY8TOr7Ftn5dIIpCl\/kfEqxxoEFambNOWplkB9amW6e52V+IsTeS5t2+kbcoPCBIevUhiyGFfKEJcMihqU2X5BXlhLu3XCcWC0jPVbXRNQfRcGSIwIQxD4WzpLfDgZiLPqmeHm00zHffUHD2VNdBGWBEfMHDniO+aSNxoazQIpNcYu3EFhQaUtrlWFenleIsCnY4cjUQvm5blB800tXDI7ag7Brrq800NCH4ZdRNDISmQf105Owe5u6V4ILQAe48Xr2YmTzFyVKvOKyAGAimOP6fJQroyJwZhTEjFLaJx+K6rK8YjBOcUKxNPG8MXhsIqPaZcmbCEflK88PeqFFydvL6hw2cH0hJpxEj\/yX0RIbcqJUy9tJMHbIReE+IjqS5pAZv6A==");
        // try {
        // file.createNewFile();
        // FileOutputStream fos = new FileOutputStream(file);
        // fos.write(data);
        // fos.close();
        // } catch (Exception e) {
        // System.out.println(e.toString());
        // }
        var encoder = Base64.getEncoder();
        try {
            FileInputStream fis = new FileInputStream(file);
            var data = fis.readAllBytes();
            var bstr = encoder.encode(data);
            fis.close();
            System.out.println(new String(bstr, "UTF-8"));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
