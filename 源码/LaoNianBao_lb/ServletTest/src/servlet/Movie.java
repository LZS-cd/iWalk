package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import spider.*;
/**
 * Servlet implementation class Movie
 */
@WebServlet("/Movie")
public class Movie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Movie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		Rule rule = new Rule("http://laodianying.cctv.com/",  
                new String[] { "name" }, new String[] { "" },  
                null, -1, Rule.GET); 
	    List<LinkTypeData> extracts = ExtractService.extract(rule);  
	
	    JSONObject jsonObject=new JSONObject();
	    int length=1;
	    for (LinkTypeData data : extracts)
		{
	    	 if((data.getLinkText().indexOf("守卫") !=-1 || data.getLinkText().indexOf("人民" )!= -1|| 
        			 data.getLinkText().indexOf("国" )!= -1|| data.getLinkText().indexOf("" )!= -1
        			 ) && data.getLinkText().indexOf("公约") == -1 && data.getLinkText().length() < 10
        			 && data.getLinkText().length() != 0&& data.getLinkHref().indexOf("#" )== -1
        			 && data.getLinkHref().length() > 55 && data.getLinkText().indexOf("C") == -1 
        			 && data.getLinkText().indexOf("网") == -1 && data.getLinkText().indexOf("招聘") == -1 
        			 && data.getLinkText().indexOf("顾问") == -1 )
			{
		//	System.out.println(data.getLinkText());
		//	System.out.println("http://health.china.com" + data.getLinkHref());
		//	System.out.println("-----------------------------");
				try {
					JSONObject jsonObject1=new JSONObject();
					jsonObject1.put("标题", data.getLinkText());
					jsonObject1.put("Href", "http://health.china.com"+data.getLinkHref());
					 JSONArray jsonArr=new JSONArray();
					 jsonArr.put(jsonObject1);
					 jsonObject.put(length+"",jsonArr);
					 length++;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	    response.setContentType("text/html;charset=utf-8");
	    PrintWriter print=response.getWriter();
	    print.print(jsonObject.toString());
	    print.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	}

}
