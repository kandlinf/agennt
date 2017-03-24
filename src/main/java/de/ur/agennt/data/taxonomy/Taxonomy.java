package de.ur.agennt.data.taxonomy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Taxonomy {
    private static final String TAXCAT_FILE = "categories.dmp";

    private InputStream inputStream;
    private Set<String> speciesSet;

    public Taxonomy() {
        try {
            this.inputStream = Taxonomy.class.getResourceAsStream(TAXCAT_FILE);
            this.speciesSet = new HashSet<>();
            parseFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parseFile() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = bufferedReader.readLine();
        while (line != null) {
            parseLine(line);
            line = bufferedReader.readLine();
        }

        bufferedReader.close();
    }

    private void parseLine(String line) {
        String[] components = line.split("\t");
        String category = components[0].trim();
        String species = components[1].trim();
        String tax = components[2].trim();
        speciesSet.add(species);
    }

    public boolean isSpecies(String taxonomyId) {
        return speciesSet.contains(taxonomyId);
    }
}
