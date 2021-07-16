package Client.GUI.Controller;

import Client.GUI.Frame.*;
import Client.GUI.Panel.*;
import Data.*;
import Data.ClientRequest.*;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * 管理员窗口Controller
 *
 * @since 10
 */
public class AdminController extends UserController<AdminQuestionPanel> {
    private boolean canRevoke;// 现在是否可以撤销操作
    private final HashSet<String> removedQuestionId;// 移除的题目ID

    public AdminController(AdminFrame window, JTabbedPane tabbedPane, ArrayList<AdminQuestionPanel> questionPanel) {
        super(window, tabbedPane, questionPanel);
        removedQuestionId = new HashSet<>();
    }

    @Override
    void handleAction(JComponent source) {
        // 针对不同Action处理
        switch (source.getName()) {
            case "增加新题库" -> addNewQuestionList();
            case "删除该题库" -> deleteQuestionList();
            case "批量导入题库" -> readFromFile();
            case "跳转题目" -> gotoQuestion(((JTextField) source).getText());
            case "上一题目" -> preQuestion();
            case "下一题目" -> nextQuestion();
            case "修改题目图片" -> changeQuestionImg();
            case "新建题目" -> addNewQuestion();
            case "删除题目" -> deleteQuestion();
            case "保存操作" -> saveAction();
            case "撤销操作" -> revokeAction(false);
            case "注销" -> logoutAction();
            default -> JOptionPane.showMessageDialog(window, "未知操作！", "错误", JOptionPane.ERROR_MESSAGE);
        }
        // 更新视图
        if (canRevoke) {
            nowPanel.getRevokeButton().setEnabled(true);
        }
    }

    // 私有方法串，处理具体Action事务

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
                var qPanel = new AdminQuestionPanel(
                        questionPanel.get(questionPanel.size() - 1).getQuestionTableId() + 1, this);
                questionPanel.add(qPanel);
                tabbedPanel.add(name, qPanel);
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
                });
            }
        }
    }

    private void gotoQuestion(String str) {
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
        }
    }

    private void preQuestion() {
        int target = nowPos - 1;
        if (target < 1) {
            JOptionPane.showMessageDialog(window, "已经到头了..", "提示", JOptionPane.INFORMATION_MESSAGE);
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
        String nId;
        if (nowQuestionList.isEmpty()) {
            nId = "1";
        } else {
            nId = nowQuestionList.get(nowQuestionList.size() - 1).get("id");
            removedQuestionId.remove(nId);
        }
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
                nowPos = maxPos;
            nowPanel.add(qPanels.get(nowPos - 1), BorderLayout.CENTER);
        }
        canRevoke = true;
    }

    private void revokeAction(boolean getFromCache) {
        try {
            nowPanel.remove(qPanels.get(nowPos - 1));
            qPanels.clear();
            if (!getFromCache) {
                nowQuestionList.clear();
                nowQuestionList.addAll(getAllQuestions(nowPanel.questionTableId));
            }
            nowPanel.addQuestionsToPanels(nowQuestionList, qPanels, null, true);
            maxPos = qPanels.size();
            if (qPanels.size() == 0)
                qPanels.add(new QuestionPanel("<请在这里写入题目信息，在下面更改选项答案>", "", "A"));
            if (nowPos > maxPos)
                nowPos = maxPos;
            nowPanel.add(qPanels.get(nowPos - 1), BorderLayout.CENTER);
            nowPanel.getRevokeButton().setEnabled(false);
            removedQuestionId.clear();
            canRevoke = false;
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(window, "连接异常！即将退回登陆页面...\n原因：" + e.getMessage(), "错误",
                    JOptionPane.ERROR_MESSAGE);
            window.getLoginFrame().setVisible(true);
            window.dispose();
            Thread.currentThread().interrupt();
        }
    }

    private void saveAction() {
        // 将用户操作结果保存到新List中
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
        // 提交用户操作到服务器
        int succeedCount = 0;
        int failedCount = 0;
        for (var e : nowQuestionList) {
            // 提交新建题目操作
            if (e.get("text").isEmpty()) {
                if (setQuestionToServer()) {
                    succeedCount += 1;
                } else {
                    failedCount += 1;
                }
            }
        }
        String[] fields = new String[]{"text", "imgSrc", "chooseA", "chooseB", "chooseC", "chooseD", "answer"};
        for (int i = 0; i < nowQuestionList.size(); i++) {
            for (var f : fields) {
                // 提交更新题目操作
                int compare = nowQuestionList.get(i).get(f).compareTo(newQuestionList.get(i).get(f));
                if (compare != 0) {
                    var map = newQuestionList.get(i);
                    map.put("id", nowQuestionList.get(i).get("id"));
                    if (updateQuestionToServer(map)) {
                        succeedCount += 1;
                    } else {
                        failedCount += 1;
                    }
                    break;
                }
            }
        }
        for (var e : removedQuestionId) {
            // 提交移除题目操作
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
            // 如果移除了题目，则需要再对数据库的ID主键进行刷新操作
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
        // 弹窗显示处理结果
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

    private void logoutAction() {
        window.getLoginFrame().setVisible(true);
        window.dispose();
        Thread.currentThread().interrupt();
    }

    private void refreshAction() {
        updateWithErrorCatch(() -> {
            var newList = getAllQuestions(nowPanel.questionTableId);
            nowQuestionList.clear();
            nowQuestionList.addAll(newList);
            var newPanels = new ArrayList<QuestionPanel>();
            nowPanel.addQuestionsToPanels(newList, newPanels, null, true);
            nowPanel.remove(qPanels.get(nowPos - 1));
            qPanels.clear();
            qPanels.addAll(newPanels);
            nowPos = 1;
            maxPos = qPanels.size();
            nowPanel.add(qPanels.get(0), BorderLayout.CENTER);
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
        try (var reader = new Scanner(file)) {
            String questionTableName = reader.nextLine();
            var questions = new ArrayList<Map<String, String>>();
            // 确定新题目的ID从哪里开始
            int nowQuestionId = 1;
            for (int i = 0; i < tabbedPanel.getTabCount(); i++) {
                if (tabbedPanel.getTitleAt(i).equals(questionTableName)) {
                    var list = questionPanel.get(i).getQuestionList();
                    if (!list.isEmpty()) {
                        nowQuestionId = Integer.parseInt(list.get(list.size() - 1).get("id")) + 1;
                    }
                }
            }
            // 循环录入缓存
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
            // 录入本地题库
            boolean flag = false;
            for (int i = 0; i < tabbedPanel.getTabCount(); i++) {
                if (tabbedPanel.getTitleAt(i).equals(questionTableName)) {
                    // 存在对应题库，添加到现有题库中
                    flag = true;
                    tabbedPanel.setSelectedIndex(i);
                    init();
                    nowQuestionList.addAll(questions);
                    revokeAction(true);
                }
            }
            if (!flag) {
                // 不存在对应题库，新建
                var request = new ClientRequest();
                request.setRequestType(TYPE.AddQuestionList);
                var map = new HashMap<String, String>();
                map.put("questionTableName", questionTableName);
                request.setData(map);
                if (withErrorCatch(request, false)) {
                    // 操作成功，更新视图
                    updateWithErrorCatch(() -> {
                        var qPanel = new AdminQuestionPanel(
                                questionPanel.get(questionPanel.size() - 1).getQuestionTableId() + 1, this);
                        questionPanel.add(qPanel);
                        tabbedPanel.add(questionTableName, qPanel);
                    });
                    tabbedPanel.setSelectedIndex(tabbedPanel.getTabCount() - 1);
                    init();
                    nowQuestionList.addAll(questions);
                    revokeAction(true);
                } else {
                    JOptionPane.showMessageDialog(window, "新建题库时服务器出错！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            // 提交到服务器
            int succeedCount = 0;
            int failedCount = 0;
            for (int i = 0; i < questions.size(); i++) {
                if (setQuestionToServer()) {
                    succeedCount += 1;
                } else {
                    failedCount += 1;
                }
            }
            for (Map<String, String> map : questions) {
                if (updateQuestionToServer(map)) {
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
     * @param str 题目文本
     * @return 题目Map(text + chooseA / B / C / D)
     * @throws Exception 用户输入异常
     */
    private Map<String, String> questionStrParser(String str) throws Exception {
        var map = new HashMap<String, String>();
        String[] cRegex = new String[]{"A", "B", "C", "D"};
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

    private boolean setQuestionToServer() {
        var map = new HashMap<String, String>();
        map.put("questionTableId", String.valueOf(nowPanel.getQuestionTableId()));
        var request = new ClientRequest();
        request.setRequestType(TYPE.SetQuestion);
        request.setData(map);
        return withErrorCatch(request, false);
    }

    private boolean updateQuestionToServer(Map<String, String> data) {
        data.put("questionTableId", String.valueOf(nowPanel.getQuestionTableId()));
        var request = new ClientRequest();
        request.setRequestType(TYPE.UpdateQuestion);
        request.setData(data);
        return withErrorCatch(request, false);
    }

    /**
     * 获取当前题目库ID下的全部题目
     *
     * @return 题目信息列表
     * @throws IllegalStateException 向服务器的出错
     * @throws IOException           连接异常
     */
    public final ArrayList<Map<String, String>> getAllQuestions(int questionTableId) throws IllegalStateException,
            IOException {
        var request = new ClientRequest();
        request.setRequestType(TYPE.GetAllQuestions);
        var data = new HashMap<String, String>();
        data.put("questionTableId", String.valueOf(questionTableId));
        request.setData(data);
        var rs = clientSocket.query(request);
        if (rs.isSucceed()) {
            return rs.getResult();
        }
        throw new IllegalStateException("连接错误！" + rs.getFailReason());
    }
}
