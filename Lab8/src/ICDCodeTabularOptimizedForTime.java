import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ICDCodeTabularOptimizedForTime implements ICDCodeTabular {
    private Map<String, String> codeToDescription = new HashMap<>();

    public ICDCodeTabularOptimizedForTime(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber < 88) continue;

                line = line.trim();

                /*
                Wyjaśnienie regexa:
                ^ - początek linii
                [A-Z] - pojedyncza duża litereka
                [0-9]{2} - dwie cyferki od 0-9
                (\\.[0-9]+)? - część opcjonalna, czyli kropka a po niej jedna lub lika cyferek
                \\s+ - jedna lub więcej spacji
                .+ - jeden luv więcej znaków
                $ - koniec linii

                wystarczy złączyć to wszystko w jedno i mamy regexa wykrywjącego nasze kody IDC-10
                 */

                if (line.matches("[A-Z][0-9]{2}(\\.[0-9]+)?\\s+.+")) {
                    String[] parts = line.split("\\s+", 2);
                    if (parts.length == 2) {
                        this.codeToDescription.put(parts[0], parts[1]);
                    }
                }
            }
        }
    }

    @Override
    public String getDescription(String code) {
        if (!this.codeToDescription.containsKey(code)) {
            throw new IndexOutOfBoundsException("Kod nieznaleziony: " + code);
        }
        return this.codeToDescription.get(code);
    }
}

