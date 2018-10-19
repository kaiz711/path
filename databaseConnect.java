package map;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class databaseConnect {

	int[][] connect(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int matrix[][] = null;
		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/test?characterEncoding=UTF-8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "root", "5145");
			stmt = conn.createStatement();
			
			String sql = "SELECT MAX(x), MAX(y) FROM map";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int maxX = rs.getInt(1);
				int maxY = rs.getInt(2);
			
				matrix = new int[maxX+1][];
				for(int i=0;i<=maxX;i++) {
					matrix[i] = new int[maxY+1];
					for(int j=0;j<=maxY;j++)
						matrix[i][j] = 0;
				}
			}
			
			sql = "SELECT * FROM map";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				matrix[rs.getInt(1)][rs.getInt(2)] = 1;
			}
		}
		catch(ClassNotFoundException e){
			System.out.println(e);
		}
		catch(SQLException e){
			System.out.println(e);
		}
		
		return matrix;
	}	
}