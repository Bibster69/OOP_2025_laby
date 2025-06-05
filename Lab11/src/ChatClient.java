import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {

    private static final String SERVER_ADDRESS = "localhost"; // Zmień, jeśli serwer jest na innej maszynie
    private static final int SERVER_PORT = 12345; // Port, na którym nasłuchuje serwer

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); // Nawiąż połączenie z serwerem
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Strumień do odczytu z serwera
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // Strumień do zapisu na serwer (true = auto-flush)
             Scanner scanner = new Scanner(System.in)) { // Obiekt do odczytu danych ze standardowego wejścia

            System.out.println("Połączono z serwerem czatu.");

            String userName;
            while (true) {
                System.out.println("Podaj swoję imie.");
                userName = scanner.nextLine();
                if (userName == null || userName.trim().isEmpty()) {
                    System.out.println("Imie nie może być puste");
                } else {
                    out.println(userName);
                    break;
                }
            }

            // Wątek do odczytywania wiadomości z serwera
            // Ten wątek działa niezależnie, aby odbierać wiadomości od serwera
            // w dowolnym momencie, nawet gdy użytkownik pisze swoją wiadomość.
            new Thread(() -> {
                String line;
                try {
                    while ((line = in.readLine()) != null) {
                        if (line.startsWith("NAME_TAKEN")){
                            System.out.println(line.substring("Ten login jest zajęty, wybierz coś innego".length()).trim());
                            System.out.println("Rozłączono z serwerem z powodu zajętego loginu. Uruchom klienta ponownie.");
                            System.exit(0);
                        }
                        else if (line.startsWith("CZAT:")) {
                            System.out.println(line.substring("CZAT:".length()).trim());
                        } else if (line.startsWith("MSG:")) {
                            // Zad. 1
                            // System.out.println(line); // Wyświetl odebraną wiadomość na standardowym wyjściu
                            System.out.println(line.substring("WIADOMOSC:".length()).trim());
                        } else {
                            System.out.println(line);
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Utracono połączenie z serwerem: " + e.getMessage());
                }
            }).start(); // Uruchom wątek

            // Główny wątek do wysyłania wiadomości do serwera
            // Ten wątek odczytuje dane ze standardowego wejścia użytkownika
            // i wysyła je do serwera.
//            String userInput;
//            while (scanner.hasNextLine()) { // Dopóki są dane do odczytania ze standardowego wejścia
//                userInput = scanner.nextLine(); // Odczytaj linię wprowadzoną przez użytkownika
//                out.println(userInput); // Wyślij ją do serwera
//            }

            String userInput;
            while (scanner.hasNextLine()) {
                userInput = scanner.nextLine();
                out.println(userInput); // Wysyłaj wprowadzone przez użytkownika dane do serwera
            }

        } catch (IOException e) {
            System.err.println("Nie można połączyć się z serwerem: " + e.getMessage());
        }
    }
}