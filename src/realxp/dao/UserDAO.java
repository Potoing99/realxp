package realxp.dao;

import realxp.model.MyCharacter;
import realxp.model.User;
import realxp.db.DBUtil;

import java.sql.*;

public class UserDAO {
	
	public boolean insertUser(User user) {
		String sql = "INSERT INTO users (username, password, level, exp) VALUES (?, ?, ?, ?)";
		
		try(Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
		
			stmt.setString(1, user.getUsername());
			stmt.setString(2,  user.getPassword());
			stmt.setInt(3, user.getCharacter().getLevel());
			stmt.setInt(4, user.getCharacter().getExp());
			
			int result = stmt.executeUpdate();
			return result > 0;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;	
		}
	}
	
	public User getUserByUsername(String username) {
		String sql = "SELECT * FROM users WHERE username = ?";
		try(Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				int id = rs.getInt("id");
				String password = rs.getString("password");
				int level = rs.getInt("level");
				int exp = rs.getInt("exp");
				
				MyCharacter character = new MyCharacter(level, exp);
				User user = new User(username, password, character);
				user.setId(id);
				user.getCharacter().setLevel(level);
				user.getCharacter().setExp(exp);
				
				return user;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void updateCharacter(User user) {
		String sql = "UPDATE users SET `level` = ?, exp = ? WHERE username = ?";
		
		try(Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setInt(1, user.getCharacter().getLevel());
			pstmt.setInt(2, user.getCharacter().getExp());
			pstmt.setString(3, user.getUsername());
			
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
