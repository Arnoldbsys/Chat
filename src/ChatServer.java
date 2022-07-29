import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    List <ChatClient> chatUsers = new ArrayList<>();
    ServerSocket server;
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(1235);
        ChatServer chat = new ChatServer();
        chat.startServer();
    }
    public void startServer() throws IOException {
        // создаем серверный сокет на порту 1234
        server = new ServerSocket(1234);

        while(true) {
            System.out.println("Waiting...");
            // ждем клиента из сети
            Socket socket = server.accept();
            System.out.println("Client connected!");
            // создаем клиента на своей стороне
            ChatClient client = new ChatClient(socket, this);
            chatUsers.add(client);
            // запускаем поток
            Thread thread = new Thread(client);
            thread.start();
        }

    }

    public void sendMessage(String msg){
        for (ChatClient client: chatUsers){
            client.out.println(msg);
        }
    }
}
