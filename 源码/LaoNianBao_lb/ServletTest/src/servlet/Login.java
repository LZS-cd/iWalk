package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import com.mysql.jdbc.Connection;
import org.json.*;
/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String code="";
		String message="";
		String account=request.getParameter("account");
		String password=request.getParameter("password");
		String type=request.getParameter("type");
		String name="";
		int age = 0;
		String telephone="";
		Connection connect=JdbcTest.getConnect();
		try {
			Statement statement=connect.createStatement();
			String sql="select * from "+JdbcTest.TABLE_PASSWORD+" where userAccount = "+
						"'"+account+"'";
			ResultSet result=statement.executeQuery(sql);
			if(result.next())
			{
				String reponese_password=result.getString(3);
				String reponse_type=result.getString(5);
				if(reponse_type.equals(type))
				{
					if(reponese_password.equals(password)){
						code="100";
						message="µÇÂ¼³É¹¦";
						if(type.equals("old")){
							int userId=result.getInt(1);
							sql="select * from "+JdbcTest.TABLE_USERINFO+" where userId="+userId;
							result=statement.executeQuery(sql);
							if(result.next())
								{
								name=result.getString(2);
								age=result.getInt(3);
								telephone=result.getString(4);
								}
						}
					}else{
						code="101";
						message="µÇÂ¼Ê§°Ü£¬ÃÜÂë´íÎó";
					}
				}else{
					code="200";
					message="µÇÂ¼Ê§°Ü£¬¿Í»§¶Ë´íÎó";
				}
				
			}
			else
			{
				code="201";
				message="µÇÂ¼Ê§°Ü£¬¸ÃÕËºÅÎ´×¢²á";
			}
			JSONObject jsonObject=new JSONObject();
			try {
				jsonObject.put("resultCode", code);
				jsonObject.put("name", name);
				jsonObject.put("age", age);
				jsonObject.put("telephone", telephone);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			response.setContentType("text/html;charset=utf-8");//ÖÐÎÄÌáÊ¾
			PrintWriter print=response.getWriter();
			print.print(jsonObject.toString());
			print.flush();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
