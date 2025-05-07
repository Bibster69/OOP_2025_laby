import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ICDCodeTabularOptimizedForMemory implements ICDCodeTabular {
    private String path;

    public ICDCodeTabularOptimizedForMemory(String path) {
        this.path = path;
    }

    @Override
    public String getDescription(String code) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.path));
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;

                if (lineNumber < 88) {
                    continue;
                }

                line = line.trim();
                if (line.isEmpty() || !Character.isLetter(line.charAt(0))) {
                    continue;
                }

                int firstSpace = line.indexOf(' ');
                if (firstSpace == -1) {
                    continue;
                }

                String currentCode = line.substring(0, firstSpace).trim();
                String description = line.substring(firstSpace + 1).trim();

                if (currentCode.equals(code)) {
                    br.close();
                    return description;
                }
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        throw new IndexOutOfBoundsException("No desc. for code: " + code);
    }


}
