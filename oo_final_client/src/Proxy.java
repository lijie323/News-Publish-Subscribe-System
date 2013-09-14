import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/* Proxy do the real work, including generating CallMessage, send it to the Server and handle the received CallMessage from server*/
public class Proxy {
	
	Client client;
	Socket socket;
	ObjectOutputStream out = null;
	
	public Client getClient() {
		return client;
	}
	
	public void setClient(Client c) {
		this.client = c;
		c.setP(this);
	}
	
	public void setMode(){
		client.setMode();
	}
	
	
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket cSocket) {
		
		try {
			this.socket = cSocket;
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**************************Sending***********************/
	/* Create new account */
	public void Register(String userName, String password){
		CallMessage cm = new CallMessage();
		cm.setMethodType("Register");
		cm.addParams(userName);
		cm.addParams(password);
		/*Send*/
		try {
			out.writeObject(cm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/* Log in to an existing account */
	public void Login(String userName, String password){
		CallMessage cm = new CallMessage();
		cm.setMethodType("Login");
		cm.addParams(userName);
		cm.addParams(password);
		/*Send*/
		try {
			out.writeObject(cm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/* Request server to list all available topics */
	public void List(){
		CallMessage cm = new CallMessage();
		cm.setMethodType("List");
		/*Send*/
		try {
			out.writeObject(cm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/* Subscribe to a topic*/
	public void Subscribe(String topicName){
		CallMessage cm= new CallMessage();
		cm.setMethodType("Subscribe");
		cm.addParams(topicName);
		/*Send*/
		try {
			out.writeObject(cm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/* Request sender to send all the unread articles */
	public void Refresh(){
		CallMessage cm = new CallMessage();
		cm.setMethodType("Refresh");
		/*Send*/
		try {
			out.writeObject(cm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Exit(){
		CallMessage cm = new CallMessage();
		cm.setMethodType("Bye");
		/*Send*/
		try {
			out.writeObject(cm);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**************************Receiving***********************/
	public void getMessage(CallMessage cm){
		String methodType = cm.getMethodType();
		if(methodType.equals("Notice")){
			String notice = cm.getParams().get(0);
			client.Display(notice);
		}else if(methodType.equals("List")){
			ArrayList<String> TopName = cm.getParams();
			for(String name:TopName){
				client.Display(name);
			}
		}else if(methodType.equals("Article")){
			ArrayList<String> param = cm.getParams();
			Mode curmode = client.getMode();
			curmode.runMode(getClient(), param);
		}else if(methodType.equals("Verified")){
			Client.setLoggedin(true);
			System.out.println("Verified");
		}
	}
	
	
}
