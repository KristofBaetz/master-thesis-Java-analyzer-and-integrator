package org.baetz.christoph.databaseGraphLoader;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.baetz.christoph.databaseGraphLoader.rdfLoader.step1MoveAndDecompress.DecompressAndMoveFile;
import org.baetz.christoph.databaseGraphLoader.rdfLoader.step2Split.StreamingN3Splitter;
import org.baetz.christoph.databaseGraphLoader.rdfLoader.step3Querying.QueryExecuter;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {


    /**
     *
     * @param args
     *
     * Methode, die den Prozess der Datenintegration der .n3-Dateien startet
     */
    public static void main(String[] args) {

        // Setze Root Logger auf den Level Info
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);

        // Setze Jena Logger auf INFO
        ch.qos.logback.classic.Logger jenaLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.apache.jena");
        jenaLogger.setLevel(Level.INFO);

        String inputFolder = "D:/Daten/Twitter-Daten Gepackt";

        // Erstelle das Objekt Path aus dem Pfad des Ordners, indem später die dekomprimierten Dateien zwischengelagert werden sollen
        String decompressedFileFolderString = "./n3-file-decompressed/";
        Path decompressedFileFolder = Paths.get(decompressedFileFolderString);

        // Erstelle das Objekt Path aus dem Pfad des Ordners, indem später Teile der dekomprimierten .n3-Dateien zwischengelagert werden sollen
        String splitOutputFolder = "./split_output/";
        Path splitOutputPath = Paths.get(splitOutputFolder);

        List<Path> fileList = getFilesOfDirectory(inputFolder);

        // Um sicherzustellen, dass keine Dateien von einem vorherigen Lauf in einer der Dateien sind, werden zunächst diese Ordner zunächst bereinigt
        cleanUpFolder(decompressedFileFolder);
        cleanUpFolder(splitOutputPath);

        for (Path inputPath : fileList) {

            // Erstelle Ordner zum Zwischenspeichern der Dateien
            createDirectory(decompressedFileFolder);
            createDirectory(splitOutputPath);

            System.out.println("Load: " + inputPath.toString());

            // Erstelle den vollen Pfad fuer die dekomprimierte Output-Datei
            String decompressedFilePath = decompressedFileFolderString
                                      + inputPath.getFileName().toString().replace(".gz", "");

            // Lade und dekomprimiere eine Monatsdatei der Twitter-ARchive
            DecompressAndMoveFile decompressAndMoveFile = new DecompressAndMoveFile();
            decompressAndMoveFile.execute(inputPath.toString(),decompressedFilePath);

            // Teile die N3-Datei in einzelne Dateien, die jeweils 1.000.000 Zeilen lang sind
            StreamingN3Splitter streamingN3Splitter = new StreamingN3Splitter();
            streamingN3Splitter.execute(decompressedFilePath);

            // Loesche die dekomprimierte Datei nach der Aufteilung, um Platz auf der Festplatte zu sparen
            cleanUpFolder(decompressedFileFolder);

            // Liste alle aufgeteilten Dateien auf
            List<Path> splittedFiles = getFilesOfDirectory(splitOutputFolder);

            // Extrahiere nun die relevanten Tweet-Daten aus jedem einzelnen der
            for (Path splittedFile : splittedFiles) {

                QueryExecuter queryExecuter = new QueryExecuter();
                queryExecuter.execute(splitOutputFolder + splittedFile.getFileName().toString());

            }

            // lösche alle kleineren .n3-Dateien, um den Platz auf der Festplatte wieder freizugeben
            cleanUpFolder(Paths.get(splitOutputFolder));
        }
    }

    /**
     *
     * @param folder
     *
     * Try-Catch-Wrapper fuer Methode deleteFilesInDirectoryRecursively
     */
    private static void cleanUpFolder(Path folder){

        try {
            deleteFilesInDirectoryRecursively(folder);
            System.out.println("Cleaned up '" + folder + "' successfully!");
        } catch (IOException e) {
            System.err.println("An error occurred while deleting the files: " + e.getMessage());
        }
    }

    /**
     *
     * @param dirPath
     * @return
     *
     * Try-Catch-Wrapper für Methode "listFilesInDirectory
     */
    private static List<Path> getFilesOfDirectory (String dirPath){

        List<Path> fileList = null;

        try {
            fileList = listFilesInDirectory(Paths.get(dirPath));
        } catch (IOException e) {
            System.err.println("An error occurred while listing the files: " + e.getMessage());
            System.exit(1);
        }

        return fileList;

    }

    /**
     *
     * @param dirPath
     * @return
     * @throws IOException
     *
     * Methode, die alle Dateien in einem Ordner in einer Liste mit Objekten vom Typ Path zurueckgibt.
     */
    private static List<Path> listFilesInDirectory(Path dirPath) throws IOException {
        List<Path> fileList = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    fileList.add(entry);
                }
            }
        }

        return fileList;
    }

    /**
     *
     * @param dirPath
     * @throws IOException
     *
     * Methode, die einen Ordner nach und nach durchgeht und alle Dateien, enthaltenden Ordner und schliesslich
     * den angegebenen Ordner selbst loescht
     */
    public static void deleteFilesInDirectoryRecursively(Path dirPath) throws IOException {
        if (Files.isDirectory(dirPath)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
                for (Path entry : stream) {
                    deleteFilesInDirectoryRecursively(entry);
                }
            }
        }
        Files.delete(dirPath);
        System.out.println("Deleted: " + dirPath);
    }

    /**
     *
     * @param dirPath
     *
     * Methode zur Erstellung eines Ordners
     */
    private static void createDirectory(Path dirPath)  {
        if (!Files.exists(dirPath)) {
            try {
                Files.createDirectory(dirPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Created directory: " + dirPath);
        } else {
            System.out.println("Directory already exists: " + dirPath);
        }
    }
}
