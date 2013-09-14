import java.util.Vector;

/* A FIFO message queue to store the unread article for each user*/
public class MessageQueue {
	Vector<Article> queue;
	String topicName;
	String userName;
	public MessageQueue(String u, String t){
		queue = new Vector<Article>();
		userName = u;
		topicName = t;
	}
	public void addMessage(Article m){
		queue.add(m);
		System.out.println("Add "+m.getTitle()+" to "+userName+"'s queue!");
	} 
	public Article popMessage(){
		Article m = null;
		if(!isEmpty()){
			m = queue.firstElement();
			queue.removeElementAt(0);
		}
			return m;
	}

	
	public boolean isEmpty(){
		return queue.isEmpty();
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	
}
