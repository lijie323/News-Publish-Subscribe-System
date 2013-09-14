import java.util.ArrayList;

/* Base class for different mode. Different strategies will be taken for different mode. */
public class Mode { 
	private ModeStrategy mode;
	
	public Mode(ModeStrategy modestrategy){
		this.mode=modestrategy;
	}
	public ModeStrategy getMode(){
		return mode;
	}
	
	public void runMode(Client c, ArrayList<String> param){
		this.mode.runMode(c,param);
	}

}
