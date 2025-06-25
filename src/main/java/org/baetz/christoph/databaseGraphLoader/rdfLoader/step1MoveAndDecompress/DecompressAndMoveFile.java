package org.baetz.christoph.databaseGraphLoader.rdfLoader.step1MoveAndDecompress;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.zip.GZIPInputStream;

public class DecompressAndMoveFile {

    /**
     *
     * @param inputPathString
     * @param outputPathString
     *
     * Diese Methode verwendet einen inputPathString, um eine komprimierte Quelldatei zu bestimmen, die im
     * Laufe der Methode entpackt werden soll und anschließend an den Zielort geschrieben werden soll.
     */
    public void execute(String inputPathString, String outputPathString) {

        // Fuer die Zeitmessung dieses Schrittes wird hier die Startzeit bestimmt
        Instant start = Instant.now();

        Path inputPath = Paths.get(inputPathString);
        Path outputPath = Paths.get(outputPathString);

        try (InputStream fis = Files.newInputStream(inputPath);
             BufferedInputStream bis = new BufferedInputStream(fis);
             GZIPInputStream gis = new GZIPInputStream(bis);
             OutputStream fos = Files.newOutputStream(outputPath);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {

            byte[] buffer = new byte[8192];
            int len;
            while ((len = gis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }

            System.out.println(inputPath.getFileName() + " decompressed successfully!");

        } catch (IOException e) {
            System.err.println("An error occurred while decompressing the file: " + e.getMessage());
        }

        // Fuer die Zeitmessung dieses Schrittes wird hier die Endzeit bestimmt
        Instant end = Instant.now();
        Duration queryTime = Duration.between(start, end);
        System.out.printf("Time taken for decompress file: %d minutes, %d seconds%n",
                queryTime.toMinutes(), queryTime.getSeconds() % 60);
    }
}
