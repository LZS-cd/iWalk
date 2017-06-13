package xg;
import com.tencent.xinge.Message;
import com.tencent.xinge.XingeApp;
import org.json.JSONObject;
public class LaoNianBaoPush {
	public static XingeApp xingeApp;
	public static JSONObject pushMessage(String account,Message message){
		xingeApp=new XingeApp(2100259782,"9178bb772d1bd0aa933d57c7fd6f993c");
		JSONObject a=xingeApp.pushSingleAccount(0,account,message);
		return a;
	}
}
