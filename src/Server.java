import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TODO: Реализовать NIO с использованием селекторов\
 * TODO: Реализовать запись переписки в файл через NIO & IO
 * TODO: Дополнить логированием весь процесс (изучить логи)
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
                ConnectionHandler connectionHandler = new ConnectionHandler(this, socket, clientNumber);
                addMessageListener(connectionHandler);
                clientNumber++;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
