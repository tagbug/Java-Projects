package 精编p201;

import java.io.*;

public class AnalysisResult {
    public static void main(String[] args) {
        File fRead = new File("精编p201/score.txt");
        File fWrite = new File("精编p201/scoreAnalysis.txt");
        try {
            Writer out = new FileWriter(fWrite, true);
            BufferedWriter bufferedWriter = new BufferedWriter(out);
            Reader in = new FileReader(fRead);
            BufferedReader bufferedReader = new BufferedReader(in);
            String s = null;
            while ((s = bufferedReader.readLine()) != null) {
                double totalScore = Fenxi.getTotalScore(s);
                s = s + "总分：" + totalScore;
                System.out.println(s);
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }
            bufferedReader.close();
            bufferedWriter.close();
            System.out.println("学号：3200608080，姓名：陈欣阳");
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}