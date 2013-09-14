import java.util.Observable;
import java.util.Observer;

/* observer to certain topics so a notification is sent when a new article in that topic is published */
public class OnlineUser implements Observer{
	Proxy proxy;
	String userName;

	public OnlineUser(String name, Proxy p) {
		userName = name;
		proxy = p;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof String){
			proxy.sendNotice((String) arg);
		}
		
	}

}
