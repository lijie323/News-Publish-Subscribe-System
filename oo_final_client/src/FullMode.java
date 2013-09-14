import java.util.ArrayList;

/* When the reading mode is set to Full, all details of the article will be displayed */
public class FullMode implements ModeStrategy{

	@Override
	public void runMode(Client c,ArrayList<String> param) {
		// TODO Auto-generated method stub
		c.Display("Topic: "+param.get(0));
		c.Display("Title: "+param.get(1));
		c.Display("Publish Time: "+param.get(2));
		c.Display("Content: "+param.get(3));
	}

}
