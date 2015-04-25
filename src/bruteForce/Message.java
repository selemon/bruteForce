package bruteForce;

import java.io.Serializable;
import java.math.BigInteger;

public class Message implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private int chunkSize;
	private BigInteger bi;
	private String found;
	private String key;
	private int ID;
	private byte[] cipher;


	public Message(int ID, int chunkSize, BigInteger bi){
		this.chunkSize = chunkSize;
		this.bi = bi;
		this.ID = ID;
	}

	public Message(int ID, String found, BigInteger bi){
		this.ID = ID;
		this.found = found;
		this.bi = bi;
	}

	public Message(int ID, BigInteger bi, String key){
		this.bi = bi;
		this.key = key;
		this.ID = ID;
	}

	public Message(int ID, int chunkSize) {
		this.chunkSize = chunkSize;
		this.ID = ID;
	}

	public Message(int ID, BigInteger bi,int chunkSize) {
		this.chunkSize = chunkSize;
		this.ID = ID;
		this.bi = bi;
	}
	
	public Message(int ID, int chunkSize, BigInteger bi, byte[] ciphertext) {
		this.chunkSize = chunkSize;
		this.bi = bi;
		this.ID = ID;
		this.cipher = ciphertext;
	}


	public byte[] getCipherText(){return cipher;}

	public String getFound(){return found;}
	public String getKey(){return key;}

	public int getID(){return ID;}

	public int getChuckSize(){return chunkSize;}
	public BigInteger getBi(){return bi;}




}
