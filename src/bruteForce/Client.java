package bruteForce;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

	private static int count =0;
	private static Socket sock;
	
	public Client(Socket sock){
		this.sock = sock;
	}

	public static void main(String[] args){
		if(args.length<3){
			System.err.println("Usage: java client <IP address> <Port number> <chunksize>");
			System.exit(0);
		}

		ObjectInputStream in = null;
		ObjectOutputStream out = null;

		sock = null;


		String address = args[0];
		int port = Integer.parseInt(args[1]);
		int chunkSize = Integer.parseInt(args[2]);

		try {

			sock = new Socket();
			sock.setReuseAddress(true);
			sock.connect(new InetSocketAddress(InetAddress.getLocalHost(), port));


			out = new ObjectOutputStream(sock.getOutputStream());
			in = new ObjectInputStream(sock.getInputStream());

			Message message;
			while(true){
				if(count==0){
					message = new Message(0, chunkSize);
					out.writeObject(message);
					count++;
				}

				message = (Message) in.readObject();
				
				if(message.getID()==-1){
					System.out.println("Key found by another client");
					System.exit(0);
				}

				Message r = Search.Decrypt(message.getBi(), message.getChuckSize(), message.getCipherText(), 20);

				if(r.getID()==1){
					String found = r.getFound();
					message = new Message(1, r.getBi(), found);
					out.writeObject(message);
					System.exit(-1);
				}

				else{
					message = new Message(-1,r.getBi(), chunkSize);
					out.writeObject(message);
				}

			}


		} catch (IOException e) {
			System.err.println(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
				if (sock != null)
					sock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}









}
