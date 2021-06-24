
package filetransferringtcp;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println(args[0]);
if(args[0].equals("client")){
    Socket sock = new Socket("localhost", 3456);
        String FileName = args[1] ;
        File MyFile = new File(FileName);
        int FileSize = (int) MyFile.length();
        OutputStream os =sock.getOutputStream();
        PrintWriter pr = new PrintWriter(sock.getOutputStream(), true);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(MyFile));
        Scanner in = new Scanner(sock.getInputStream());
        pr.println(FileName);
        pr.println(FileSize);
        byte[] filebyte = new byte[FileSize];
        bis.read(filebyte, 0, filebyte.length);
        os.write(filebyte, 0, filebyte.length);
        System.out.println(in.nextLine());
        os.flush();
        sock.close();
}        else{
        ServerSocket servsock = new ServerSocket(3456);
        Socket sock = servsock.accept();
        Scanner in = new Scanner(sock.getInputStream());
        InputStream is = sock.getInputStream();
        PrintWriter pr = new PrintWriter(sock.getOutputStream(), true);
        String FileName = in.nextLine();
        int FileSize = in.nextInt();
        FileOutputStream fos = new FileOutputStream(args[1]);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        byte[] filebyte = new byte[FileSize];
        int file = is.read(filebyte, 0, filebyte.length);
        bos.write(filebyte, 0, file);   
        System.out.println("Incoming File: " + FileName);
        System.out.println("Size: " + FileSize + "Byte");
        if(FileSize == file)System.out.println("File is verified");
        else System.out.println("File is corrupted. File Recieved " + file + " Byte");
        pr.println("File Recieved SUccessfully.");
        bos.close();
        sock.close();
    }
}}