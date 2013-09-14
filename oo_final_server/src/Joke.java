import java.util.Random;
/* A subtype of article*/
public class Joke extends Article{
	public Joke(String topic){
		topicName = topic;
		Random rn = new Random();
		String num = Integer.toString(rn.nextInt());
		title = "Joke"+ num;
		content = "Hello! I am joke "+num;
	}
}
