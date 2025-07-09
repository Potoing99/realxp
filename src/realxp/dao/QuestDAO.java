package realxp.dao;

import realxp.model.Quest;
import realxp.db.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestDAO {

    /** 퀘스트 등록 */
    public void insertQuest(int userId, String title, String category) {
        String sql = "INSERT INTO quests (user_id, title, category) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, title);
            ps.setString(3, category);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** 사용자별 전체 퀘스트 조회 */
    public List<Quest> getQuestsByUserId(int userId) {
        List<Quest> quests = new ArrayList<>();
        String sql = "SELECT id, user_id, title, category, completed FROM quests WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Quest q = new Quest(
                    rs.getString("title"),
                    rs.getString("category")
                );
                q.setId(rs.getInt("id"));
                q.setUserId(rs.getInt("user_id"));
                q.setCategory(rs.getString("category"));
                q.setCompleted(rs.getBoolean("completed"));
                quests.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quests;
    }

    /** 제목 키워드로 퀘스트 검색 */
    public List<Quest> searchQuestsByTitle(int userId, String keyword) {
        List<Quest> result = new ArrayList<>();
        String sql = "SELECT id, user_id, title, category, completed FROM quests WHERE user_id = ? AND title LIKE ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Quest q = new Quest(
                    rs.getString("title"),
                    rs.getString("category")
                );
                q.setId(rs.getInt("id"));
                q.setUserId(rs.getInt("user_id"));
                q.setCategory(rs.getString("category"));
                q.setCompleted(rs.getBoolean("completed"));
                result.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** 퀘스트 수정 */
    public void updateQuest(int questId, String newTitle, String newCategory) {
        String sql = "UPDATE quests SET title = ?, category = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newTitle);
            ps.setString(2, newCategory);
            ps.setInt(3, questId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** 퀘스트 완료 처리 */
    public void completeQuest(int questId) {
        String sql = "UPDATE quests SET completed = 1 WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, questId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** 퀘스트 삭제 */
    public void deleteQuest(int questId) {
        String sql = "DELETE FROM quests WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, questId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** 완료 상태별 조회 */
    public List<Quest> getQuestsByUserIdAndCompletion(int userId, boolean completed) {
        List<Quest> quests = new ArrayList<>();
        String sql = "SELECT id, user_id, title, category, completed FROM quests WHERE user_id = ? AND completed = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setBoolean(2, completed);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Quest q = new Quest(
                    rs.getString("title"),
                    rs.getString("category")
                );
                q.setId(rs.getInt("id"));
                q.setUserId(rs.getInt("user_id"));
                q.setCategory(rs.getString("category"));
                q.setCompleted(rs.getBoolean("completed"));
                quests.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quests;
    }
}
