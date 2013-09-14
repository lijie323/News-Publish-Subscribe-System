import java.util.ArrayList;
import java.util.Observable;

/* Articles are divided to different topics and users can subscribe to different topics*/
public class Topic extends Observable{
	String name;
	ArrayList<String> subList;
	

	public Topic(String topicName) {
		name = topicName;
		subList = new ArrayList<String>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public ArrayList<String> getSubList() {
		return subList;
	}
	

	public void addUser(String userName) {
		subList.add(userName);
		System.out.print(name+" get new subscriber "+ userName);
		
	}
	
	public void change(){
		this.setChanged();
	}
}
