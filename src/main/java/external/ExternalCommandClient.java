package external;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.PrintWriter;
import java.net.Socket;

public class ExternalCommandClient {
    public void send(ExternalCommand command){
        try {
            Gson gson = new Gson();
            String json = gson.toJson(command);
            Socket socket = new Socket("localhost",8080);
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println(json);
            pw.flush();
            pw.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
//        ExternalCommandServer server = new ExternalCommandServer(new ExternalCommandHandler());
//        server.open();

        ExternalCommand command = new ExternalCommand("text","524035064523259924","604400677451726888","Hello");
        ExternalCommandClient client = new ExternalCommandClient();
        client.send(command);
    }
}
