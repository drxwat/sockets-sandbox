import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by drxwat on 25.03.15.
 */
public class ServerNio extends ServerAbstract {

    private int maxClients = 150;

    public ServerNio(int port) {
        super(port);
    }

    public ServerNio(int port, int maxClients){
        super(port);
        this.maxClients = maxClients;
    }

    @Override
    public void init() {
        try(
                Selector selector = Selector.open();
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()
        ){
            serverSocketChannel.bind(new InetSocketAddress(this.port));
            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            int selectedChannels = 0;
            while (true){
                selectedChannels = selector.select();
                if(selectedChannels == 0){
                    continue;
                }



            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
