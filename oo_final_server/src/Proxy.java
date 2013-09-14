import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/* Proxy generate CallMessage to send to the client, and handle the requests get from client */
public class Proxy {
	CallMessage cm;
	SubListener listener;
	SubThread sender;
	
	
	
	/* Send Notice */
	public void sendNotice(String str){
		CallMessage cm = new CallMessage();
		cm.setMethodType("Notice");
		cm.addParams(str);
		/* Send */
		sender.setCm(cm);
	}

	/* Send List */
	public void sendList(ArrayList<Topic> topList){
		CallMessage cm = new CallMessage();
		cm.setMethodType("List");
		for(Topic t:topList){
			cm.addParams(t.getName());
		}
		/* Send */
		sender.setCm(cm);
	}
	
	/* Send Article */
	public void sendArticle(Article a){
		CallMessage cm = new CallMessage();
		cm.setMethodType("Article");
		cm.addParams(a.getTopic());
		cm.addParams(a.getTitle());
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		cm.addParams(dateFormat.format(a.getPubTime()));
		cm.addParams(a.getContent());		
		/* Send */
		sender.setCm(cm);
	}
	
	/* Send verified */
	public void sendVerified(){
		CallMessage cm = new CallMessage();
		cm.setMethodType("Verified");
		System.out.println("Sending verified");
		
		/* Send */
		sender.setCm(cm);
	}
	
	/* Stop connection */
	public void sendBye(){
		CallMessage cm = new CallMessage();
		cm.setMethodType("Bye");
		/* Send */
		sender.setCm(cm);
	}
	
	/* Handle request from client */
	public void getRequest(CallMessage cm ){
		String method = cm.getMethodType();
		/* Create new user */
		if(method.equals("Register")){
			System.out.println("Register requested!");
			String userName = cm.getParams().get(0);
			String psd = cm.getParams().get(1);
			Server server = Server.getInstance();
			ArrayList<User> userList = server.getUserList();
			int flag = 0; 
			/* Check whether the userName is already in use */
			for(User u:userList){
				if(u.getUserName().equals(userName)){
					flag = 1;
					sendNotice("Sorry, the user name is already in use. Please choose another one.");
					break;
				}
			}
			if(flag == 0){
				User u = new User(userName, psd);
				server.addUser(u);
				sendVerified();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sendNotice("Welcom "+userName);
				sender.setUser(u.getUserName());
			}
		}
		/* Log in */
		else if(method.equals("Login")){
			System.out.println("Login requested!");
			String userName = cm.getParams().get(0);
			String psd = cm.getParams().get(1);
			Server server = Server.getInstance();
			ArrayList<User> userList = server.getUserList();
			int flag = 0;
			for(User u:userList){
				if(u.getUserName().equals(userName)&&u.getPassword().equals(psd)){
					flag =1;
					
					/* Make the user to observe all his subscribe list*/
					OnlineUser ou = new OnlineUser(sender.getUser(),this);				
					for(MessageQueue mq :u.getTopics()){
						String topName = mq.getTopicName();
						for(int i = 0; i<server.getTopicList().size(); i++){
							if(server.getTopicList().get(i).getName().equals(topName)){
								server.getTopicList().get(i).addObserver(ou);
							}
						}
					}
					
					sendVerified();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					sendNotice("Welcom back "+userName);
					sender.setUser(u.getUserName());
					break;
				}
			}
			if(flag == 0){
				sendNotice("Your input does not match our record. Please try again!");
			}
		}
		/* List topics */
		else if(method.equals("List")){
			System.out.println("List requested!");
			Server server = Server.getInstance();
			ArrayList<Topic> topList = server.getTopicList();
			/* Send Topic List */
			sendList(topList);
			
		}
		/* Subscribe */
		else if(method.equals("Subscribe")){
			System.out.println("Subscribe requested!");
			String topName = cm.getParams().get(0);
			String user = sender.getUser();
			Topic topic=null;
			Server server = Server.getInstance();
			ArrayList<Topic> topList = server.getTopicList();
			
			for(Topic t:topList){
				if(t.getName().equals(topName)){
					/* Add subscription */
					topic = t;
				}
			}
			if(topic!=null){
				server.addSubscription(user, topic.getName());
				/* Make the user to observe this topic */
				OnlineUser ou = new OnlineUser(sender.getUser(),this);				
				topic.addObserver(ou);
				sendNotice("You have successfully subscribed to the topic "+topName);
			}else{
				sendNotice("Sorry, no topic "+topName+" exists!");
			}
			
			
		}
		/* Refresh */
		else if(method.equals("Refresh")){
			System.out.println("Refresh requested!");
			String user = sender.getUser();
			Server server = Server.getInstance();
			ArrayList<Article> artList = server.refresh(user);
			if(artList!=null){
			for(Article article:artList){
				sendArticle(article);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
			
		}
		/* Clean */
		else if(method.equals("Bye")){
			String userName = sender.getUser();
			Server server = Server.getInstance();
			OnlineUser ou = new OnlineUser(sender.getUser(),this);
			
			/* Remove observer for the user's subscribe topics*/
			for(User u:server.getUserList()){
				if(u.getUserName().equals(userName)){
					for(MessageQueue mq:u.getTopics()){
						String topName = mq.getTopicName();
						for(int i = 0; i<server.getTopicList().size(); i++){
							if(server.getTopicList().get(i).getName().equals(topName)){
								server.getTopicList().get(i).deleteObserver(ou);
							}
						}
					}
				}
			}
			listener.end();
			sendBye();
			sender.end();
			System.out.print("Client disconnected!");
		}
	}
	
	

	public SubListener getListener() {
		return listener;
	}


	public void setListener(SubListener listener) {
		this.listener = listener;
	}

	public SubThread getSender() {
		return sender;
	}

	public void setSender(SubThread sender) {
		this.sender = sender;
	}
	
	
}
