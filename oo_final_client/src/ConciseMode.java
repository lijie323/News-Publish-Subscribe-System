import java.util.ArrayList;

/* When the reading mode is set to Concise, will only display the topic, title and publish time of the article */
public class ConciseMode implements ModeStrategy{

	@Override
	public void runMode(Client c, ArrayList<String> param) {
		// TODO Auto-generated method stub
		c.Display(param.get(0)+":  '"+param.get(1)+"'  "+param.get(2));
	}

}
