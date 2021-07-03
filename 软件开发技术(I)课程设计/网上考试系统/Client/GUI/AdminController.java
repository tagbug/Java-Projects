package Client.GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.Callable;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Client.util.ClientSocket;
import Data.ClientRequest;
import Data.ClientRequest.TYPE;

public class AdminController implements ActionListener {
    private ClientSocket clientSocket;
    private ArrayList<AdminQuestionPanel> questionPanel;// 试题库页面容器
    private ArrayList<QuestionPanel> qPanels;// 题目容器
    private JTabbedPane tabbedPanel;
    private AdminFrame window;
    private AdminQuestionPanel nowPanel;
    private ArrayList<Map<String, String>> nowQuestionList;
    private int nowPos;
    private int maxPos;
    private boolean canRevoke;
    private HashSet<String> removedQuestionId;

    AdminController(AdminFrame window, ClientSocket clientSocket, JTabbedPane tabbedPane,
            ArrayList<AdminQuestionPanel> questionPanel) {
        this.window = window;
        this.clientSocket = clientSocket;
        this.tabbedPanel = tabbedPane;
        this.questionPanel = questionPanel;
        removedQuestionId = new HashSet<String>();
    }

    private void init() {
        nowPanel = questionPanel.get(tabbedPanel.getSelectedIndex());
        nowQuestionList = nowPanel.getQuestionList();
        qPanels = nowPanel.getQPanels();
        var pos = nowPanel.getPosLabel().getText().split("[/]");
        nowPos = Integer.parseInt(pos[0]);
        maxPos = Integer.parseInt(pos[1]);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        init();
        var source = (JComponent) e.getSource();
        switch (source.getName()) {
            case "增加新题库":
                addNewQuestionList();
                break;
            case "删除该题库":
                deleteQuestionList();
                break;
            case "批量导入题库":
                readFromFile();
                break;
            case "跳转题目":
                gotoQuestion(((JTextField) source).getText());
                break;
            case "上一题目":
                preQuestion();
                break;
            case "下一题目":
                nextQuestion();
                break;
            case "修改题目图片":
                changeQuestionImg();
                break;
            case "新建题目":
                addNewQuestion();
                break;
            case "删除题目":
                deleteQuestion();
                break;
            case "保存操作":
                saveAction();
                break;
            case "撤销操作":
                revokeAction();
                break;
            default:
                JOptionPane.showMessageDialog(window, "未知操作！", "错误", JOptionPane.ERROR_MESSAGE);
                break;
        }
        nowPanel.getPosLabel().setText(nowPos + "/" + maxPos);
        if (canRevoke) {
            nowPanel.getRevokeButton().setEnabled(true);
        }
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

    private void updateWithErrorCatch(Callable<Void> c) {
        try {
            c.call();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(window, "连接异常！即将退回登陆页面...\n原因：" + e.getMessage(), "错误",
                    JOptionPane.ERROR_MESSAGE);
            window.getLoginFrame().setVisible(true);
            window.dispose();
            Thread.currentThread().interrupt();
        }
    }

    private void addNewQuestionList() {
        var name = JOptionPane.showInputDialog(window, "请输入题库名", "提示", JOptionPane.INFORMATION_MESSAGE);
        if (name == null || name.isEmpty()) {
            return;
        }
        var request = new ClientRequest();
        request.setRequestType(TYPE.AddQuestionList);
        var map = new HashMap<String, String>();
        map.put("questionTableName", name);
        request.setData(map);
        if (withErrorCatch(request, true)) {
            // 操作成功，更新视图
            updateWithErrorCatch(() -> {
                var qPanel = new AdminQuestionPanel(clientSocket,
                        questionPanel.get(questionPanel.size() - 1).getQuestionTableId() + 1, this);
                questionPanel.add(qPanel);
                tabbedPanel.add(name, qPanel);
                return null;
            });
        }
    }

    private void deleteQuestionList() {
        int confirm = JOptionPane.showConfirmDialog(window, "真的要删除吗？此操作不可逆！", "提示", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = nowPanel.getQuestionTableId();
            var request = new ClientRequest();
            request.setRequestType(TYPE.DeleteQuestionList);
            var map = new HashMap<String, String>();
            map.put("questionTableId", String.valueOf(id));
            request.setData(map);
            if (withErrorCatch(request, true)) {
                // 操作成功，更新视图
                updateWithErrorCatch(() -> {
                    questionPanel.remove(tabbedPanel.getSelectedIndex());
                    tabbedPanel.remove(tabbedPanel.getSelectedIndex());
                    return null;
                });
            }
        }
    }

    private void gotoQuestion(String str) {
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
        }
    }

    private void preQuestion() {
        int target = nowPos - 1;
        if (target < 1) {
            JOptionPane.showMessageDialog(window, "已经到头了..", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            nowPanel.remove(qPanels.get(nowPos - 1));
            nowPanel.add(qPanels.get(target - 1));
            nowPos = target;
        }
    }

    private void nextQuestion() {
        int target = nowPos + 1;
        if (target > maxPos) {
            JOptionPane.showMessageDialog(window, "已经到末尾了..", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            nowPanel.remove(qPanels.get(nowPos - 1));
            nowPanel.add(qPanels.get(target - 1));
            nowPos = target;
        }
    }

    private void changeQuestionImg() {
        String imgSrc = JOptionPane.showInputDialog(window, "请输入图片URL：", "提示", JOptionPane.INFORMATION_MESSAGE);
        if (imgSrc == null || imgSrc.isEmpty())
            return;
        var oriPanel = qPanels.remove(nowPos - 1);
        nowPanel.remove(oriPanel);
        var newPanel = new QuestionPanel(oriPanel.getText(), imgSrc, oriPanel.getChoose());
        qPanels.add(nowPos - 1, newPanel);
        nowPanel.add(newPanel);
        canRevoke = true;
    }

    private void addNewQuestion() {
        var newQuestion = new QuestionPanel("<请在这里写入题目信息，在下面更改选项答案>", "", "A");
        qPanels.add(newQuestion);
        maxPos += 1;
        var map = new HashMap<String, String>();
        var nId = nowQuestionList.get(nowQuestionList.size() - 1).get("id");
        removedQuestionId.remove(nId);
        map.put("id", String.valueOf(Integer.parseInt(nId) + 1));
        map.put("text", "");
        map.put("imgSrc", "");
        map.put("chooseA", "");
        map.put("chooseB", "");
        map.put("chooseC", "");
        map.put("chooseD", "");
        map.put("answer", "");
        nowQuestionList.add(map);
        gotoQuestion(String.valueOf(maxPos));
        canRevoke = true;
    }

    private void deleteQuestion() {
        nowPanel.remove(qPanels.get(nowPos - 1));
        var removed = nowQuestionList.remove(nowPos - 1);
        removedQuestionId.add(removed.get("id"));
        qPanels.remove(nowPos - 1);
        maxPos -= 1;
        if (maxPos == 0) {
            addNewQuestion();
        } else {
            if (nowPos > maxPos)
                nowPos -= 1;
            nowPanel.add(qPanels.get(nowPos - 1), BorderLayout.CENTER);
        }
        canRevoke = true;
    }

    private void revokeAction() {
        nowPanel.remove(qPanels.get(nowPos - 1));
        qPanels.clear();
        for (int i = 0; i < nowQuestionList.size(); i++) {
            var map = nowQuestionList.get(i);
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
            qPanels.add(new QuestionPanel(sb.toString(), map.get("imgSrc"), map.get("answer")));
        }
        maxPos = qPanels.size();
        if (qPanels.size() == 0)
            qPanels.add(new QuestionPanel("<请在这里写入题目信息，在下面更改选项答案>", "", "A"));
        if (nowPos > maxPos)
            nowPos -= 1;
        nowPanel.add(qPanels.get(nowPos - 1), BorderLayout.CENTER);
        nowPanel.getRevokeButton().setEnabled(false);
        removedQuestionId.clear();
        canRevoke = false;
    }

    private void saveAction() {
        var newQuestionList = new ArrayList<Map<String, String>>();
        for (var e : qPanels) {
            var oriStr = e.getText();
            Map<String, String> map;
            try {
                map = questionStrParser(oriStr);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(window, "保存失败！" + e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            map.put("answer", e.getChoose());
            map.put("imgSrc", e.getImgSrc());
            newQuestionList.add(map);
        }
        int succeedCount = 0;
        int failedCount = 0;
        for (var e : nowQuestionList) {
            if (e.get("text").isEmpty()) {
                var map = new HashMap<String, String>();
                map.put("questionTableId", String.valueOf(nowPanel.getQuestionTableId()));
                var request = new ClientRequest();
                request.setRequestType(TYPE.SetQuestion);
                request.setData(map);
                if (withErrorCatch(request, false)) {
                    succeedCount += 1;
                } else {
                    failedCount += 1;
                }
            }
        }
        String[] fields = new String[] { "text", "imgSrc", "chooseA", "chooseB", "chooseC", "chooseD", "answer" };
        for (int i = 0; i < nowQuestionList.size(); i++) {
            for (var f : fields) {
                int compare = nowQuestionList.get(i).get(f).compareTo(newQuestionList.get(i).get(f));
                if (compare != 0) {
                    var map = newQuestionList.get(i);
                    map.put("id", nowQuestionList.get(i).get("id"));
                    map.put("questionTableId", String.valueOf(nowPanel.getQuestionTableId()));
                    var request = new ClientRequest();
                    request.setRequestType(TYPE.UpdateQuestion);
                    request.setData(map);
                    if (withErrorCatch(request, false)) {
                        succeedCount += 1;
                    } else {
                        failedCount += 1;
                    }
                    break;
                }
            }
        }
        for (var e : removedQuestionId) {
            var map = new HashMap<String, String>();
            map.put("questionTableId", String.valueOf(nowPanel.getQuestionTableId()));
            map.put("id", String.valueOf(e));
            var request = new ClientRequest();
            request.setRequestType(TYPE.DeleteQuestion);
            request.setData(map);
            if (withErrorCatch(request, false)) {
                succeedCount += 1;
            } else {
                failedCount += 1;
            }
        }
        if (!removedQuestionId.isEmpty()) {
            removedQuestionId.clear();
            var map = new HashMap<String, String>();
            map.put("questionTableId", String.valueOf(nowPanel.getQuestionTableId()));
            var request = new ClientRequest();
            request.setRequestType(TYPE.RefreshTable);
            request.setData(map);
            if (withErrorCatch(request, false)) {
                succeedCount += 1;
            } else {
                failedCount += 1;
            }
        }
        if (failedCount == 0) {
            JOptionPane.showMessageDialog(window, "保存成功！\n成功数：" + succeedCount + "\n失败数：" + failedCount, "提示",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (succeedCount == 0) {
                JOptionPane.showMessageDialog(window, "保存失败！\n成功数：" + succeedCount + "\n失败数：" + failedCount, "提示",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(window, "部分成功\n成功数：" + succeedCount + "\n失败数：" + failedCount, "提示",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        refreshAction();
    }

    private void refreshAction() {
        updateWithErrorCatch(() -> {
            var newList = nowPanel.getAllQuestions();
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
                newPanels.add(new QuestionPanel(sb.toString(), map.get("imgSrc"), map.get("answer")));
            }
            if (newPanels.size() == 0)
                newPanels.add(new QuestionPanel("<请在这里写入题目信息，在下面更改选项答案>", "", "A"));
            nowPanel.remove(qPanels.get(nowPos - 1));
            qPanels.clear();
            qPanels.addAll(newPanels);
            nowPos = 1;
            maxPos = qPanels.size();
            nowPanel.add(qPanels.get(0), BorderLayout.CENTER);
            return null;
        });
    }

    /**
     * 从文本文件中批量导入题库 格式要求：（注意一个文件只能有一个题库）
     * <p>
     * 
     * <pre>
     * 题库名字
     * 
     * 问题描述文本
     * A. 选项A文本
     * B. 选项B文本
     * C. 选项C文本
     * D. 选项D文本
     * 
     * NULL（图片地址，没有就是null）
     * B（本题答案）
     * 
     * 问题描述文本
     * A. 选项A文本
     * ........
     * </pre>
     */
    private void readFromFile() {
        var chooser = new JFileChooser(new File("."));
        chooser.setFileFilter(new FileNameExtensionFilter("文本文件", "txt"));
        int choose = chooser.showOpenDialog(window);
        if (choose != JFileChooser.APPROVE_OPTION)
            return;
        File file = chooser.getSelectedFile();
        try (var reader = new Scanner(file);) {
            String questionTableName = reader.nextLine();
            var questions = new ArrayList<Map<String, String>>();
            // 确定新题目的ID从哪里开始
            int nowQuestionId = 1;
            for (int i = 0; i < tabbedPanel.getTabCount(); i++) {
                if (tabbedPanel.getTitleAt(i).equals(questionTableName)) {
                    var list = questionPanel.get(i).getQuestionList();
                    nowQuestionId = Integer.parseInt(list.get(list.size() - 1).get("id")) + 1;
                }
            }
            // 循环录入
            while (reader.hasNextLine()) {
                var sb = new StringBuilder();
                try {
                    reader.nextLine();// 空行
                    sb.append(reader.nextLine());// 第一行-题目描述
                    if (sb.toString().isEmpty())
                        break;
                    sb.append("\n");
                    for (int i = 0; i < 4; i++) {
                        sb.append(reader.nextLine());
                        sb.append("\n");
                    }
                    String questionText = sb.toString();
                    var map = questionStrParser(questionText);
                    reader.nextLine();// 空行
                    String imgSrc = reader.nextLine();
                    map.put("imgSrc", imgSrc.equalsIgnoreCase("NULL") ? "" : imgSrc);
                    map.put("answer", reader.nextLine());
                    map.put("id", String.valueOf(nowQuestionId));
                    nowQuestionId += 1;
                    questions.add(map);
                } catch (NoSuchElementException e1) {
                    if (sb.toString().isEmpty())
                        break;
                    throw e1;
                }
            }
            boolean flag = false;
            for (int i = 0; i < tabbedPanel.getTabCount(); i++) {
                if (tabbedPanel.getTitleAt(i).equals(questionTableName)) {
                    // 存在题库，添加到现有题库中
                    flag = true;
                    tabbedPanel.setSelectedIndex(i);
                    init();
                    nowQuestionList.addAll(questions);
                    revokeAction();
                }
            }
            if (!flag) {
                // 不存在题库，
                var request = new ClientRequest();
                request.setRequestType(TYPE.AddQuestionList);
                var map = new HashMap<String, String>();
                map.put("questionTableName", questionTableName);
                request.setData(map);
                if (withErrorCatch(request, false)) {
                    // 操作成功，更新视图
                    updateWithErrorCatch(() -> {
                        var qPanel = new AdminQuestionPanel(clientSocket,
                                questionPanel.get(questionPanel.size() - 1).getQuestionTableId() + 1, this);
                        questionPanel.add(qPanel);
                        tabbedPanel.add(questionTableName, qPanel);
                        return null;
                    });
                    tabbedPanel.setSelectedIndex(tabbedPanel.getTabCount() - 1);
                    init();
                    nowQuestionList.addAll(questions);
                    revokeAction();
                } else {
                    JOptionPane.showMessageDialog(window, "新建题库时服务器出错！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            // 提交到服务器
            int succeedCount = 0;
            int failedCount = 0;
            for (int i = 0; i < questions.size(); i++) {
                var map = new HashMap<String, String>();
                map.put("questionTableId", String.valueOf(nowPanel.getQuestionTableId()));
                var request = new ClientRequest();
                request.setRequestType(TYPE.SetQuestion);
                request.setData(map);
                if (withErrorCatch(request, false)) {
                    succeedCount += 1;
                } else {
                    failedCount += 1;
                }
            }
            for (int i = 0; i < questions.size(); i++) {
                var map = questions.get(i);
                map.put("questionTableId", String.valueOf(nowPanel.getQuestionTableId()));
                var request = new ClientRequest();
                request.setRequestType(TYPE.UpdateQuestion);
                request.setData(map);
                if (withErrorCatch(request, false)) {
                    succeedCount += 1;
                } else {
                    failedCount += 1;
                }
            }
            JOptionPane.showMessageDialog(window, "批量导入题库成功！\n共导入" + questions.size() + "个题目\n提交到服务器：\n操作成功数："
                    + succeedCount + "\n失败数：" + failedCount, "提示", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(window, "输入文件或文件格式有误！\n错误信息：" + e.getMessage(), "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 将用户输入转换成标准题目格式
     * 
     * @param str
     * @return
     * @throws Exception 用户输入异常
     */
    private Map<String, String> questionStrParser(String str) throws Exception {
        var map = new HashMap<String, String>();
        String[] cRegex = new String[] { "A", "B", "C", "D" };
        String sRegex = "[.][ ]";
        String[] a = str.split("[\n]");
        if (a.length != 5) {
            throw new Exception("输入格式异常！");
        }
        map.put("text", a[0]);
        for (int i = 1; i < a.length; i++) {
            a[i] = a[i].replaceFirst(cRegex[i - 1] + sRegex, "");
            map.put("choose" + cRegex[i - 1], a[i]);
        }
        return map;
    }
}
