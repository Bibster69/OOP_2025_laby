import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
//        String filePath = "resources/zgony.csv"; // względna ścieżka do pliku


//        // Zad.1
//
//        ArrayList<DeathCauseStatistic> stats = new ArrayList<DeathCauseStatistic>();
//
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            // pomijamy 2 pierwsze wiersze, są bezużyteczne
//            br.readLine();
//            br.readLine();
//
//            String line;
//            while ((line = br.readLine()) != null) {
//                DeathCauseStatistic stat = DeathCauseStatistic.fromCsvLine(line);
//                stats.add(stat);
//                System.out.println(stat.getIcd10Code());
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Zad.2
//
//        System.out.println(stats.get(22).getAgeBracket(22));
//
//         Zad.3
//
//        DeathCauseStatisticList list = new DeathCauseStatisticList();
//        list.repopulate("resources/zgony.csv");
//
//        int age = 75;
//        int topN = 5;
//
//        System.out.println("Top " + topN + " najśmiertelniejszych chorób dla wieku " + age + ":");
//        List<DeathCauseStatistic> topDiseases = list.mostDeadlyDiseases(age, topN);
//        for (DeathCauseStatistic stat : topDiseases) {
//            System.out.println(stat.getIcd10Code());
//        }
//
        // Zad. 4

        ICDCodeTabular timeOptimized = new ICDCodeTabularOptimizedForTime("resources/icd10_descriptions.txt");
        ICDCodeTabular memoryOptimized = new ICDCodeTabularOptimizedForMemory("resources/icd10_descriptions.txt");

        String descTime = "N/A";
        String descMemory = "N/A";
        try {
            descTime = timeOptimized.getDescription("A00");
        } catch (IndexOutOfBoundsException e) {
            descTime = "(not found)";
        }
        System.out.println("  Description (time-optimized): " + descTime);

        try {
            descMemory = memoryOptimized.getDescription("A01.00");
        } catch (IndexOutOfBoundsException e) {
            descMemory = "(not found)";
        }
        System.out.println("  Description (memory-optimized): " + descMemory);

    }
    }

