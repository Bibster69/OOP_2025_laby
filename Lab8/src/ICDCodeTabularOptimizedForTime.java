import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ICDCodeTabularOptimizedForTime implements ICDCodeTabular {
    private Map<String, String> codeToDesc;

    public ICDCodeTabularOptimizedForTime(String path) throws IOException {
        this.codeToDesc = new HashMap<String, String>();
        loadFromFile(path);
    }

    private void loadFromFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        int lineNumber = 0;


        while ((line  = reader.readLine()) != null){
            lineNumber++;

            if (lineNumber < 88) {
                continue;
            }

            line = line.trim();

            // Jeśli lina jest pusta, lub nie zaczyna się od literki, kodu musi zaczynać się od literki, idziemy dalej
            if (line.isEmpty() || !Character.isLetter(line.charAt(0))) {
                continue;
            }

            int firstSpace = line.indexOf(' ');
            if (firstSpace == -1) {
                continue;
            }

            String code = line.substring(0, firstSpace).trim();
            String desc = line.substring(firstSpace + 1).trim();


            // I znowu sprawdzamy czy kod zaczyna sie od Literki i dwóch cyferek
            if (code.length() >= 3 && Character.isLetter(code.charAt(0)) && Character.isDigit(code.charAt(1)) && Character.isDigit(code.charAt(2))) {
                this.codeToDesc.put(code, desc);
            }
        }
        reader.close();
    }

    @Override
    public String getDescription(String code) throws IndexOutOfBoundsException {
        if (!this.codeToDesc.containsKey(code)) {
            throw new IndexOutOfBoundsException("Nie znaleziono opisu dla kodu: " + code);
        }
        return this.codeToDesc.get(code);
    }
}

