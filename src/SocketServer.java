import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements Runnable {

    private int thisServerPort;

    /**
     * This constructor forces port to be passed in that is necessary for ServerSocket startup
     */

    public SocketServer(int iPort){ //this is a constructor.
        thisServerPort = iPort;
    }

    /**
    * This thread listens for connecting clients and receives messages.
     */

    public void run(){

        try(ServerSocket oServerSocket = new ServerSocket(thisServerPort)){
            System.out.print("Server is listening on port " + thisServerPort);

            while(true){


                //Waiting for client to connect to this server.
                Socket oSocket = oServerSocket.accept();
                System.out.println("[server] New client connected: " +
                        oSocket.getRemoteSocketAddress());

                // Set up a reader
                InputStream input = oSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                // Set up a writer
                OutputStream output = oSocket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                //Get message from client.
                String sReceivedMessage = reader.readLine();
                System.out.println("[server] message received from client: " + sReceivedMessage);

                // Send reply back to client
                writer.println("Server received your message: " + sReceivedMessage);
                writer.flush();
            }

        }
        catch(IOException ex){
            System.out.println("[server] Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

}
