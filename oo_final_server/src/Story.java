import java.util.Random;

/* A subtype of the article*/
public class Story extends Article{
	public Story(String topic){
		topicName = topic;
		Random rn = new Random();
		String num = Integer.toString(rn.nextInt());
		title = "Story"+ num;
		content = "Hello! I am story "+num;
	}
}
