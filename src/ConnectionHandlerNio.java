import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by drxwat on 23.03.15.
 */
public class ConnectionHandlerNio implements Runnable, MessageListener{


    private ServerNio server = null;
    private Socket socket = null;
    private int clientNumber = 1;
    private ByteBuffer buffer = null;
    private SocketChannel channel = null;

    public int getClientNumber() {
        return clientNumber;
    }

    public ConnectionHandlerNio(ServerNio server, Socket socket, int clientNumber){
        this.server = server;
        this.socket = socket;
        this.channel = this.socket.getChannel();
        this.clientNumber = clientNumber;
        this.buffer = ByteBuffer.allocate(256);

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        buffer.flip();
        try{
            int byteRead = 0;
            while (byteRead != -1){
                channel.read(buffer);
            }
            System.out.println(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageReceived(MessageEvent messageEvent) {
        System.out.println("Message: " + messageEvent.getMessage());
    }
}
