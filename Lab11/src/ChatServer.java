import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {

    private static final int PORT = 12345; // Port na którym będzie działał server
    // Zad. 1
    //private static Set<PrintWriter> clientWriters = new HashSet<>(); // Przechowuje output streamy wszystkich klientów
    //Zad. 3a
    private static Map<String, PrintWriter> clientWriters = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        System.out.println("Chat Server is running on port " + PORT);
        ExecutorService pool = Executors.newFixedThreadPool(50); // Pula wątków, do obsługiwania klientów

        try (ServerSocket listener = new ServerSocket(PORT)) {
            while (true) {
                pool.execute(new ClientHandler(listener.accept())); // Akceptujemy połączenie klientów
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            pool.shutdown(); // Wyłączamy pulę wątków
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String userName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Pytamu klienta o imię
                userName = in.readLine();
                while (userName == null || userName.trim().isEmpty()) {
                    out.println("Imie nie może być puste, podaj imie"); // Pytamy ponownie jeśli jest puste
                    userName = in.readLine();
                }
                System.out.println(userName + " Dołączył do czatu.");
                broadcastMessage("CZAT: " + userName + " Dołączył do czatu.");

                // Zad. 1
//                synchronized (clientWriters) {
//                    clientWriters.add(out);
//                }

                synchronized (clientWriters){
                    if(clientWriters.containsKey(userName)){
                        out.println("NAME_TAKEN " + userName + "Ten login jest zajęty, wybierz coś innego");
                        return;
                    }
                    clientWriters.put(userName, out);
                }

                // Odczytujemy wiadmość klienta i ją nadajemy
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(userName + ": " + message);
                    broadcastMessage("MSG: " + userName + ": " + message);
                }
            } catch (IOException e) {
                System.err.println("Error handling client " + userName + ": " + e.getMessage());
            } finally {
                if (userName != null) {
                    synchronized (clientWriters){
                        clientWriters.remove(userName);
                    }
                    System.out.println(userName + " wyszedł z czatu.");
                    broadcastMessage("CZAT: " + userName + " wyszedł z czatu.");
                }
                if (out != null) {
                    synchronized (clientWriters) {
                        clientWriters.remove(out); // Usuwamy writer klienta i się rozłączamy
                    }
                }
                try {
                    socket.close(); // Zamykamy socket
                } catch (IOException e) {
                    System.err.println("Error closing socket for " + userName + ": " + e.getMessage());
                }
            }
        }
    }

    // Wysyłamy wiadomość do wszystkich klientów
    private static void broadcastMessage(String message) {
        synchronized (clientWriters) {
            for (PrintWriter writer: clientWriters.values()) {
                writer.println(message);
            }
        }
    }
}