import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by drxwat on 30.03.15.
 */
public class ChannelHandler implements MessageListener {

    public static int TYPE_SERVER = 1;
    public static int TYPE_CLIENT = 2;

    private int channelType = TYPE_SERVER;

    private LinkedHashSet<ByteBuffer> messageByteBuffers = new LinkedHashSet<ByteBuffer>();

    public ChannelHandler(int channelType){
        if(channelType == TYPE_SERVER || channelType == TYPE_CLIENT){
            this.channelType = channelType;
        }
    }

    public boolean isServer(){
        return this.channelType == TYPE_SERVER;
    }

    public boolean isClient(){
        return this.channelType == TYPE_CLIENT;
    }

    public LinkedHashSet<ByteBuffer> getMessageByteBuffers() {
        return messageByteBuffers;
    }

    public boolean hasMessage(){
        return !this.getMessageByteBuffers().isEmpty();
    }

    public ByteBuffer getMessage() throws NoSuchElementException{
        LinkedHashSet<ByteBuffer> byteBuffers = this.getMessageByteBuffers();
        Iterator<ByteBuffer> byteBufferIterator = byteBuffers.iterator();
        if(byteBufferIterator.hasNext()){
            ByteBuffer nextBuffer = byteBufferIterator.next();
            byteBufferIterator.remove();
            return nextBuffer;
        }else{
            throw new NoSuchElementException("There is no one message here");
        }
    }

    @Override
    public void messageReceived(MessageEvent messageEvent) {
        byte[] bytes = messageEvent.getMessage().getBytes(Charset.defaultCharset());
        this.getMessageByteBuffers().add(ByteBuffer.wrap(bytes));
    }
}
