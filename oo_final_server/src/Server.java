import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

/* do lower level work of the server part*/
public class Server {
	static ArrayList<User> userList;
	static ArrayList<Topic> topicList;
	Scanner reader = new Scanner(System.in);
	
	private static Server instance = null;
	private Server(){};
	public static Server getInstance(){
		if(instance == null){
			instance = new Server();
			userList = new ArrayList<User>();
			topicList = new ArrayList<Topic>();
			
			
		}
		return instance;
	}

	public ArrayList<User> getUserList() {
		return userList;
	}
	public ArrayList<Topic> getTopicList() {
		return topicList;
	}
	
	public void addUser(User u){
		userList.add(u);
	}
	
	public static void addTopic(Topic t){
		topicList.add(t);
	}
	

	
	
	
	public void addSubscription(String userName, String topicName){
		userAddTopic(userName,topicName);
		topicAddUser(userName,topicName);
	}
	/* Add a topic to a user's subscribe list*/
	public void userAddTopic(String userName, String topicName){
		for(int i = 0; i<userList.size();i++){
			if(userList.get(i).getUserName().equals(userName)){
				MessageQueue mq = new MessageQueue(userName,topicName);
				userList.get(i).addTopic(mq);
				break;
			}
			
		}
	}
	/* Add a user to a topic's subscriber list */
	public void topicAddUser(String userName, String topicName){
		for(int i=0;i<topicList.size();i++){
			if(topicList.get(i).getName().equals(topicName)){
				topicList.get(i).addUser(userName);
			}
		}
	}
	
	/* Send unread articles to user when requested*/
	public ArrayList<Article> refresh(String userName) {
		ArrayList<Article> artList = new ArrayList<Article>();
		User curUser = null;
		for(User u:userList){
			if(u.getUserName().equals(userName)){
				curUser = u;
				break;
			}
		}
		for(MessageQueue mq:curUser.getTopics()){
			while(!mq.isEmpty()){
				artList.add(mq.popMessage());
			}
		}
		return artList;

	}
	
	/* Create a topic */
	public void addTopic(){
		System.out.println("Please input the topic name");
		String topName = reader.nextLine();
		/* Check if topic exist */
		int flag =0;
		for(Topic t:topicList){
			if(t.getName().equals(topName)){
				flag = 1;
				break;
			}
		}
		if(flag==1){
			System.out.println("Sorry, the topic "+topName+" has already exist. Please choose another one");
		}else{
			Topic newTopic = new Topic(topName);
			topicList.add(newTopic);
			System.out.println("Topic "+topName+" is created!");
		}
	}
	
	/* Publish a article under a topic */
	public void publishArticle(Article art){
		/* add the article to its topic's queue */
		Topic curTopic = null;
		String topName = art.getTopic();
		int flag = 0;
		for(int i = 0; i< topicList.size();i++){
			if(topicList.get(i).getName().equals(topName)){
				curTopic = topicList.get(i);
				
				/* Notify online subscribers to this topic */
				topicList.get(i).change();		
				topicList.get(i).notifyObservers("Topic "+curTopic.getName()+" has been updated! Please refresh to see.");
				flag = 1;
				
				/*Add the article to the topic's subscriber's message queue;*/
				art.setPubTime(new Date());
				for(String user:topicList.get(i).getSubList()){
					for(User u:userList){
						if(u.getUserName().equals(user)){
							for(MessageQueue queue:u.getTopics()){
								if(queue.getTopicName().equals(topName)){
									queue.addMessage(art);
									break;
								}
							}
						}
					}
					
				}
				break;
			}
		}
		/* topic does not exist*/
		if(flag==0){
			Topic newTopic = new Topic(topName);
			topicList.add(newTopic);
			System.out.println("Topic "+topName+" is created!");
			art.setPubTime(new Date());
			curTopic = newTopic;
			
			/*Add the article to the topic's subscriber's message queue;*/
			for(String user:newTopic.getSubList()){
				for(User u:userList){
					if(u.getUserName().equals(user)){
						for(MessageQueue queue:u.getTopics()){
							if(queue.getTopicName().equals(topName)){
								queue.addMessage(art);
								break;
							}
						}
					}
				}
				
			}
		}
	}
	
	/* Use ArticleFactory to generate new articles */
	public Article newArticle() {
		System.out.println("Please indicate the topic");
		String topic = reader.nextLine();
		String[] types = {"News","Story","Poem","Joke"};
		Random rn = new Random();
		int index = rn.nextInt(4);
		System.out.println(index);
		String type = types[index];
		Article art = ArticleFactory.getArticle(topic, type);
		return art;
		
	}

}
