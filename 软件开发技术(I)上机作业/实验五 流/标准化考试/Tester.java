package 标准化考试;

import java.io.*;
import java.util.Scanner;

public class Tester {
    public enum Choose {
        A, B, C, D
    };

    private boolean hasTest;
    private Choose correctChoose;
    private String nextTest;
    private Scanner reader;
    private int totalScore;

    Tester(File txt) throws FileNotFoundException {
        reader = new Scanner(txt);
    }

    public Choose charToAnswer(char c) throws IllegalArgumentException {
        switch (c) {
            case 'A':
                return Choose.A;
            case 'B':
                return Choose.B;
            case 'C':
                return Choose.C;
            case 'D':
                return Choose.D;
            default:
                throw new IllegalArgumentException("非法选择项：" + c);
        }
    }

    public boolean hasNext() {
        if (hasTest) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        while (reader.hasNext()) {
            String s = reader.nextLine();
            if (s.startsWith("------")) {
                try {
                    correctChoose = charToAnswer(s.charAt(6));
                    hasTest = true;
                    break;
                } catch (IllegalArgumentException e) {
                    sb = new StringBuilder();
                    continue;
                }
            }
            sb.append(s + '\n');
        }
        if (hasTest) {
            nextTest = sb.toString();
            return true;
        }
        return false;
    }

    public String next() throws IllegalStateException {
        if (hasTest || hasNext()) {
            hasTest = false;
            return nextTest;
        }
        throw new IllegalStateException("已经没有下一道题了！");
    }

    public boolean checkAnswer(char answer) throws IllegalStateException {
        if (correctChoose != null) {
            try {
                if (charToAnswer(answer) == correctChoose) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                return false;
            }
        }
        throw new IllegalStateException("还没有读取到有效答案！");
    }

    public void submitAnswer(char answer) throws IllegalStateException {
        if (checkAnswer(Character.toUpperCase(answer)))
            totalScore += 1;
    }

    public int getTotalScore() {
        return totalScore;
    }
}
