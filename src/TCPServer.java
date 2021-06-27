
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


class TCPServer {

    public static File getLatestFilefromDir(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName().toLowerCase();
                return name.endsWith(".txt") && pathname.isFile();
            }
        });
        // File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }
        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }

        return lastModifiedFile;
    }
    public static void sendFile(File myFile, BufferedOutputStream outToClient) throws FileNotFoundException {

        if (outToClient != null) {
            System.out.println(myFile);
            byte[] mybytearray = new byte[(int) myFile.length()];
            FileInputStream fileinput = null;
            try {
                fileinput = new FileInputStream(myFile);
            } catch (FileNotFoundException ex) {
            }
            BufferedInputStream bufferedinput = new BufferedInputStream(fileinput);

            try {
                bufferedinput.read(mybytearray, 0, mybytearray.length);
                outToClient.write(mybytearray, 0, mybytearray.length);
                outToClient.flush();
                outToClient.close();

                System.out.println("The file has been successfully sent");
            } catch (IOException ex) {
                System.out.println("The was an Error in sending the file");
            }
        }
    }

    //private final static String fileToSend = "C:\\Users\\Kenya Aliens IT\\Desktop\\sp\\n.pdf";

    public static void main(String args[]) throws Exception {

        while (true) {
            ServerSocket welcomeSocket = null;
            Socket connectionSocket = null;
            BufferedOutputStream outToClient = null;

            try {
                welcomeSocket = new ServerSocket(3248);
                connectionSocket = welcomeSocket.accept();
                outToClient = new BufferedOutputStream(connectionSocket.getOutputStream());
                System.out.println("-----The Client Has Connected Successfully--------");
            } catch (IOException ex) {
                //ex.printStackTrace();
            }

            if (outToClient != null) {
                File myFile = new File(String.valueOf(getLatestFilefromDir("C:\\Users\\Kenya Aliens IT\\Desktop\\server\\")));

                try {
                   //encrypt the output file
                   DESEncryption.fnDESEncryption(myFile);
                    //location of encrypted data
                    String encryptedData = "C:\\Users\\Kenya Aliens IT\\Desktop\\server\\encrypted\\enc.txt";
                    sendFile(new File(encryptedData), outToClient);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                connectionSocket.close();

            }
        }
    }
}