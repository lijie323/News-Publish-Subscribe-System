import java.io.*;
import java.net.*;

/* A thread used to listen from a certain client */
public class SubListener implements Runnable{
	Socket socket;
	ObjectInputStream in = null;
	Proxy p;
	Object inObject=null;
	int stop = 0;
	
	public SubListener(Socket s, Proxy proxy){
			socket = s;
			p = proxy;
	}

	@Override
	public void run() {
		try {
			in = new ObjectInputStream(socket.getInputStream());
			p.setListener(this);
			System.out.println();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			while(stop==0){
				/* Get request from client */
				try {
					if((inObject=in.readObject())!=null){
						CallMessage cm = (CallMessage) inObject;
						p.getRequest(cm);										
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public Proxy getP() {
		return p;
	}

	public void setP(Proxy p) {
		this.p = p;
	}

	public void end() {
		try {
			in.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		stop = 1;	
	}
	
	
}
