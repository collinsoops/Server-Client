
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESEncryption {
    //creating an instance of the Cipher class for encryption
    private static Cipher encrypt;

    //initializing vector
    private static final byte[] initialization_vector = {22, 33, 11, 44, 55, 99, 66, 77};

    //main() method
    public static void fnDESEncryption(File file) {
        //path of the file that we want to encrypt
       // String textFile = "C:\\Users\\Kenya Aliens IT\\Desktop\\javatest\\new.txt";
        String textFile= String.valueOf(file);
        //path of the encrypted file that we get as output
        String encryptedData = "C:\\Users\\Kenya Aliens IT\\Desktop\\server\\encrypted\\enc.txt";
        //path of the decrypted file that we get as output
        String decryptedData = "C:\\Users\\Kenya Aliens IT\\Desktop\\javatest\\decrypted\\dec.txt";

        try {

            /*
           generating keys by using the KeyGenerator class
           SecretKey scrtkey = KeyGenerator.getInstance("DES").generateKey();
            System.out.println(scrtkey);
             */
            //generating secret key from a constant String Password
            String password = "3nuyeb9thd6";
            byte[] bytes = password.getBytes();
            DESKeySpec keySpec = new DESKeySpec(bytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey scrtkey = keyFactory.generateSecret(keySpec);

            AlgorithmParameterSpec aps = new IvParameterSpec(initialization_vector);
            //setting encryption mode
            encrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");
            encrypt.init(Cipher.ENCRYPT_MODE, scrtkey, aps);

            //calling encrypt() method to encrypt the file
            encryption(new FileInputStream(textFile), new FileOutputStream(encryptedData));

            //prints the stetment if the program runs successfully
            System.out.println("The file  has been successfully encrypted.");
        }
        //catching multiple exceptions by using the | (or) operator in a single catch block
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IOException | InvalidKeySpecException e) {
            //prints the message (if any) related to exceptions
            e.printStackTrace();
        }
    }

    //method for encryption
    private static void encryption(InputStream input, OutputStream output)
            throws IOException {
        output = new CipherOutputStream(output, encrypt);
        //calling the writeBytes() method to write the encrypted bytes to the file
        writeBytes(input, output);
    }


    //method for writting bytes to the files
    private static void writeBytes(InputStream input, OutputStream output)
            throws IOException {
        byte[] writeBuffer = new byte[512];
        int readBytes = 0;
        while ((readBytes = input.read(writeBuffer)) >= 0) {
            output.write(writeBuffer, 0, readBytes);
        }
        //closing the output stream
        output.close();
        //closing the input stream
        input.close();
    }
}
