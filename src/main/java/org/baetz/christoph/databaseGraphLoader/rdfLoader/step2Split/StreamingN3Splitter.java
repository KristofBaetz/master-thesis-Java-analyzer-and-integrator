package org.baetz.christoph.databaseGraphLoader.rdfLoader.step2Split;

import org.apache.jena.graph.Triple;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.riot.RDFParser;
import org.apache.jena.riot.system.StreamRDF;
import org.apache.jena.riot.system.StreamRDFWriter;
import org.apache.jena.sparql.core.Quad;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class StreamingN3Splitter {
    private static class SplittingRDFHandler implements StreamRDF {
        private StreamRDF writer;
        private int tripleCount = 0;
        private int fileCount = 0;
        private int triplesPerFile;

        private String outputPath;

        public SplittingRDFHandler(int triplesPerFile) {
            this.triplesPerFile = triplesPerFile;
            createNewWriter();
        }

        private void createNewWriter() {
            fileCount++;
            String outputFile = "./split_output/" + "split_" + fileCount + ".n3";
            try {
                writer = StreamRDFWriter.getWriterStream(new FileOutputStream(outputFile), RDFFormat.NTRIPLES);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            writer.start();
        }

        @Override
        public void triple(Triple triple) {
            writer.triple(triple);
            tripleCount++;
            if (tripleCount % triplesPerFile == 0) {
                writer.finish();
                createNewWriter();
            }
        }

        @Override
        public void start() {}

        @Override
        public void finish() {
            writer.finish();
        }

        @Override
        public void base(String base) {
            writer.base(base);
        }

        @Override
        public void prefix(String prefix, String iri) {
            writer.prefix(prefix, iri);
        }

        @Override
        public void quad(Quad quad) {}
    }

    public void execute(String inputFile){
        int triplesPerFile = 1000000; // Adjust as needed

        SplittingRDFHandler handler = new SplittingRDFHandler(triplesPerFile);
        RDFParser.create()
                .source(inputFile)
                .lang(Lang.N3)
                .parse(handler);
    }


}