package realxp.ui;

import realxp.model.User;
import realxp.service.QuestService;
import realxp.service.UserService;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField idField;
    private JPasswordField pwField;
    private JTextField nameField;
    private UserService userService;
    private QuestService questService;

    public RegisterFrame(UserService userService, QuestService questService) {
        this.userService = userService;
        this.questService = questService;

        setTitle("RealXP - 회원가입");
        setSize(350, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Font font;
        try {
            font = new Font("나눔손글씨 붓", Font.PLAIN, 16);
        } catch (Exception e) {
            font = new Font("Segoe UI", Font.PLAIN, 14);
        }

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        JLabel nameLabel = new JLabel("ID:");
        nameLabel.setFont(font);
        nameField = new JTextField();
        nameField.setFont(font);
        
        JLabel pwLabel = new JLabel("비밀번호:");
        pwLabel.setFont(font);
        pwField = new JPasswordField();
        pwField.setFont(font);


        panel.add(nameLabel); panel.add(nameField);
        panel.add(pwLabel); panel.add(pwField);

        JButton registerBtn = new JButton("가입하기");
        registerBtn.setFont(font);
        registerBtn.addActionListener(e -> register());

        JButton cancelBtn = new JButton("취소");
        cancelBtn.setFont(font);
        cancelBtn.addActionListener(e -> {
            dispose();
            new LoginFrame(userService, questService);
        });

        panel.add(registerBtn);
        panel.add(cancelBtn);

        add(panel);
        setVisible(true);
    }

    private void register() {
        String pw = new String(pwField.getPassword()).trim();
        String name = nameField.getText().trim();

        if (pw.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "모든 필드를 입력하세요.");
            return;
        }

        User newUser = new User();
        newUser.setPassword(pw);
        newUser.setUsername(name);

        boolean success = userService.register(newUser);
        if (success) {
            JOptionPane.showMessageDialog(this, "회원가입 성공! 로그인 해주세요.");
            dispose();
            new LoginFrame(userService,questService);
        } else {
            JOptionPane.showMessageDialog(this, "회원가입 실패: DB 오류");
        }
    }
}
