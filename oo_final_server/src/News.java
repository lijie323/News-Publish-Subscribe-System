import java.util.Random;

/* a subtype of article*/
public class News extends Article{
	public News(String topic){
		topicName = topic;
		Random rn = new Random();
		String num = Integer.toString(rn.nextInt());
		title = "News"+ num;
		content = "Hello! I am news "+num;
	}

}
