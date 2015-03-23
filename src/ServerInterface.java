import java.util.ArrayList;

/**
 * TODO: Дополнить интерфейс и создать абстрактник с поддержкой событий
 * TODO: Создать интерфейст и абстрактник для Клиентов (IO, NOI, NIO2)
 * Created by drxwat on 23.03.15.
 */
public interface ServerInterface {

    void init();

    void addMessageListener(MessageListener messageListener);

    ArrayList<MessageListener> getMessageListeners();

    void removeMessageListener(MessageListener messageListener);

    void fireMessage(MessageListener source, String message);
}
