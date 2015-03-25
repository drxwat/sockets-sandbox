/**
 * Created by drxwat on 21.03.15.
 */
public class Launcher {

    private static final int PORT = 6666;

    public static void main(String[] args){
        if(args.length == 0){
            throw new Error("There is no one argument passed to program");
        }

        String command = args[0].toLowerCase().trim();

        if(args.length > 1 && args[1].toLowerCase().trim().equals("nio")){
            switch (command){
                case "server":
                    System.out.println("Вы запустили NIO сервер");
                    ServerNio serverNio = new ServerNio(PORT);
                    serverNio.init();
                    break;
                case "client-writer":
                    System.out.println("Вы запустили NIO клиент-писатель");
                    ClientNio clientNioWriter = new ClientNio(PORT);
                    clientNioWriter.init();
                    break;
                case "client-reader":
                    System.out.println("Вы запустили NIO клиент-читатель");
                    break;
                default:
                    throw new Error("Passed Invalid arguments");
            }
        }else{
            switch (command){
                case "server":
                    System.out.println("Вы запустили сервер");
                    Server server = new Server(PORT);
                    server.init();
                    break;
                case "client-writer":
                    System.out.println("Вы запустили клиент-писатель");
                    Client clientWriter = new Client(PORT);
                    clientWriter.init();
                    break;
                case "client-reader":
                    System.out.println("Вы запустили клиент-читатель");
                    Client clientReader = new Client(PORT, Client.TYPE_READER);
                    clientReader.init();
                    break;
                default:
                    throw new Error("Passed Invalid arguments");
            }
        }
    }
}
