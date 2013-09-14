import java.io.IOException;
import java.net.*;

/* A thread use to listen to socket and accept new connections */
public class ServerThread implements Runnable{
	ServerSocket serverSocket;
	
	public ServerThread(ServerSocket s){
		serverSocket = s;
		
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true){
			try {
				Socket subSocket = serverSocket.accept();
				System.out.println("Connected with a client!");
				
				/*Generate a subThread sender for the subSocket*/
				Proxy p = new Proxy();
				/*Generate a listener for the subSocket*/
				SubListener subl = new SubListener(subSocket,p);
				SubThread subs = new SubThread(subSocket, p);
				Thread threadl = new Thread(subl);
				Thread threads = new Thread(subs);
				threadl.start();
				threads.start();

				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}

}
