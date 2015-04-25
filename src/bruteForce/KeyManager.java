package bruteForce;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class KeyManager {

	public static void main(String[] args){

		// Extract the key, turn into an array (of right size) and
        //   convert the base64 ciphertext into an array
        BigInteger bi = new BigInteger(args[0]);
        int keySize = Integer.parseInt(args[1]);
        byte[] key = Blowfish.asByteArray(bi, keySize);
        byte[] ciphertext = Blowfish.fromBase64(args[2]);



		ServerSocket sock = null;

		try {
			//establish the socket
			sock = new ServerSocket(0);
			System.out.println("waiting for connection "+sock.getLocalPort());

			/**
			 * listen for new connection requests.
			 * when a request arrives, pass the socket to
			 * a separate thread and resume listening for
			 * more requests
			 */

			while(true){
				//listen for connections
				Socket client = sock.accept();
				Connect c = new Connect(client, bi, keySize, key, ciphertext);
				c.start();
			}



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(sock!=null){
				try {
					sock.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}


	}
}
