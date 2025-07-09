package realxp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DBUtil {
	
	private static final String URL = "jdbc:mysql://localhost:3306/reality_xp?serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASSWORD= "1234";
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(URL, USER, PASSWORD);
		}catch(ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 로딩실패: " + e.getMessage());
		}catch(SQLException e) {
			System.out.println("DB 연결 실패: " + e.getMessage());;
		}
		return null;
	}
	
	public static void close(PreparedStatement pstmt, Connection conn) {
	
		try{
			if(pstmt != null) pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if(conn != null) conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
	    try {
	        if (rs != null) rs.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    try {
	        if (pstmt != null) pstmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    try {
	        if (conn != null) conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
