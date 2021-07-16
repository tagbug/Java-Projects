package Client.GUI.Action;

import Client.util.*;
import Data.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

import static Data.ClientRequest.TYPE.Register;

public class RegisterAction extends ClientAction<JPanel> {
    private final JTextField idText;
    private final JTextField pasText;
    private final JRadioButton isAdminButton;

    public RegisterAction(ClientSocket clientSocket, JPanel panel, JTextField idText, JTextField pasText,
                          JRadioButton isAdminButton) {
        super("注册", clientSocket, panel);
        this.idText = idText;
        this.pasText = pasText;
        this.isAdminButton = isAdminButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClientRequest request = new ClientRequest();
        request.setRequestType(Register);
        var map = new HashMap<String, String>();
        map.put("name", idText.getText());
        map.put("password", pasText.getText());
        map.put("isAdmin", String.valueOf(isAdminButton.isSelected()));
        request.setData(map);
        withConnectionReset(()->{
            var response = clientSocket.query(request);
            if (response.isSucceed()) {
                JOptionPane.showMessageDialog(component, "注册成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(component, "注册失败！" + response.getFailReason(), "错误",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
