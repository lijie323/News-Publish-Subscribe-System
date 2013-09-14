import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/* The thread used to listen from the socket */
public class ClientSocket implements Runnable{
	Socket cSocket = null;
	ObjectInputStream in = null;
	Proxy p = null;
	CallMessage command = null;
	Thread ServerListenerThread = null;
	Object inObject;
	int stop = 0;
	/* Constructor */
	public ClientSocket(Proxy proxy,Socket socket){
		p = proxy;
		cSocket = socket;
		try {
			in = new ObjectInputStream(cSocket.getInputStream());
		} catch  (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: taranis.");
			System.exit(1);
		}
	}


			
	public void run() {
		System.out.println("I am socket thread!");
		while(stop==0){
			try {
				if((inObject = in.readObject())!=null){
					
					CallMessage cm = (CallMessage) inObject;
					System.out.println(cm.getMethodType());
					if(cm.getMethodType().equals("Bye")){
						Exit();
					}
					else{
						p.getMessage(cm);
					}
				}
				Thread.sleep(1000);
			} catch (IOException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
	}

	public void Exit() {
		// TODO Auto-generated method stub
		try {
			stop = 1;
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			in.close();
			cSocket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
