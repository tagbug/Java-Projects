package FileExample;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;

public class Example_10 {
    public static void main(String[] args) {
        try {
            ByteArrayOutputStream outByte = new ByteArrayOutputStream();
            byte[] byteContent = "刘二松".getBytes();
            outByte.write(byteContent);
            ByteArrayInputStream inByte = new ByteArrayInputStream(outByte.toByteArray());
            byte[] backByte = new byte[outByte.toByteArray().length];
            inByte.read(backByte);
            System.out.println(new String(backByte));
            CharArrayWriter outChar = new CharArrayWriter();
            char[] charContent = "刘三松".toCharArray();
            outChar.write(charContent);
            char[] backChar = new char[outChar.toCharArray().length];
            CharArrayReader inChar = new CharArrayReader(outChar.toCharArray());
            inChar.read(backChar);
            System.out.println(new String(backChar));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
