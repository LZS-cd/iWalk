package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import java.util.*;
import org.json.*;
/**
 * Servlet implementation class GetLocation
 */
@WebServlet("/GetLocation")
public class GetLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetLocation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String account=request.getParameter("userAccount");
		String resCode="";
		String resMsg="";
		double latitude;
		double longitude;
		Date time;
		JSONObject json=new JSONObject();
		Connection connect=JdbcTest.getConnect();
		try {
			Statement statement=connect.createStatement();
			String sql="select * from "+JdbcTest.TABLE_LOCATION+" where userAccount = "+
					"'"+account+"'";
			ResultSet result=statement.executeQuery(sql);
			if(result.next())
			{
				latitude=result.getDouble(2);
				longitude=result.getDouble(3);
				time=result.getDate(4);
				json.put("latitude",latitude);
				json.put("longitude", longitude);
				json.put("time", time);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 response.setContentType("text/html;charset=utf-8"); // 设置响应报文的编码格式  
	     PrintWriter pw = response.getWriter(); // 获取 response 的输出流  
	     pw.print(json.toString()); // 通过输出流把业务逻辑的结果输出  
	     pw.flush();  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
