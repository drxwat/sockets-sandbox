import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Created by drxwat on 25.03.15.
 */
public class ClientNio {

    private int port = 6666;

    ClientNio(int port){
        this.port = port;
    }

    public void init(){
        try(
                SocketChannel   channel = SocketChannel.open();
                Socket          socket  = channel.socket()
        ) {

            socket.connect(new InetSocketAddress(this.port));
            System.out.println("Connected!");

//            Charset charset = Charset.forName("UTF-8");
            ByteBuffer buffer = ByteBuffer.allocate(256);
            int bytesRead = channel.read(buffer);

            if(bytesRead != -1){
//               CharBuffer charBuffer = charset.decode(buffer);
//                System.out.println(charBuffer.length());
                while (buffer.hasRemaining()){
                    System.out.print(buffer.getChar());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
