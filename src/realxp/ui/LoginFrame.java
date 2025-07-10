package realxp.ui;

import realxp.model.User;
import realxp.service.UserService;
import realxp.service.QuestService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField idField;
    private JPasswordField pwField;
    private UserService userService;
    private QuestService questService;

    public LoginFrame(UserService userService, QuestService questService) {
        this.userService = userService;
        this.questService = questService;

        setTitle("RealXP - 로그인");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 폰트 설정
        Font font;
        try {
            font = new Font("나눔손글씨 붓", Font.PLAIN, 16);
        } catch (Exception e) {
            font = new Font("Segoe UI", Font.PLAIN, 14);
        }

        // UI 구성
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        JLabel idLabel = new JLabel("ID:");
        idLabel.setFont(font);
        idField = new JTextField();
        idField.setFont(font);

        JLabel pwLabel = new JLabel("비밀번호:");
        pwLabel.setFont(font);
        pwField = new JPasswordField();
        pwField.setFont(font);

        panel.add(idLabel);
        panel.add(idField);
        panel.add(pwLabel);
        panel.add(pwField);

        // 버튼
        JButton loginButton = new JButton("로그인");
        loginButton.setFont(font);
        loginButton.addActionListener(e -> login());

        JButton registerButton = new JButton("회원가입");
        registerButton.setFont(font);
        registerButton.addActionListener(e -> {
            dispose();
            new RegisterFrame(userService, questService);
        });

        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);
        setVisible(true);
    }

    private void login() {
        String id = idField.getText().trim();
        String pw = new String(pwField.getPassword()).trim();

        if (id.isEmpty() || pw.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID와 비밀번호를 모두 입력하세요.");
            return;
        }

        User user = userService.login(id, pw);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "로그인 성공!");
            dispose();
            new MainFrame(user, userService, questService);  // 로그인 성공 시 메인화면으로
        } else {
            JOptionPane.showMessageDialog(this, "로그인 실패: ID 또는 비밀번호가 올바르지 않습니다.");
        }
    }
}
