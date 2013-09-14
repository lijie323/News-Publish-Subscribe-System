import java.io.*;
import java.net.Socket;

/* A thread used to send message to certain clients */
public class SubThread implements Runnable{
	String userName;
	Socket socket;
	ObjectOutputStream out = null;
//	ObjectInputStream in = null;
	Proxy p;
	CallMessage cm = null;
	int stop = 0;
	
	public SubThread(Socket s, Proxy proxy){
//		try {
			socket = s;
			userName = null;
			p = proxy;
			p.setSender(this);
			try {
				out = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(stop==0){
		try {
			if(cm!=null){
				out.writeObject(cm);
				cm = null;
			}
			Thread.sleep(1000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	public String getUser() {
		return userName;
	}

	public void setUser(String user) {
		userName = user;
		System.out.println("Current user is "+user);
	}

	public Proxy getP() {
		return p;
	}

	public void setP(Proxy p) {
		this.p = p;
	}

	public CallMessage getCm() {
		return cm;
	}

	public void setCm(CallMessage cm) {
		this.cm = cm;
	}

	public void end() {
		stop = 1;
		// TODO Auto-generated method stub
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
