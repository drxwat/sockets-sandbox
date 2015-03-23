import java.util.ArrayList;

/**
 * Created by drxwat on 23.03.15.
 */
public abstract class ServerAbstract implements ServerInterface{

    protected int port = 6666; // default port
    private ArrayList<MessageListener> messageListeners = new ArrayList<MessageListener>();

    public ServerAbstract(int port){
        this.port = port;
    }

    @Override
    public void addMessageListener(MessageListener messageListener) {
        this.messageListeners.add(messageListener);
    }

    @Override
    public ArrayList<MessageListener> getMessageListeners() {
        return this.messageListeners;
    }

    @Override
    public void removeMessageListener(MessageListener messageListener) {
        this.messageListeners.remove(messageListener);
    }

    @Override
    public void fireMessage(MessageListener source, String message) {
        MessageEvent messageEvent = new MessageEvent(source, message);
        for(MessageListener listener :  this.getMessageListeners()){
            listener.messageReceived(messageEvent);
        }
    }
}
