package bruteForce;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Arrays;
import java.util.*;


/**
 * This class decrypts a Base64 ciphertext given a particular key (integer).
 *
 * No error checking on inputs!
 *
*/
public class Search {

    /**
    * Example illustrating how to search a range of keys.
    *
    * NO ERROR CHECKING, EXCEPTIONS JUST PROPAGATED.
    *
    * @param args[0] key represented as a big integer value
    * @param args[1] key size
    * @param args[2] ciphertext encoded as base64 value
    * @param args[3] range
     * @return
    */
    public static Message Decrypt(BigInteger big, int keySizes,byte[] cy, int r) throws Exception {

        // Extract the key, turn into an array (of right size) and
        //   convert the base64 ciphertext into an array
        BigInteger bi = big;
        int keySize = keySizes;
        byte[] key = Blowfish.asByteArray(bi, keySize);
        byte[] ciphertext = cy;
        int range = r;

        // Go into a loop where we try a range of keys starting at the given one
        String plaintext = null;
        // Search from the key that will give us our desired ciphertext
        for (int i=0; i<range; i++) {
            // tell user which key is being checked
            String keyStr = bi.toString();
            System.out.print(i+" ");
            System.out.print("Key is: "+keyStr);
            Thread.sleep(100);
            for (int j=0; j<keyStr.length();j++) {
                System.out.print("\b");
            }
            // decrypt and compare to known plaintext
            Blowfish.setKey(key);
            plaintext = Blowfish.decryptToString(ciphertext);
            System.out.println("decrypted: "+plaintext);
            if (plaintext.equals("May good flourish; Kia hua ko te pai")) {
                System.out.println("Plaintext found!");
                System.out.println(plaintext);
                String l = "key is (hex) " + Blowfish.toHex(key) + " " + bi;
                System.out.println(l);

                Message m = new Message(1, l, bi);

                //System.exit(-1);
                return m;
            }

            // try the next key
            bi = bi.add(BigInteger.ONE);
            key = Blowfish.asByteArray(bi,keySize);
        }
        System.out.println();
        System.out.println("No key found!");
        return new Message(-1, "not found", bi);
    }
}