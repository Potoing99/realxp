package realxp.ui;

import realxp.service.QuestService;
import realxp.service.UserService;

import javax.swing.*;
import java.awt.*;

public class RegisterQuestFrame extends JFrame {

    private int userId;
    private UserService userService;
    private QuestService questService;

    private JTextField titleField;
    private JTextField categoryField;

    public RegisterQuestFrame(int userId, UserService userService, QuestService questService) {
        this.userId = userId;
        this.userService = userService;
        this.questService = questService;

        setTitle("퀘스트 등록");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Font font = new Font("나눔고딕", Font.PLAIN, 14);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 제목 라벨 및 필드
        JLabel titleLabel = new JLabel("제목:");
        titleLabel.setFont(font);
        titleField = new JTextField();
        titleField.setFont(font);

        // 카테고리 라벨 및 필드
        JLabel categoryLabel = new JLabel("카테고리:");
        categoryLabel.setFont(font);
        categoryField = new JTextField();
        categoryField.setFont(font);

        // 등록 버튼
        JButton registerBtn = new JButton("등록");
        registerBtn.setFont(font);
        registerBtn.addActionListener(e -> registerQuest());

        // 빈 라벨 (레이아웃 정렬용)
        panel.add(titleLabel);
        panel.add(titleField);
        panel.add(categoryLabel);
        panel.add(categoryField);
        panel.add(new JLabel()); // 빈 칸
        panel.add(registerBtn);

        add(panel);
        setVisible(true);
    }

    private void registerQuest() {
        String title = titleField.getText().trim();
        String category = categoryField.getText().trim();

        if (!title.isEmpty() && !category.isEmpty()) {
            try {
                questService.addQuest(userId, title, category);
                JOptionPane.showMessageDialog(this, "퀘스트가 등록되었습니다!");
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "등록 실패: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "모든 항목을 입력해주세요.");
        }
    }
}
