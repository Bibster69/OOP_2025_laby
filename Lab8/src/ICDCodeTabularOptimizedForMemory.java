import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ICDCodeTabularOptimizedForMemory implements ICDCodeTabular {
    private String filePath;

    public ICDCodeTabularOptimizedForMemory(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getDescription(String code) throws IndexOutOfBoundsException {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber < 88) continue;

                line = line.trim();
                if (line.startsWith(code + " ")) {
                    return line.substring(code.length()).trim();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Błąd odczytu pliku", e);
        }
        throw new IndexOutOfBoundsException("Kod nieznaleziony: " + code);
    }
}

