import java.util.Scanner;

/* Client class do the lower lever functions */
public class Client {
	Proxy p;
	Mode mode;
	static boolean loggedin;
	
	/* Constructor */
	public Client(){
		mode = new Mode(new FullMode());
		loggedin = false;
	}

	Scanner reader = new Scanner(System.in);
	
	/* Create a new account */
	public void Register(){
		System.out.println("Please input your UserName:");
		String userName = reader.nextLine();
		System.out.println("Please input your Password");
		String psd = reader.nextLine();
		p.Register(userName, psd);
	}
	/* Log in to an existing account */
	public void Login(){
		System.out.println("Please input your UserName:");
		String userName = reader.nextLine();
		System.out.println("Please input your Password");
		String psd = reader.nextLine();
		p.Login(userName, psd);
	}
	/* List all topics available in server*/
	public void List(){
		p.List();
	}
	/* Subscribe to a topic */
	public void Subscribe(){
		System.out.println("Please input the topic name you want to subscribe:");
		String topicName = reader.nextLine();
		p.Subscribe(topicName);
	}
	/* Request server to send all unread articles */
	public void Refresh(){
		p.Refresh();
	}
	
	public void Exit(){
		p.Exit();
	}
	/* Show the message */
	public void Display(String str){
		System.out.println(str);
	}
	/* Set the reading mode to show articles in different ways */
	public void setMode() {
		System.out.println("Please choose the mode: 'Full' or 'Concise'");
		String m = reader.nextLine();
		Mode mode = getMode();
		if(m.equals("Full")){
			mode = new Mode(new FullMode());
		}
		else if(m.equals("Concise")){
			mode = new Mode(new ConciseMode());
		}else{
			System.out.println("Wrong input!");
		}
		this.mode = mode;
	}
	
	public Proxy getP() {
		return p;
	}

	public void setP(Proxy p) {
		this.p = p;
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public boolean isLoggedin() {
		return loggedin;
	}

	public static void setLoggedin(boolean flag) {
		loggedin = flag;
	}

	

}
