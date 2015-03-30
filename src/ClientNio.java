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
        ) {
            // Устанавливаем соединение
            channel.connect(new InetSocketAddress(this.port));

            System.out.println("Подключение прошло успешно!");
            System.out.println();

            Charset charset = Charset.forName("UTF-8");
            ByteBuffer buffer = ByteBuffer.allocate(10);

            int bytesRead = 0;
            while (bytesRead != -1) {
                bytesRead = channel.read(buffer);
                if (bytesRead != -1) {

                    buffer.flip();

                    String message = new String(ByteBuffer.allocate(bytesRead).put(buffer).array(), charset);
                    System.out.print(message);

                    buffer.clear();

                }
            }

            bytesRead = 0;
            while (bytesRead != -1){
                bytesRead = channel.read(buffer);
                if(bytesRead != -1){

                    buffer.flip();

                    String message = new String(ByteBuffer.allocate(bytesRead).put(buffer).array(), charset);
                    System.out.print(message);

                    buffer.clear();

                }
            }




        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
