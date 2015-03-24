import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * TODO: Необходимо сделать несколько вариантов работы с вводом выводом: IO NIO NIO2
 * Created by drxwat on 21.03.15.
 */
public class Server extends ServerAbstract{

    public Server(int port) {
        super(port);
    }

    public void init(){
        try(ServerSocket serverSocket = new ServerSocket(this.port)){

            System.out.println("Ожидание клиента на порту " + this.port + " ...");

            int clientNumber = 1;

            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("Клиент #"+ clientNumber + " присоединился!");
                ConnectionHandlerNio connectionHandler = new ConnectionHandlerNio(this, socket, clientNumber);
                addMessageListener(connectionHandler);
                clientNumber++;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
