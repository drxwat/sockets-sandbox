import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

/**
 * Created by drxwat on 25.03.15.
 */
public class ClientNio {

    private int port = 6666;

    ClientNio(int port){
        this.port = port;
    }

    public void init(){
        try(Socket socket = SocketChannel.open().socket()) {
            socket.bind(new InetSocketAddress(this.port));

            System.out.println("Connected!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
