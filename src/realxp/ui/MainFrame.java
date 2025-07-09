package realxp.ui;

import realxp.model.User;
import realxp.model.MyCharacter;
import realxp.service.UserService;
import realxp.service.QuestService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

public class MainFrame extends JFrame {

    private User loginUser;
    private UserService userService;
    private QuestService questService;
    private JLabel infoLabel;

    public MainFrame(User loginUser, UserService userService, QuestService questService) {
        this.loginUser = loginUser;
        this.userService = userService;
        this.questService = questService;

        setTitle("RealXp - 메인 화면");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🟢 캐릭터 정보 레이블 (North)
        infoLabel = new JLabel("", SwingConstants.CENTER);
        infoLabel.setFont(getCuteFont());
        infoLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(infoLabel, BorderLayout.NORTH);
        refreshInfo();

        // 🟢 캐릭터 이미지 (Center)
        MyCharacter character = loginUser.getCharacter();
        String imagePath = getImagePathByLevel(character.getLevel());
        JLabel imageLabel = createCharacterImageLabel(imagePath, 180, 180);
        imageLabel.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(imageLabel, BorderLayout.CENTER);

        // 🟢 버튼 패널 (South)
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton questRegisterButton = new JButton("퀘스트 등록");
        questRegisterButton.setFont(getCuteFont());
        questRegisterButton.addActionListener(e ->
            new RegisterQuestFrame(loginUser.getId(), userService, questService)
        );

        JButton questListButton = new JButton("퀘스트 목록");
        questListButton.setFont(getCuteFont());
        questListButton.addActionListener(e ->
            new QuestListFrame(loginUser, questService, MainFrame.this)
        );

        JButton myCharacterButton = new JButton("내 캐릭터 보기");
        myCharacterButton.setFont(getCuteFont());
        myCharacterButton.addActionListener(e ->
            JOptionPane.showMessageDialog(
                this,
                String.format("ID: %s\n레벨: %d\n경험치: %d / %d",
                    loginUser.getUsername(),
                    character.getLevel(),
                    character.getExp(),
                    character.getReaminExp()),
                "내 캐릭터 정보",
                JOptionPane.INFORMATION_MESSAGE
            )
        );

        JButton exitButton = new JButton("종료");
        exitButton.setFont(getCuteFont());
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(questRegisterButton);
        buttonPanel.add(questListButton);
        buttonPanel.add(myCharacterButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // 🟢 배경색 설정
        getContentPane().setBackground(new Color(245, 255, 245));

        setVisible(true);
    }

    /** 캐릭터 정보 갱신 */
    public void refreshInfo() {
        MyCharacter character = loginUser.getCharacter();
        String html = String.format(
            "<html><div style='text-align:center;'>ID: %s<br>레벨: %d<br>경험치: %d / %d</div></html>",
            loginUser.getUsername(),
            character.getLevel(),
            character.getExp(),
            character.getReaminExp()
        );
        infoLabel.setText(html);
    }

    /** 레벨에 따라 이미지 경로 반환 */
    private String getImagePathByLevel(int level) {
        if (level >= 20) return "/realxp/ui/images/character_lv5.png";
        if (level >= 15) return "/realxp/ui/images/character_lv4.png";
        if (level >= 10) return "/realxp/ui/images/character_lv3.png";
        if (level >= 5)  return "/realxp/ui/images/character_lv2.png";
        return "/realxp/ui/images/character_lv1.png";
    }

    /** 이미지 리사이즈해 JLabel 반환 */
    private JLabel createCharacterImageLabel(String imagePath, int maxWidth, int maxHeight) {
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl == null) {
            System.err.println("이미지를 불러올 수 없습니다: " + imagePath);
            return new JLabel("이미지 없음");
        }
        ImageIcon originalIcon = new ImageIcon(imageUrl);
        Image originalImage = originalIcon.getImage();
        int width = originalIcon.getIconWidth();
        int height = originalIcon.getIconHeight();
        float widthRatio = (float) maxWidth / width;
        float heightRatio = (float) maxHeight / height;
        float scale = Math.min(widthRatio, heightRatio);
        int newWidth = (int) (width * scale);
        int newHeight = (int) (height * scale);
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return imageLabel;
    }

    /** 귀여운 폰트 반환 */
    private Font getCuteFont() {
        try {
            return new Font("나눔손글씨 붓", Font.PLAIN, 16);
        } catch (Exception e) {
            return new Font("Segoe UI", Font.PLAIN, 16);
        }
    }
}
