import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

/**
 * Created by drxwat on 23.03.15.
 */
public class ConnectionHandlerNio implements Runnable, MessageListener{


    private ServerAbstract server = null;
    private Socket socket = null;
    private int clientNumber = 1;
    private ByteBuffer buffer = null;

    public int getClientNumber() {
        return clientNumber;
    }

    public ConnectionHandlerNio(ServerAbstract server, Socket socket, int clientNumber){
        this.server = server;
        this.socket = socket;
        this.clientNumber = clientNumber;
        this.buffer = ByteBuffer.allocate(256);

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try(SocketChannel channel = socket.getChannel()){

            Charset charset = Charset.forName("UTF-8");
            ByteBuffer encodedByteBuffer = charset.encode("Hello from a Server!");
            buffer.clear();
            buffer.put(encodedByteBuffer);

            buffer.flip();

            while (buffer.hasRemaining()){
                channel.write(buffer);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void messageReceived(MessageEvent messageEvent) {

        try(SocketChannel socketChannel = this.socket.getChannel()){
            buffer.clear();
            buffer.put(messageEvent.getMessage().getBytes());
            buffer.flip();

            while (buffer.hasRemaining()){
                socketChannel.write(buffer);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
