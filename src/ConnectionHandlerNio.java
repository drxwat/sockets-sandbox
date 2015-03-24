import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by drxwat on 23.03.15.
 */
public class ConnectionHandlerNio implements Runnable, MessageListener{


    private Server server = null;
    private Socket socket = null;
    private int clientNumber = 1;
    private ByteBuffer buffer = null;

    public int getClientNumber() {
        return clientNumber;
    }

    public ConnectionHandlerNio(Server server, Socket socket, int clientNumber){
        this.server = server;
        this.socket = socket;
        this.clientNumber = clientNumber;
        this.buffer = ByteBuffer.allocate(256);

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {


        try(SocketChannel socketChannel = socket.getChannel()){
            //server.fireMessage(this, "Клиент #" + this.clientNumber + " присоединился к беседе");
            String line = null;


            ByteBuffer buffer = ByteBuffer.allocate(256);
            int byteRead = socketChannel.read(buffer);
            while (byteRead != 1){
                buffer.flip();

                while (buffer.hasRemaining()){
                    //read
                }

                buffer.clear();
                byteRead = socketChannel.read(buffer);

            }
/*

            do{
                byteRead = socketChannel.read(buffer);
                if(byteRead != -1){
                    line = new String(buffer.array());
                    server.fireMessage(this, "#" + this.clientNumber + " " + line);
                }
            }while (byteRead == -1);
            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
            server.removeMessageListener(this);
            server.fireMessage(this, "Клиент #" + this.clientNumber + " покинул беседу");
*/

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
