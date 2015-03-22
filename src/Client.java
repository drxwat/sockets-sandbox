import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by drxwat on 21.03.15.
 */
public class Client {

    private int port = 6666;
    private int type = this.TYPE_WRITER;
    public static final int TYPE_WRITER = 1;
    public static final int TYPE_READER = 2;


    public Client(int port){
        this.port = port;
    }

    public Client(int port, int type){
        this(port);
        if(type == TYPE_WRITER || type == TYPE_READER){
            this.type = type;
        }else{
            throw new Error("Wrong Client Type");
        }
    }

    public void init(){
        try{
            InetAddress inetAddress = InetAddress.getLocalHost();
            try(Socket socket = new Socket(inetAddress, this.port)){
                System.out.println("Подключение успешно выполнено!");

                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                DataInputStream dataInputStream = new DataInputStream(inputStream);
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                String line = null;

                BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

                while (true){
                    if(this.type == TYPE_WRITER){
                        System.out.println("Введите сообщение: ");
                        line = keyboard.readLine();
                        dataOutputStream.writeUTF(line);
                        dataOutputStream.flush();
                        if(line.toLowerCase().equals("exit")){
                            socket.shutdownInput();
                            socket.shutdownOutput();
                            socket.close();
                            break;
                        }
                    }else{
                        line = dataInputStream.readUTF();
                        System.out.println(line);
                    }
                }
                System.out.println("Сессия завершена. Спасибо!");

            }catch (IOException e){
                e.printStackTrace();
            }

        }catch (UnknownHostException e){
            e.printStackTrace();
        }



    }
}
