package FileExample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Example_7 {
    public static void main(String[] args) {
        File file = new File("Items.txt");
        String[] items = { "商品列表：", "刘一松，0元/台", "电视机📺，2333元/台", "电脑💻，6666元/台", "PS10🎮，9999元/台" };
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (var item : items) {
                bw.write(item);
                bw.newLine();
            }
            bw.close();
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String s;
            while ((s = br.readLine()) != null) {
                System.out.println(s);
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
