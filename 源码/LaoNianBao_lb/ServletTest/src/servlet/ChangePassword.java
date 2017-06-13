package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class ChangePassword
 */
@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BufferedReader reader=request.getReader();
		String re=reader.readLine();
		HashMap<String,String> hasmap=parseToMap(re);
		String account=hasmap.get("account");
		String password=hasmap.get("password");
		String resCode = "";  
        String resMsg = "";  
        try {
        	Connection connet=JdbcTest.getConnect();
			Statement statement=(Statement) connet.createStatement();
			ResultSet result; 
			String sqlQuery="select * from "+JdbcTest.TABLE_PASSWORD+" where userAccount="+"'"+account+"'";
			result=statement.executeQuery(sqlQuery);
			if(result.next())
			{
				String sqlUpdate="update "+JdbcTest.TABLE_PASSWORD+" set userPassword='"+password+"'"+" where userAccount="+"'"+account+"'";
				int row=statement.executeUpdate(sqlUpdate);
				if(row==1)
				{
					resCode="100";
					resMsg="修改成功";
				}
				else
				{
					resCode="201";
					resMsg="修改出错";
				}
			}
			else
			{
				resCode="200";
				resMsg="该账号不存在";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
        response.setContentType("text/html;charset=utf-8"); // 设置响应报文的编码格式  
        PrintWriter pw = response.getWriter(); // 获取 response 的输出流  
        pw.print(resCode); // 通过输出流把业务逻辑的结果输出  
        pw.flush();  
	}
	private HashMap<String,String> parseToMap(String str)
	{
		HashMap<String,String> result=new HashMap();
		String[] parm1=str.split("&");
		String[] parm2;
		for(String a:parm1)
		{
			 parm2=a.split("=");
			result.put(parm2[0], parm2[1]);
		}
		return result;
	}
}
