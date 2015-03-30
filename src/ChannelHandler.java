/**
 * Created by drxwat on 30.03.15.
 */
public class ChannelHandler implements MessageListener {



    @Override
    public void messageReceived(MessageEvent messageEvent) {
        System.out.println(this + messageEvent.getMessage());
    }
}
