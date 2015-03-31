import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

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

            SelectionKey serverSocketChannelKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            ChannelHandler serverChannelHandler = new ChannelHandler(ChannelHandler.TYPE_SERVER);
            serverSocketChannelKey.attach(serverChannelHandler);

            while (true){

                if(selector.select() == 0){
                    continue;
                }

                Set<SelectionKey> selectionKeys =  selector.selectedKeys();
                Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();

                while (selectionKeyIterator.hasNext()){

                    SelectionKey key = selectionKeyIterator.next();

                    ChannelHandler channelHandler = (ChannelHandler)key.attachment();

                    if(channelHandler.isServer()){
                        // Устанавливаем соединение с клиентом
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        if(socketChannel == null){
                            continue;
                        }
                        socketChannel.configureBlocking(false);

                        System.out.println("Client is Connected");
                        SelectionKey socketChannelKey = socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

                        ChannelHandler clientChannelHandler1 = new ChannelHandler(ChannelHandler.TYPE_CLIENT);
                        socketChannelKey.attach(clientChannelHandler1);
                        this.addMessageListener(clientChannelHandler1);

                    }else{
                        // Работаем с клиентом
                        if(key.isReadable()){
                            System.out.println("Client is Readable");

                            ByteBuffer readBuffer = ByteBuffer.allocate(100);
                            readBuffer.clear();
                            readBuffer.flip();

                            SocketChannel clientReaderSocketChannel = (SocketChannel)key.channel();
                            int bytesRead = clientReaderSocketChannel.read(readBuffer);

                            byte[] readMessage = new byte[bytesRead];
                            if(bytesRead != -1){
                                int i = 0;
                                while (bytesRead != -1){
                                    readMessage[i] = readBuffer.get();
                                }
                                this.fireMessage((MessageListener)key.attachment(), new String(readMessage, Charset.defaultCharset()));
                            }

                        }else if(key.isWritable()){
                            System.out.println("Client is Writable");

                            ByteBuffer buffer = ByteBuffer.allocate(100);
                            buffer.clear();
                            ChannelHandler writerChannelHandler1 = (ChannelHandler)key.attachment();
                            if(writerChannelHandler1.hasMessage()){

                                buffer.put(Charset.forName("UTF-8").encode(new String(writerChannelHandler1.getMessage().array(), Charset.defaultCharset())));
                                buffer.flip();

                                SocketChannel channel = (SocketChannel)key.channel();
                                channel.write(buffer);

                            }
                        }
                    }

                    selectionKeyIterator.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
