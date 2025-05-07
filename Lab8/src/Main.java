import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String filePath = "resources/zgony.csv"; // względna ścieżka do pliku


        // Zad.1

        ArrayList<DeathCauseStatistic> stats = new ArrayList<DeathCauseStatistic>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // pomijamy 2 pierwsze wiersze, są bezużyteczne
            br.readLine();
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                DeathCauseStatistic stat = DeathCauseStatistic.fromCsvLine(line);
                stats.add(stat);
                System.out.println(stat.getIcd10Code());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Zad.2

        System.out.println(stats.get(12).getAgeBracket(12));
    }
}
