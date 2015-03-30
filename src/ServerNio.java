import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by drxwat on 25.03.15.
 */
public class ServerNio extends ServerAbstract {

    private static String clientChannel = "clientChannel";
    private static String serverChannel = "serverChannel";
    private static String channelType = "channelType";
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

            SelectionKey serverSocketChannelKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            Map<String, String> keyMap = new HashMap<String, String>();
            keyMap.put(channelType, serverChannel);
            serverSocketChannelKey.attach(keyMap);

            while (true){

                if(selector.select() == 0){
                    continue;
                }

                Set<SelectionKey> selectionKeys =  selector.selectedKeys();
                Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();

                while (selectionKeyIterator.hasNext()){

                    SelectionKey key = selectionKeyIterator.next();

                    Map<String, String> map = (Map<String, String>)key.attachment();

                    if(map.get(channelType).equals(serverChannel)){
                        // Устанавливаем соединение с клиентом
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        if(socketChannel == null){
                            continue;
                        }
                        socketChannel.configureBlocking(false);

                        System.out.println("Client is Connected");
                        SelectionKey socketChannelKey = socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

                        Map<String, String> socketChannelKeyMap = new HashMap<String, String>();
                        socketChannelKeyMap.put(channelType, clientChannel);
                        socketChannelKey.attach(socketChannelKeyMap);

                    }else{
                        // Работаем с клиентом
                        if(key.isReadable()){
                            System.out.println("Client is Readable");
                        }else if(key.isWritable()){
                            System.out.println("Client is Writable");

                            ByteBuffer buffer = ByteBuffer.allocate(100);
                            buffer.clear();
                            buffer.put(Charset.forName("UTF-8").encode("Hello from Max's Server!"));

                            buffer.flip();

                            SocketChannel channel = (SocketChannel)key.channel();
                            channel.write(buffer);
                        }
                    }

                    selectionKeyIterator.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptConnection(){

    }
}
