import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by drxwat on 25.03.15.
 * TODO: Для клиента читателя сделать возможность читать из файла, а для писателя писать в файл (в целях обучения)
 * TODO: TransferTo, TransferFrom использовать с файлами как дополнительный вариант
 */
public class ClientNio {

    private int port = 6666;

    public ClientNio(int port){
        this.port = port;
    }

    public void init(){
        try(SocketChannel socketChannel = SocketChannel.open()){
            Socket socket = socketChannel.socket();
            socket.connect(new InetSocketAddress(this.port));

            ByteBuffer buffer = ByteBuffer.allocate(256);

            buffer.clear();
            buffer.put("Hello".getBytes());
            socketChannel.write(buffer);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
