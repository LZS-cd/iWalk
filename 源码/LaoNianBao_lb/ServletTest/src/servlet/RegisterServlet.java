package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import com.mysql.jdbc.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    }  
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BufferedReader reader=request.getReader();
		String requestStr=reader.readLine();
		HashMap<String,String> responseMap=parseToMap(requestStr);
		
		String account = responseMap.get("account"); // 从 request 中获取名为 account 的参数的值  
        String password = responseMap.get("password"); // 从 request 中获取名为 password 的参数的值  
        String name=responseMap.get("name");
        String age=responseMap.get("age");
        String tel_number=responseMap.get("telephone");
        String resCode = "";  
        String resMsg = "";  
        String userId = "";  
        Date date=new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {  
            Connection connect = JdbcTest.getConnect();  
            Statement statement = connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现  
            ResultSet result;  
              
            String sqlQuery = "select * from " + JdbcTest.TABLE_PASSWORD + " where userAccount='" + account + "'";  
              
            // 查询类操作返回一个ResultSet集合，没有查到结果时ResultSet的长度为0  
            result = statement.executeQuery(sqlQuery); // 先查询同样的账号（比如手机号）是否存在  
            if(result.next()){ // 已存在  
                resCode = "200";  
                resMsg = "该账号已注册，请使用此账号直接登录或使用其他账号注册";  
                userId = "";  
            } else { // 不存在  
                String sqlInsertPass = "insert into " + JdbcTest.TABLE_PASSWORD + "(userAccount,userPassword,registerTime,type) "
                		+ "values('"+account+"','"+password+"','"+simpleDateFormat.format(date)+"','old')";  
                // 更新类操作返回一个int类型的值，代表该操作影响到的行数  
                int row1 = statement.executeUpdate(sqlInsertPass); // 插入帐号密码  
                if(row1 == 1){  
                    String sqlQueryId = "select userId from " + JdbcTest.TABLE_PASSWORD + " where userAccount='" + account + "'";  
                    ResultSet result2 = statement.executeQuery(sqlQueryId); // 查询新增记录的userId  
                    if(result2.next()){  
                        userId = result2.getInt("userId") + "";  
                    }  
                   String sqlInsertInfo = "insert into " + JdbcTest.TABLE_USERINFO + "(userId,userName,userAge,telephone) "
                   		+ "values("+Integer.parseInt(userId)+",'"+name+"',"+Integer.parseInt(age)+",'"+tel_number+"'"+")";  
                   int row2 = statement.executeUpdate(sqlInsertInfo); // 在用户信息表中插入刚注册的Id  
                    if(row2 == 1){ // 两个表中都插入成功，从业务角度认定为注册成功  
                        resCode = "100";  
                        resMsg = "注册成功";  
                    }  
                } else {  
                    resCode = "201";  
                    resMsg = "用户信息表插入错误";  
                    userId = "";  
                }  
            }  
              
        } catch (SQLException e) {  
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
