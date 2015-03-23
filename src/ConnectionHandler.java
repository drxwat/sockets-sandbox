import java.io.*;
import java.net.Socket;

/**
 * Created by drxwat on 21.03.15.
 */
public class ConnectionHandler implements Runnable, MessageListener{

    private Server server = null;
    private Socket socket = null;
    private int clientNumber = 1;
    private DataOutputStream dataOutputStream = null;
    private DataInputStream dataInputStream = null;

    public int getClientNumber() {
        return clientNumber;
    }

    public ConnectionHandler(Server server, Socket socket, int clientNumber){
        this.server = server;
        this.socket = socket;
        this.clientNumber = clientNumber;

        try{
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            this.dataInputStream = new DataInputStream(inputStream);
            this.dataOutputStream = new DataOutputStream(outputStream);

        }catch (IOException e){
            e.printStackTrace();
        }

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
       try{

           server.fireMessage(this, "Клиент #" + this.clientNumber + " присоединился к беседе");
           String line = null;

            while (true){
                line = this.dataInputStream.readUTF(); // Событие!!!
                if(line.toLowerCase().equals("exit")){
                    break;
                }
                server.fireMessage(this, "#" + this.clientNumber + " " + line);
            }
           socket.shutdownOutput();
           socket.shutdownInput();
           socket.close();
           server.removeMessageListener(this);
           server.fireMessage(this, "Клиент #" + this.clientNumber + " покинул беседу");

       }catch (IOException e){
           e.printStackTrace();
       }

    }

    @Override
    public void messageReceived(MessageEvent messageEvent) {
        try{
            this.dataOutputStream.writeUTF(messageEvent.getMessage());
            this.dataOutputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
