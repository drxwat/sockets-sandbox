import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by drxwat on 25.03.15.
 */
public class ServerNio extends ServerAbstract {

    public ServerNio(int port) {
        super(port);
    }

    @Override
    public void init() {
        try(ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()){
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(this.port));

            int clientNumber = 1;
            while (true){
                System.out.println("Ожидаю клиента");
                Socket socket = serverSocket.accept();
                System.out.println("Клиент присоединился");
                // Передаем управление обрабтчику
                ConnectionHandlerNio connectionHandlerNio = new ConnectionHandlerNio(this, socket, clientNumber);
                this.addMessageListener(connectionHandlerNio);
                clientNumber++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
