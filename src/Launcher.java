/**
 * Created by drxwat on 21.03.15.
 */
public class Launcher {

    private static final int PORT = 6666;

    public static void main(String[] args){
        if(args.length == 0){
            throw new Error("There is no one argument passed to program");
        }

        if(args[0].toLowerCase().equals("server")){

            System.out.println("Вы запустили сервер");
            Server server = new Server(PORT);
            server.init();

        }else if(args[0].toLowerCase().equals("client-writer")){

            System.out.println("Вы запустили клиент-писатель");
            Client client = new Client(PORT);
            client.init();

        }else if(args[0].toLowerCase().equals("client-reader")){

            System.out.println("Вы запустили клиент-читатель");
            Client client = new Client(PORT, Client.TYPE_READER);
            client.init();

        }else{
            throw new Error("Passed Invalid arguments");
        }
    }
}
