package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import java.util.*;
import java.util.Date;
import java.text.*;
/**
 * Servlet implementation class UploadLocation
 */
@WebServlet("/UploadLocation")
public class UploadLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadLocation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		double latitude=Double.parseDouble(request.getParameter("latitude"));
		double longitude=Double.parseDouble(request.getParameter("longitude"));
		String account=request.getParameter("account");
		String resCode="";
		String resMsg="";
		Connection connect=JdbcTest.getConnect();
		Date date=new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Statement statement=connect.createStatement();
			String sql="select * from "+JdbcTest.TABLE_LOCATION+" where userAccount= '"+account+"'";
			ResultSet resultSet=statement.executeQuery(sql);
			if(resultSet.next())
			{
				String sqlUpdate="update "+JdbcTest.TABLE_LOCATION+" set latitude="+latitude+" ,longitude="+longitude+" ,time="+"'"+simpleDateFormat.format(date)+"'"
								+" where userAccount= '"+account+"'";
				int res=statement.executeUpdate(sqlUpdate);
				if(res==1)
				{
					resCode="100";
					resMsg="替换成功";
				}else{
					resCode="200";
					resMsg="替换错误";
				}
			}
			else{
				String sqlInsert="insert into "+JdbcTest.TABLE_LOCATION+"(userAccount,latitude,longitude,time)"
						+" values('"+account+"',"+latitude+","+longitude+","+"'"+simpleDateFormat.format(date)+"')";
				int res2=statement.executeUpdate(sqlInsert);
				if(res2==1)
				{
					resCode="101";
					resMsg="插入成功";
				}else{
					resCode="201";
					resMsg="插入错误";
				}
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8"); // 设置响应报文的编码格式  
        PrintWriter pw = response.getWriter(); // 获取 response 的输出流  
        pw.print(resCode); // 通过输出流把业务逻辑的结果输出  
        pw.flush();  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
