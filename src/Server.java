import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * TODO: Необходимо сделать несколько вариантов работы с вводом выводом: IO NIO NIO2
 * Created by drxwat on 21.03.15.
 */
public class Server {

    private int port = 6666; // default port
    private ArrayList<MessageListener> messageListeners = new ArrayList<MessageListener>();

    public Server(int port){
        this.port = port;
    }

    public void init(){
        try(ServerSocket serverSocket = new ServerSocket(this.port)){

            System.out.println("Ожидание клиента на порту " + this.port + " ...");

            int clientNumber = 1;

            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("Клиент #"+ clientNumber + " присоединился!");
                ConnectionHandler connectionHandler = new ConnectionHandler(this, socket, clientNumber);
                addMessageListener(connectionHandler);
                clientNumber++;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addMessageListener(MessageListener messageListener){
        this.messageListeners.add(messageListener);
    }

    public ArrayList<MessageListener> getMessageListeners(){
        return this.messageListeners;
    }

    public void removeMessageListener(MessageListener messageListener){
        this.messageListeners.remove(messageListener);
    }

    public void fireMessage(ConnectionHandler source, String message){
        MessageEvent messageEvent = new MessageEvent(source, message);
        for(MessageListener listener :  this.getMessageListeners()){
            listener.messageReceived(messageEvent, source.getClientNumber());
        }
    }

}
