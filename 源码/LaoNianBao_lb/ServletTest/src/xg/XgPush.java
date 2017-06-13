package xg;
import com.tencent.xinge.Message;
import com.tencent.xinge.XingeApp;
import org.json.JSONObject;
public class XgPush {
	public static XingeApp xingeApp;
	public static JSONObject pushMessage(String account,Message message){
		xingeApp=new XingeApp(2100259252,"70c8d1fcaef3eae88ec8ce5654843df5");
		JSONObject a=xingeApp.pushSingleAccount(0,account,message);
		return a;
	}
}
