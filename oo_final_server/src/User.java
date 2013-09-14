import java.util.ArrayList;


public class User {
	String userName;
	String password;
	ArrayList<MessageQueue> topicQueue;
	
	public User(String name, String psd){
		userName = name;
		password = psd;
		topicQueue = new ArrayList<MessageQueue>();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public ArrayList<MessageQueue> getTopics(){
		return topicQueue;
	}
	
	public void addTopic(MessageQueue topic){
		topicQueue.add(topic);
	}
	
}

