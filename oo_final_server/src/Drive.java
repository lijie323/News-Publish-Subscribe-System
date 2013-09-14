import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

/* Server side user interface */
public class Drive {
	
	public static void main(String arg[]){
		Server serverv = Server.getInstance();
		Scanner reader = new Scanner(System.in);
		ServerSocket serverSocket = null;
	    try {
	        serverSocket = new ServerSocket(4567);
	        System.out.println("Listen to port 4567");
	        ServerThread sThread = new ServerThread(serverSocket);
	        Thread listener = new Thread(sThread);
	        listener.start();
	     } catch (IOException e) {
	        System.err.println(e.getMessage());
	        System.exit(1);
	     }
	        
	      while(true){
	    	  Server server = Server.getInstance();
	    	  System.out.println("Please input the operation you want.'1' for listing users; '2' for listing topics; '3' for adding topic; '4' for publish article");
	    	  String op = reader.nextLine();
	    	  if(op.equals("1")){
	    		  /*Show all users*/
	    		  for(User user:server.getUserList()){
	    			  System.out.println(user.getUserName());
	    		  }
	    	  }else if(op.equals("2")){
	    		  /*Show all topics*/
	    		  for(Topic topic:server.getTopicList()){
	    			  System.out.println(topic.getName());
	    		  }
	    	  }else if(op.equals("3")){
	    		  /* New topic*/
	    		  server.addTopic();
	    	  }else if(op.equals("4")){
	    		  /* New article*/
	    		  Article a = server.newArticle();
	    		  server.publishArticle(a);
	    	  }else{
	    		  System.out.println("Sorry, wrong input!");
	    	  }
	    	  try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	      }  
		
	}

}
