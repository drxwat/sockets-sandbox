import java.util.EventObject;
import java.util.Objects;

/**
 * Created by drxwat on 21.03.15.
 */
public class MessageEvent extends EventObject {

    private String message;

    public MessageEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public MessageEvent(Objects source){
        this(source, "");
    }

    public MessageEvent(String message){
        this(null, message);
    }

    public MessageEvent(){
        this(null, "");
    }

    public String getMessage() {
        return message;
    }
}
