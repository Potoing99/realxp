package realxp.ui;

import realxp.model.Quest;
import realxp.model.User;
import realxp.service.QuestService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class QuestListFrame extends JFrame {
    private User loginUser;
    private QuestService questService;
    private MainFrame mainFrame;
    private JTable questTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    // 현재 로드된 퀘스트 목록을 저장
    private List<Quest> currentQuests;

    public QuestListFrame(User loginUser, QuestService questService, MainFrame mainFrame) {
        this.loginUser = loginUser;
        this.questService = questService;
        this.mainFrame = mainFrame;

        setTitle("RealXP - 퀘스트 목록 및 검색");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 검색 패널
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("검색:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchButton = new JButton("검색");
        searchButton.addActionListener(e -> handleSearch());
        searchPanel.add(searchButton);
        JButton resetButton = new JButton("전체보기");
        resetButton.addActionListener(e -> refreshList());
        searchPanel.add(resetButton);

        // 테이블 모델 (번호 컬럼으로 대체)
        String[] columnNames = {"순번", "제목", "카테고리", "상태"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        questTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(questTable);

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton("완료", e -> handleComplete()));
        buttonPanel.add(createButton("수정", e -> handleEdit()));
        buttonPanel.add(createButton("삭제", e -> handleDelete()));
        buttonPanel.add(createButton("닫기", e -> dispose()));

        // 레이아웃 배치
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // 초기 로드
        refreshList();
        setVisible(true);
    }

    private JButton createButton(String text, java.awt.event.ActionListener al) {
        JButton btn = new JButton(text);
        btn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        btn.addActionListener(al);
        return btn;
    }

    private void handleSearch() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "검색어를 입력하세요.");
            return;
        }
        loadData(questService.searchQuestsByTitle(loginUser.getId(), keyword));
    }

    private void handleComplete() {
        int selectedRow = questTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "완료할 퀘스트를 선택하세요.");
            return;
        }
        int questId = currentQuests.get(selectedRow).getId();
        questService.completeQuestAndReward(loginUser, questId);
        JOptionPane.showMessageDialog(this, "퀘스트 완료! 경험치 +100");
        refreshList();
        mainFrame.refreshInfo();
    }

    private void handleEdit() {
        int selectedRow = questTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "수정할 퀘스트를 선택하세요.");
            return;
        }
        Quest q = currentQuests.get(selectedRow);
        JPanel panel = new JPanel(new GridLayout(2,2));
        panel.add(new JLabel("제목:"));
        JTextField titleField = new JTextField(q.getTitle());
        panel.add(titleField);
        panel.add(new JLabel("카테고리:"));
        JTextField categoryField = new JTextField(q.getCategory());
        panel.add(categoryField);
        int res = JOptionPane.showConfirmDialog(this, panel, "퀘스트 수정", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            questService.updateQuest(q.getId(), titleField.getText().trim(), categoryField.getText().trim());
            JOptionPane.showMessageDialog(this, "퀘스트가 수정되었습니다.");
            refreshList();
            mainFrame.refreshInfo();
        }
    }

    private void handleDelete() {
        int selectedRow = questTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "삭제할 퀘스트를 선택하세요.");
            return;
        }
        int questId = currentQuests.get(selectedRow).getId();
        int confirm = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "퀘스트 삭제", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            questService.deleteQuest(questId);
            JOptionPane.showMessageDialog(this, "퀘스트가 삭제되었습니다.");
            refreshList();
            mainFrame.refreshInfo();
        }
    }

    private void refreshList() {
        loadData(questService.getAllQuestsByUserId(loginUser.getId()));
    }

    private void loadData(List<Quest> list) {
        currentQuests = list;
        tableModel.setRowCount(0);
        for (int i = 0; i < list.size(); i++) {
            Quest q = list.get(i);
            String status = q.isCompleted() ? "완료" : "진행중";
            Object[] row = { i + 1, q.getTitle(), q.getCategory(), status };
            tableModel.addRow(row);
        }
    }
}