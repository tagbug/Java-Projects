package Client.GUI.Controller;

import Client.GUI.Frame.*;
import Client.GUI.Panel.*;
import Data.*;
import Data.ClientRequest.*;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.text.*;
import java.util.*;

/**
 * 考生窗口Controller
 *
 * @since 10
 */
public class TestController extends UserController<TestQuestionPanel> {
    private final Timer lastTimer;// 考试时间计时器
    private Date lastTime;// 交卷时间
    private static final int PER_QUESTION_TIME = 30;// 每道题给多长时间作答（秒）

    public TestController(TestFrame window, JTabbedPane tabbedPane, ArrayList<TestQuestionPanel> questionPanel) {
        super(window, tabbedPane, questionPanel);
        // 初始化计时器，每隔1s更新一次
        lastTimer = new Timer(1000, event -> goTestTime());
    }

    @Override
    void handleAction(JComponent source) {
        // 针对不同Action处理
        switch (source.getName()) {
            case "跳转题目" -> gotoQuestion(((JTextField) source).getText());
            case "上一题目" -> preQuestion();
            case "下一题目" -> nextQuestion();
            case "开始考试" -> startTest();
            case "交卷" -> tryToSaveScore();
            case "查询分数" -> queryScore();
            case "注销" -> logoutAction();
            default -> JOptionPane.showMessageDialog(window, "未知操作！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 私有方法串，处理具体Action事务

    private void gotoQuestion(String str) {
        if (testNotStartCheck())
            return;
        int target;
        try {
            target = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(window, "请输入有效数值！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (target < 1 || target > maxPos) {
            JOptionPane.showMessageDialog(window, "请输入有效数值！", "错误", JOptionPane.ERROR_MESSAGE);
        } else {
            nowPanel.remove(qPanels.get(nowPos - 1));
            nowPanel.add(qPanels.get(target - 1), BorderLayout.CENTER);
            nowPos = target;
            transToStart();
        }
    }

    private boolean testNotStartCheck() {
        if (nowPanel.getNowStateLabel().getText().equals("考试未开始")) {
            JOptionPane.showMessageDialog(window, "考试还未开始，请先获取题目..", "提示", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    private void transToStart() {
        if (!lastTimer.isRunning() && !nowPanel.getNowStateLabel().getText().equals("正在考试")) {
            nowPanel.getNowStateLabel().setText("正在考试");
            lastTime = new Date();
            lastTime.setTime(lastTime.getTime() + nowQuestionList.size() * PER_QUESTION_TIME * 1000);
            lastTimer.start();
        }
    }

    private void preQuestion() {
        if (testNotStartCheck())
            return;
        int target = nowPos - 1;
        if (target < 1) {
            JOptionPane.showMessageDialog(window, "已经到头了..", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            nowPanel.remove(qPanels.get(nowPos - 1));
            nowPanel.add(qPanels.get(target - 1));
            nowPos = target;
            transToStart();
        }
    }

    private void nextQuestion() {
        if (testNotStartCheck())
            return;
        int target = nowPos + 1;
        if (target > maxPos) {
            JOptionPane.showMessageDialog(window, "已经到末尾了..", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            nowPanel.remove(qPanels.get(nowPos - 1));
            nowPanel.add(qPanels.get(target - 1));
            nowPos = target;
            transToStart();
        }
    }

    private void startTest() {
        if (lastTimer.isRunning()) {
            JOptionPane.showMessageDialog(window, "目前正在考试，不能开始新考试！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        var request = new ClientRequest();
        request.setRequestType(TYPE.GetRandomQuestions);
        var rMap = new HashMap<String, String>();
        rMap.put("questionTableId", String.valueOf(nowPanel.getQuestionTableId()));
        request.setData(rMap);
        var rs = withReturnErrorCatch(request);
        if (rs == null)
            return;
        if (rs.isSucceed()) {
            var newList = rs.getResult();
            nowQuestionList.clear();
            nowQuestionList.addAll(newList);
            var newPanels = new ArrayList<QuestionPanel>();
            nowPanel.addQuestionsToPanels(newList, newPanels, "<当前题库无题目>", false);
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
        // 更新剩余时间视图，如果时间到则自动交卷
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
        if (testNotStartCheck())
            return;
        saveScore();
    }

    private void saveScore() {
        var request = new ClientRequest();
        request.setRequestType(TYPE.SetScore);
        // 考试状态设置
        nowPanel.getNowStateLabel().setText("考试结束");
        lastTimer.stop();
        nowPanel.getLastTimeLabel().setText("");
        // 成绩统计
        int sumScore;
        double perScore = 100.0 / nowQuestionList.size();
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
            sumScore = (int) (perScore * correctCount);
        }
        // 上传成绩
        var map = new HashMap<String, String>();
        map.put("score", String.valueOf(sumScore));
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("time", ft.format(new Date()));
        request.setData(map);
        var rs = withReturnErrorCatch(request);
        if (rs == null)
            return;
        if (rs.isSucceed()) {
            JOptionPane.showMessageDialog(window, "上传成功！\n本次考试成绩：" + sumScore, "提示", JOptionPane.INFORMATION_MESSAGE);
            resetPanel();
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
            }
        }
    }

    private void resetPanel() {
        // 重置视图
        nowPanel.remove(qPanels.get(nowPos - 1));
        qPanels.clear();
        var newQPanels = new ArrayList<QuestionPanel>();
        newQPanels.add(new QuestionPanel(nowPanel.defaultQPanelInfo, "", ""));
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
        var rs = withReturnErrorCatch(request);
        if (rs == null)
            return;
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

    private void logoutAction() {
        window.getLoginFrame().setVisible(true);
        window.dispose();
        if (lastTimer.isRunning())
            lastTimer.stop();
        Thread.currentThread().interrupt();
    }
}
