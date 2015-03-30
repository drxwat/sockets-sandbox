import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 *
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

            channel.connect(new InetSocketAddress(this.port));
            System.out.println("Connected!");

            Charset charset = Charset.forName("UTF-8");
            ByteBuffer buffer = ByteBuffer.allocate(5);
            boolean inProgress = false;
            int bytesRead = 0;
            while (true){
                bytesRead = channel.read(buffer);

                if(bytesRead != -1){
                    inProgress = true;
                    // В режиме чтения
                    buffer.flip();

                    byte[] message = new byte[bytesRead];

                    int i = 0;
                    while (buffer.hasRemaining()){
                        message[i] = buffer.get();
                        i++;
                    }
                    System.out.print(new String(message, charset));

                    buffer.clear();
                }else{
                    if(inProgress){
                        // Вставим пробел между пачками
                        System.out.println();
                    }
                    inProgress = false;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
