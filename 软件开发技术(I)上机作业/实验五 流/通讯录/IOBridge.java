package 通讯录;

import java.io.*;

public class IOBridge {
    File file;
    FileWriter fw;
    FileReader fr;
    BufferedWriter bw;
    BufferedReader br;
    int lineIndex;

    IOBridge(String fileName) throws IOException {
        file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        fw = new FileWriter(file, true);
        bw = new BufferedWriter(fw);
    }

    public void write(String s) throws IOException {
        lineIndex += 1;
        bw.write(lineIndex + " " + s);
        bw.newLine();
        bw.write("------------------------------");
        bw.newLine();
        bw.flush();
    }

    public String read() throws IOException {
        fr = new FileReader(file);
        br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder();
        String s;
        lineIndex = 0;
        while ((s = br.readLine()) != null) {
            sb.append(s);
            sb.append('\n');
            lineIndex += 1;
        }
        lineIndex /= 2;
        return sb.toString();
    }
}
