package realxp.service;

import realxp.dao.QuestDAO;
import realxp.dao.UserDAO;
import realxp.model.Quest;
import realxp.model.User;

import java.util.List;

public class QuestService {
    private final QuestDAO questDAO;
    private final UserDAO userDAO;

    public QuestService() {
        this.questDAO = new QuestDAO();
        this.userDAO = new UserDAO();
    }

    /** 새 퀘스트 등록 */
    public void addQuest(int userId, String title, String category) {
        questDAO.insertQuest(userId, title, category);
    }

    /** 사용자 전체 퀘스트 조회 */
    public List<Quest> getAllQuestsByUserId(int userId) {
        return questDAO.getQuestsByUserId(userId);
    }

    /** 제목 키워드로 검색 */
    public List<Quest> searchQuestsByTitle(int userId, String keyword) {
        return questDAO.searchQuestsByTitle(userId, keyword);
    }

    /** 퀘스트 수정 (제목 + 카테고리) */
    public void updateQuest(int questId, String newTitle, String newCategory) {
        questDAO.updateQuest(questId, newTitle, newCategory);
    }

    /** 퀘스트 완료 처리 + 경험치 보상 */
    public void completeQuestAndReward(User user, int questId) {
        questDAO.completeQuest(questId);
        user.getCharacter().gainExp(100);
        userDAO.updateCharacter(user);
    }

    /** 퀘스트 삭제 */
    public void deleteQuest(int questId) {
        questDAO.deleteQuest(questId);
    }

    /** 완료된 퀘스트만 조회 */
    public List<Quest> getCompletedQuests(int userId) {
        return questDAO.getQuestsByUserIdAndCompletion(userId, true);
    }

    /** 미완료된 퀘스트만 조회 */
    public List<Quest> getIncompleteQuests(int userId) {
        return questDAO.getQuestsByUserIdAndCompletion(userId, false);
    }
}
