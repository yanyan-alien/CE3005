import java.net.*;
// import java.io.IOException;

public class Rfc865UdpServer {
    static final int QOTD_PORT = 17;
    static final String QUOTE = "The greatest glory in living lies not in never falling, but in rising every time we fall. -Nelson Mandela";
    public static void main(String[] argv) {
        //
        // 1. Open UDP socket at well-known port
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(QOTD_PORT);
            System.out.println("UDP Port listening on port " + QOTD_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        while (true) {
            try {
                // 2. Listen for UDP request from client: receiving packet
                // initialise byte array to be received
                byte[] buf = new byte[512];
                DatagramPacket request = new DatagramPacket(buf, buf.length);
                System.out.println("Waiting for request...");
                socket.receive(request);
                
                // convert byte message to string, process the request
                String requestContent = new String(buf);
                System.out.println("Received request: " + requestContent);

                // get address of client
                // there are 2 different types of constructing a datagram to send out
                // ipaddress method would require to get client using getport();
                // InetAddress clientAddress = request.getAddress(); 
                SocketAddress clientSocketAddress = request.getSocketAddress();

                // process quote to byte array
                byte[] quoteBuf = QUOTE.getBytes("UTF-8");

                // 3. Send UDP reply to client
                DatagramPacket reply = new DatagramPacket(quoteBuf, quoteBuf.length, clientSocketAddress);
                socket.send(reply);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // will result in a loop (while) where socket is close but still trying to receive request
                // java.net.SocketException socket is closed
                socket.close();
            }
        }
    }
   }
   