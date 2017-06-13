package servlet;

import java.sql.*;

import com.mysql.jdbc.Connection;

public class JdbcTest {
	public static final String TABLE_PASSWORD = "table_user_password";  
    public static final String TABLE_USERINFO = "table_user_info";  
    public static final String TABLE_LOCATION= "table_user_location";
public static Connection getConnect()
	{
	Connection conn = null;
	String url = "jdbc:mysql://localhost:3306/test"; // 数据库的Url
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("成功加载MySQL驱动程序");
			conn = (Connection)DriverManager.getConnection(url,"root","245412");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	return conn;
		
	
	
	}
}
