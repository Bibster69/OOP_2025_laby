import java.io.*;

public class PlantUMLRunner {

    private static String pathToJar;


    public static void setJarPath(String path) {
        pathToJar = path;
    }
    public static void generateDiagram(String umlText, String outputDir, String outputFilename) throws IOException, InterruptedException {
        if (pathToJar == null || pathToJar.isEmpty()) {
            throw new IllegalStateException("Ścieżka do pliku .jar nie została ustawiona.");
        }

        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File umlFile = new File(dir, outputFilename + ".puml");
        try (FileWriter writer = new FileWriter(umlFile)) {
            writer.write(umlText);
        }

//        ProcessBuilder builder = new ProcessBuilder(
//                "java", "-jar", pathToJar, tempUmlFile.getAbsolutePath(), "-o", "."
//        );
//        builder.directory(dir);
//        builder.redirectErrorStream(true);
//        Process process = builder.start();
//
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//        }
//
//        int exitCode = process.waitFor();
//        if (exitCode != 0) {
//            throw new RuntimeException("Błąd podczas generowania diagramu PlantUML.");
//        }
//
//        tempUmlFile.delete();
    }
}
