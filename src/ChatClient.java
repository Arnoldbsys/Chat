import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class ChatClient implements Runnable {
    ChatServer server;
    Socket socket;
    OutputStream os;
    PrintStream out;
    public ChatClient(Socket socket, ChatServer server) throws IOException {
        this.socket = socket;
        os = socket.getOutputStream();
        out = new PrintStream(os);
        this.server = server;
    }

    public void run() {
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            // создаем удобные средства ввода и вывода
            Scanner in = new Scanner(is);
            // читаем из сети и пишем в сеть
            out.println("Welcome to chat!");

            String input = in.nextLine();
            while (!input.equals("bye")) {
                server.sendMessage(input);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}