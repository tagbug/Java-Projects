package FileExample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Example_11 {
    public static void main(String[] args) {
        File file = new File("data.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            DataOutputStream outData = new DataOutputStream(fos);
            outData.writeInt(100);
            outData.writeLong(123456L);
            outData.writeFloat(3.1415926f);
            outData.writeDouble(987654321.1234);
            outData.writeBoolean(true);
            outData.writeChars("刘一松");
            outData.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            DataInputStream inData = new DataInputStream(fis);
            System.out.println(inData.readInt());
            System.out.println(inData.readLong());
            System.out.println(inData.readFloat());
            System.out.println(inData.readDouble());
            System.out.println(inData.readBoolean());
            char c = '\0';
            while ((c = inData.readChar()) != '\0') {
                System.out.print(c);
            }
            inData.close();
        } catch (EOFException eof) {
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
