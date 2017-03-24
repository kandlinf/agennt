package de.ur.agennt.data.pfam;

import java.io.*;
import java.util.Set;

/**
 * Created by kandlinf on 13.06.2016.
 */
public class PfamWhitelistWriter {
    private Set<String> whiteList;

    public PfamWhitelistWriter(Set<String> pfamSet) {
        whiteList = pfamSet;
    }

    public void writeToFile(File outputFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        for(String entry : whiteList) {
            bufferedWriter.write(entry);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }
}
