package Client.GUI;

import javax.swing.*;

import Client.util.ClientSocket;
import Data.ClientRequest;
import Data.ServerResponse;
import Data.ClientRequest.TYPE;

import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestController implements ActionListener {
    private ClientSocket clientSocket;
    private ArrayList<TestQuestionPanel> questionPanel;// 试题库页面容器
    private ArrayList<QuestionPanel> qPanels;// 题目容器
    private JTabbedPane tabbedPanel;
    private TestFrame window;
    private TestQuestionPanel nowPanel;
    private ArrayList<Map<String, String>> nowQuestionList;
    private int nowPos;
    private int maxPos;
    private Timer lastTimer;
    private Date lastTime;
    private static final int PER_QUESTION_TIME = 30;// 每道题给多长时间作答（秒）

    TestController(TestFrame window, ClientSocket clientSocket, JTabbedPane tabbedPane,
            ArrayList<TestQuestionPanel> questionPanel) {
        this.window = window;
        this.clientSocket = clientSocket;
        this.tabbedPanel = tabbedPane;
        this.questionPanel = questionPanel;
        lastTimer = new Timer(1000, event -> {
            goTestTime();
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        nowPanel = questionPanel.get(tabbedPanel.getSelectedIndex());
        nowQuestionList = nowPanel.getQuestionList();
        qPanels = nowPanel.getQPanels();
        var pos = nowPanel.getPosLabel().getText().split("[/]");
        nowPos = Integer.parseInt(pos[0]);
        maxPos = Integer.parseInt(pos[1]);
        var source = (JComponent) e.getSource();
        switch (source.getName()) {
            case "跳转题目":
                gotoQuestion(((JTextField) source).getText());
                break;
            case "上一题目":
                preQuestion();
                break;
            case "下一题目":
                nextQuestion();
                break;
            case "开始考试":
                startTest();
                break;
            case "交卷":
                tryToSaveScore();
                break;
            case "查询分数":
                queryScore();
                break;
            default:
                JOptionPane.showMessageDialog(window, "未知操作！", "错误", JOptionPane.ERROR_MESSAGE);
                break;
        }
        nowPanel.getPosLabel().setText(nowPos + "/" + maxPos);
        window.validate();
        window.repaint();
    }

    private boolean withErrorCatch(ClientRequest request, boolean showMessage) {
        try {
            var rs = clientSocket.query(request);
            if (rs.isSucceed()) {
                if (showMessage)
                    JOptionPane.showMessageDialog(window, "操作成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                if (showMessage)
                    JOptionPane.showMessageDialog(window, "操作失败！\n原因：" + rs.getFailReason(), "错误",
                            JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(window, "连接异常！即将退回登陆页面...\n原因：" + e.getMessage(), "错误",
                    JOptionPane.ERROR_MESSAGE);
            window.getLoginFrame().setVisible(true);
            window.dispose();
            Thread.currentThread().interrupt();
        }
        return false;
    }

    private ServerResponse withReturnErrorCatch(ClientRequest request, boolean showMessage) {
        try {
            return clientSocket.query(request);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(window, "连接异常！即将退回登陆页面...\n原因：" + e.getMessage(), "错误",
                    JOptionPane.ERROR_MESSAGE);
            window.getLoginFrame().setVisible(true);
            window.dispose();
            Thread.currentThread().interrupt();
        }
        return null;
    }

    private void gotoQuestion(String str) {
        if (nowPanel.getNowStateLabel().getText().equals("考试未开始")) {
            JOptionPane.showMessageDialog(window, "考试还未开始，请先获取题目..", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int target = 0;
        try {
            target = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(window, "请输入有效数值！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (target < 1 || target > maxPos) {
            JOptionPane.showMessageDialog(window, "请输入有效数值！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            nowPanel.remove(qPanels.get(nowPos - 1));
            nowPanel.add(qPanels.get(target - 1), BorderLayout.CENTER);
            nowPos = target;
            if (!lastTimer.isRunning() && !nowPanel.getNowStateLabel().getText().equals("正在考试")) {
                nowPanel.getNowStateLabel().setText("正在考试");
                lastTime = new Date();
                lastTime.setTime(lastTime.getTime() + nowQuestionList.size() * PER_QUESTION_TIME * 1000);
                lastTimer.start();
            }
        }
    }

    private void preQuestion() {
        if (nowPanel.getNowStateLabel().getText().equals("考试未开始")) {
            JOptionPane.showMessageDialog(window, "考试还未开始，请先获取题目..", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int target = nowPos - 1;
        if (target < 1) {
            JOptionPane.showMessageDialog(window, "已经到头了..", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            nowPanel.remove(qPanels.get(nowPos - 1));
            nowPanel.add(qPanels.get(target - 1));
            nowPos = target;
            if (!lastTimer.isRunning() && !nowPanel.getNowStateLabel().getText().equals("正在考试")) {
                nowPanel.getNowStateLabel().setText("正在考试");
                lastTime = new Date();
                lastTime.setTime(lastTime.getTime() + nowQuestionList.size() * PER_QUESTION_TIME * 1000);
                lastTimer.start();
            }
        }
    }

    private void nextQuestion() {
        if (nowPanel.getNowStateLabel().getText().equals("考试未开始")) {
            JOptionPane.showMessageDialog(window, "考试还未开始，请先获取题目..", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int target = nowPos + 1;
        if (target > maxPos) {
            JOptionPane.showMessageDialog(window, "已经到末尾了..", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            nowPanel.remove(qPanels.get(nowPos - 1));
            nowPanel.add(qPanels.get(target - 1));
            nowPos = target;
            if (!lastTimer.isRunning() && !nowPanel.getNowStateLabel().getText().equals("正在考试")) {
                nowPanel.getNowStateLabel().setText("正在考试");
                lastTime = new Date();
                lastTime.setTime(lastTime.getTime() + nowQuestionList.size() * PER_QUESTION_TIME * 1000);
                lastTimer.start();
            }
        }
    }

    private void startTest() {
        var request = new ClientRequest();
        request.setRequestType(TYPE.GetRandomQuestions);
        var rMap = new HashMap<String, String>();
        rMap.put("questionTableId", String.valueOf(nowPanel.getQuestionTableId()));
        request.setData(rMap);
        var rs = withReturnErrorCatch(request, false);
        if (rs.isSucceed()) {
            var newList = rs.getResult();
            nowQuestionList.clear();
            nowQuestionList.addAll(newList);
            var newPanels = new ArrayList<QuestionPanel>();
            for (int i = 0; i < newList.size(); i++) {
                var map = newList.get(i);
                var sb = new StringBuilder();
                sb.append(map.get("text"));
                sb.append("\nA. ");
                sb.append(map.get("chooseA"));
                sb.append("\nB. ");
                sb.append(map.get("chooseB"));
                sb.append("\nC. ");
                sb.append(map.get("chooseC"));
                sb.append("\nD. ");
                sb.append(map.get("chooseD"));
                newPanels.add(new QuestionPanel(sb.toString(), map.get("imgSrc"), ""));
            }
            if (newPanels.size() == 0)
                newPanels.add(new QuestionPanel("<当前题库无题目>", "", ""));
            nowPanel.remove(qPanels.get(nowPos - 1));
            qPanels.clear();
            qPanels.addAll(newPanels);
            nowPanel.add(qPanels.get(0), BorderLayout.CENTER);
            maxPos = qPanels.size();
            nowPanel.getNowStateLabel().setText("单击下一题或上一题按钮开始考试");
        } else {
            JOptionPane.showMessageDialog(window, "开始考试失败！获取题目时出错！\n原因：" + rs.getFailReason(), "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goTestTime() {
        Date nowDate = new Date();
        long last = lastTime.getTime() - nowDate.getTime();
        if (last <= 0) {
            saveScore();
        } else {
            long second = last / 1000;
            long minute = second / 60;
            second = second % 60;
            nowPanel.getLastTimeLabel().setText(minute + "分" + second + "秒");
        }
    }

    private void tryToSaveScore() {
        if (nowPanel.getNowStateLabel().getText().equals("考试未开始")) {
            JOptionPane.showMessageDialog(window, "考试还未开始，请先获取题目..", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        saveScore();
    }

    private void saveScore() {
        nowPanel.getNowStateLabel().setText("考试结束");
        lastTimer.stop();
        nowPanel.getLastTimeLabel().setText("");
        var request = new ClientRequest();
        request.setRequestType(TYPE.SetScore);
        double sumScore = 0;
        double perScore = 100 / nowQuestionList.size();
        int correctCount = 0;
        for (int i = 0; i < nowQuestionList.size(); i++) {
            String correct = nowQuestionList.get(i).get("answer");
            String choose = qPanels.get(i).getChoose();
            if (correct.equals(choose))
                correctCount += 1;
        }
        if (correctCount == nowQuestionList.size()) {
            sumScore = 100;
        } else {
            sumScore = perScore * correctCount;
        }
        var map = new HashMap<String, String>();
        map.put("score", String.valueOf(sumScore));
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("time", ft.format(new Date()));
        request.setData(map);
        var rs = withReturnErrorCatch(request, false);
        if (rs.isSucceed()) {
            JOptionPane.showMessageDialog(window, "上传成功！\n本次考试成绩：" + sumScore, "提示", JOptionPane.INFORMATION_MESSAGE);
            resetPanel();
            return;
        } else {
            int choose = JOptionPane.showConfirmDialog(window, "记录成绩出错！\n原因：" + rs.getFailReason() + "\n是否尝试重新记录？",
                    "提示", JOptionPane.YES_NO_OPTION);
            while (choose == JOptionPane.YES_OPTION && !withErrorCatch(request, false)) {
                choose = JOptionPane.showConfirmDialog(window, "记录成绩出错！" + "\n如果屡次出错，请联系工作人员\n是否尝试重新记录？", "提示",
                        JOptionPane.YES_NO_OPTION);
            }
            if (choose != JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(window, "本次考试成绩：" + sumScore + "（未保存）", "提示",
                        JOptionPane.INFORMATION_MESSAGE);
                resetPanel();
                return;
            }
        }
    }

    private void resetPanel() {
        nowPanel.remove(qPanels.get(nowPos - 1));
        qPanels.clear();
        var newQPanels = new ArrayList<QuestionPanel>();
        newQPanels.add(new QuestionPanel("<这里会显示题目信息，右边显示题目图片，在下方选择答案>", "", ""));
        qPanels.addAll(newQPanels);
        nowPanel.add(qPanels.get(0), BorderLayout.CENTER);
        nowQuestionList.clear();
        nowPos = 1;
        maxPos = 1;
    }

    private void queryScore() {
        var request = new ClientRequest();
        request.setRequestType(TYPE.GetScore);
        String[] arr = window.getTitle().split("[I][D][:]");
        String id = arr[arr.length - 1];
        var map = new HashMap<String, String>();
        map.put("userId", id);
        request.setData(map);
        var rs = withReturnErrorCatch(request, false);
        if (rs.isSucceed()) {
            var sb = new StringBuilder();
            var ori = rs.getResult();
            if (ori.isEmpty()) {
                sb.append("你还没有任何考试分数");
            } else {
                for (var elem : ori) {
                    sb.append(elem.get("userName"));
                    sb.append("   ");
                    sb.append(elem.get("time"));
                    sb.append("   ");
                    sb.append(elem.get("score"));
                    sb.append("\n");
                }
            }
            JOptionPane.showMessageDialog(window, "查询结果：\n" + sb.toString(), "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(window, "查询失败！\n原因：" + rs.getFailReason(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
