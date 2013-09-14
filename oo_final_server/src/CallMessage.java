import java.io.Serializable;
import java.util.ArrayList;

/* The format for the message transported between the server and client*/
public class CallMessage implements Serializable{
	String methodType;
	ArrayList<String> params;
	public CallMessage(){
		params= new ArrayList<String>();
	}
	public String getMethodType() {
		return methodType;
	}
	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}
	public ArrayList<String> getParams() {
		return params;
	}
	public void addParams(String s){
		params.add(s);
	}
}
