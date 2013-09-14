import java.util.Random;

/* A subtype of article */
public class Poem extends Article{
	public Poem(String topic){
		topicName = topic;
		Random rn = new Random();
		String num = Integer.toString(rn.nextInt());
		title = "Poem"+ num;
		content = "Hello! I am poem "+num;
	}
}
