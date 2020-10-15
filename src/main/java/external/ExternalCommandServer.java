package external;

import com.google.gson.Gson;
import com.sun.corba.se.impl.presentation.rmi.ExceptionHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;

public class ExternalCommandServer {
    public static final String hostname = "192.168.0.89";
    public static final int port = 8080;

    private ServerSocket server;

    public void open(){
        try {
            server = new ServerSocket(port);

            String publicIpAddress = "unknown";

            URL url_name = new URL("http://bot.whatismyipaddress.com");

            BufferedReader sc =
                    new BufferedReader(new InputStreamReader(url_name.openStream()));

            // reads system IPAddress
            publicIpAddress = sc.readLine().trim();

            System.out.println(String.format("\tExternal Command location: %s:%d",publicIpAddress,port));

            ClientAcceptor acceptor = new ClientAcceptor();
            Thread th = new Thread(acceptor);
            th.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class ClientAcceptor implements Runnable{

        @Override
        public void run() {
            try{
                while (true){
                    Socket client = server.accept();
                    Scanner in = new Scanner(client.getInputStream());
                    String input = in.nextLine();
                    Gson gson = new Gson();
                    ExternalCommand command = gson.fromJson(input,ExternalCommand.class);
                    handle(command);
                    client.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        private void handle(ExternalCommand command){
            ExternalCommandHandler handler;
            switch (command.type.toLowerCase()){
                case "text":
                    handler = new TextCommandHandler();
                    break;
                case "embed":
                    handler = new EmbedCommandHandler();
                    break;
                default:
                    handler = new TextCommandHandler();
            }
            handler.handle(command);
        }
    }
}
