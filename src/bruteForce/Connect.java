package bruteForce;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;

public class Connect extends Thread{


	private Socket client;
	private static volatile BigInteger bi;
	private int keySize;
	private byte[] key;
	private byte[] ciphertext;
	private BigInteger start;
	private BigInteger end;
	private static int count =0;
	private static ArrayList<Socket> s = new ArrayList<Socket>();
	ObjectInputStream read = null;
	ObjectOutputStream write = null;
	String text = null;


	public Connect(Socket client, BigInteger bi, int keySize, byte[] key, byte[] ciphertext) {
		this.client = client;
		addToList(client);
		this.keySize = keySize;
		this.key = key;
		this.ciphertext = ciphertext;
		this.bi = bi;

	}

	public void addToList(Socket c){
		s.add(c);
	}
	
	
	
	public static BigInteger getBi(){return bi;}

	/**
	 * this method is invoked as a separate thread
	 */
	public void run(){

		

		try {
			//get the input and output streams associated with the socket
			read = new ObjectInputStream(client.getInputStream());
			write = new ObjectOutputStream(client.getOutputStream());
			double time = System.currentTimeMillis();

			while(true){

				Message m = (Message) read.readObject();
				int ID = m.getID();
				int size = m.getChuckSize();

				if(ID==0){//requesting for a chunk

//					if(m.getBi()!=null){
//						bi=m.getBi();
//					}
					
					System.out.println("chunk request");
					m = new Message(0, size, bi, ciphertext);
					write.writeObject(m);
//					sendToAll(m);
				}
				else if(ID==-1){//no solution found

					if(bi.bitCount()<=0){
						System.out.println("failed to crack the key");
						time = System.currentTimeMillis()-time;
						System.out.println("the time taken is: "+time);
						System.exit(-1);
					}
					//bi = bi.subtract(new BigInteger(""+size));
					System.out.println("chunk reduce");

					bi = m.getBi();


					m = new Message(0, size, bi, ciphertext);
					System.out.println("length: "+s.size());
					
//					sendToAll(m);
					
					write.writeObject(m);



				}
				else if(ID==1){//reporting a solution
//					m = new Message(-1, size);
					System.out.println("Key Found!!");
					System.out.println(m.getKey());
					time = System.currentTimeMillis()-time;
					System.out.println("The time taken is: "+time);
					System.exit(0);
				}

			}

		}catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				if(read!=null){
					read.close();
				}
				if(write!=null){
					write.close();
				}
				if(client!=null){
					client.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}
	
	
	 public void sendToAll(Message m){
	        for(Socket client : s)
				try {
					write = new ObjectOutputStream(client.getOutputStream());
					write.writeObject(m);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    }

	
	
}

