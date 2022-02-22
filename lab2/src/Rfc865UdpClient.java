import java.net.*;
import java.io.IOException;

public class Rfc865UdpClient {
    static final int QOTD_PORT = 17;
    static String SERVER_NAME = "";
    public static void main(String[] argv) {
        // 1. Open UDP socket
        DatagramSocket socket =  null;
        try {
            socket = new DatagramSocket();
            // hostname can be machine name or a textual representation of its IP address
            InetAddress serverAddress = InetAddress.getByName(SERVER_NAME);

            socket.connect(serverAddress, QOTD_PORT);
            System.out.println("UDP Client connected on port " + QOTD_PORT + " to server: " + serverAddress);
        } catch (Exception e) {
            // catching exception e not socket exception as getbyname will throw unknownhostexception
            e.printStackTrace();
            System.exit(-1);
        }
        
        try {
            // process quote to bye
            String requestContent = String.format("%s, %s, %s", "Lim Yan Qi", "SE1", SERVER_NAME);
            byte[] requestBuf = requestContent.getBytes("UTF-8");
            System.out.println("Content to send: " + requestContent);

            DatagramPacket request = new DatagramPacket(requestBuf, requestBuf.length);
            // 2. Send UDP request to server
            socket.send(request);

            // 3. Receive UDP reply from server
            byte[] buf = new byte[512];
            DatagramPacket reply = new DatagramPacket(buf, buf.length);
            System.out.println("Waiting for reply...");
            socket.receive(reply);

            /* Process the reply */
            String replyContent = new String(buf);
            System.out.println("Received reply: " + replyContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // socket.close();
        }
    }
       
}
