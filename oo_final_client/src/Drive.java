import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/* User Interface*/
public class Drive {
	
	static Scanner reader = new Scanner(System.in);
	static Socket cSocket;
	static ClientSocket cs;
	
	
	public static void main(String arg[]){
		Client client = new Client();
		Proxy cp = new Proxy();
		cp.setClient(client);
		
		/* create socket thread */
		System.out.println("Please input 'ok' to make connection with the server");
		if(reader.nextLine().equals("ok")){
		try {
			cSocket = new Socket("JietekiMacBook-Air.local", 4567);
			cs = new ClientSocket(cp,cSocket);
			cp.setSocket(cSocket);
			Thread socketThread = new Thread(cs);
			socketThread.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.exit(0);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.exit(0);
			e.printStackTrace();
		}
		
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(!client.isLoggedin()){
			System.out.println("Please input the operation you want:'1' for Creating account; '2' for Login; '3' for exit");
			String op = reader.nextLine();
			if(op.equals("1")){
				client.Register();
			}else if(op.equals("2")){
				client.Login();
			}else if(op.equals("3")){
				/* exit */
				client.Exit();
				System.out.println("Bye!");
				System.exit(0);
			}else{
				System.out.println("Wrong input!");
			}
						
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		while(client.isLoggedin()){
			System.out.println("Please input the operation you want:'1' for Listing topics; '2' for Subscribing to a topic; '3' for Refreshing; '4' for change mode; '5' for exit");
			String op = reader.nextLine();
			if(op.equals("1")){
				client.List();
			}else if(op.equals("2")){
				client.Subscribe();
			}else if(op.equals("3")){
				client.Refresh();
			}else if(op.equals("4")){
				client.setMode();
			}else if(op.equals("5")){
				/* exit */
				client.Exit();
				Client.setLoggedin(false);

			}else{
				System.out.println("Wrong input!");
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		
		if(cSocket.isClosed()){
			System.out.print("Bye!");
			System.exit(0);
		}
		
	}	

}
