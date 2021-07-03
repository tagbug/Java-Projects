package Client.GUI;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.*;

import Client.util.ClientSocket;
import Data.ClientRequest;
import Data.ClientRequest.TYPE;

public class AdminQuestionPanel extends JPanel {
    private ClientSocket clientSocket;
    private int questionTableId;
    private ArrayList<Map<String, String>> questionList;
    private ArrayList<QuestionPanel> qPanels;
    private JLabel posLabel;
    private JButton revokeButton;

    AdminQuestionPanel(ClientSocket clientSocket, int questionTableId, ActionListener listener)
            throws IllegalStateException, IOException {
        setLayout(new BorderLayout());
        this.clientSocket = clientSocket;
        this.questionTableId = questionTableId;
        init(listener);
    }

    void init(ActionListener listener) throws IllegalStateException, IOException {
        questionList = getAllQuestions();
        qPanels = new ArrayList<QuestionPanel>();
        for (int i = 0; i < questionList.size(); i++) {
            var map = questionList.get(i);
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
        if (qPanels.size() == 0)
            qPanels.add(new QuestionPanel("<请在这里写入题目信息，在下面更改选项答案>", "", "A"));
        add(qPanels.get(0), BorderLayout.CENTER);
        var headPanel = new JPanel();
        headPanel.add(new JLabel("当前题目："));
        posLabel = new JLabel("1/" + qPanels.size());
        headPanel.add(posLabel);
        headPanel.add(new JLabel("跳转到："));
        var goText = new JTextField(4);
        goText.setName("跳转题目");
        goText.addActionListener(listener);
        headPanel.add(goText);
        var changeImgButton = new JButton("修改题目图片");
        changeImgButton.setName("修改题目图片");
        changeImgButton.addActionListener(listener);
        headPanel.add(changeImgButton);
        add(headPanel, BorderLayout.NORTH);
        var controlPanel = new JPanel();
        var preButton = new JButton("上一题目");
        preButton.setName("上一题目");
        preButton.addActionListener(listener);
        var nextButton = new JButton("下一题目");
        nextButton.setName("下一题目");
        nextButton.addActionListener(listener);
        var createButton = new JButton("新建题目");
        createButton.setName("新建题目");
        createButton.addActionListener(listener);
        var deleteButton = new JButton("删除题目");
        deleteButton.setName("删除题目");
        deleteButton.addActionListener(listener);
        var saveButton = new JButton("保存操作");
        saveButton.setName("保存操作");
        saveButton.addActionListener(listener);
        revokeButton = new JButton("撤销操作");
        revokeButton.setName("撤销操作");
        revokeButton.setEnabled(false);
        revokeButton.addActionListener(listener);
        controlPanel.add(preButton);
        controlPanel.add(nextButton);
        controlPanel.add(createButton);
        controlPanel.add(deleteButton);
        controlPanel.add(saveButton);
        controlPanel.add(revokeButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    public ArrayList<Map<String, String>> getAllQuestions() throws IllegalStateException, IOException {
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

    public ArrayList<Map<String, String>> getQuestionList() {
        return questionList;
    }

    public ArrayList<QuestionPanel> getQPanels() {
        return qPanels;
    }

    public int getQuestionTableId() {
        return questionTableId;
    }

    public JLabel getPosLabel() {
        return posLabel;
    }

    public JButton getRevokeButton() {
        return revokeButton;
    }
}
