package de.ur.agennt.data.pfam;

import de.ur.agennt.entity.go.Pfam2GoEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pfam2Go {

    private static final String PFAM2GO_FILE = "pfam2go.txt";

    private InputStream inputStream;
    private Map<String, List<Pfam2GoEntry>> pfamMap;

    public Pfam2Go() {
        try {
            this.inputStream = Pfam2Go.class.getResourceAsStream(PFAM2GO_FILE);
            this.pfamMap = new HashMap<>();
            parseFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parseFile() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = bufferedReader.readLine();
        while(line != null) {
            parseLine(line);
            line = bufferedReader.readLine();
        }

        bufferedReader.close();
    }

    private void parseLine(String line) {
        if(line.startsWith("!")) {
            //igonore comment lines
        } else {
            String pfam = line.substring(5,12);
            int splitIndex = line.indexOf(">");
            String goString = line.substring(splitIndex+1);
            String[] goStringParts = goString.split(";");
            String goDescription = goStringParts[0].trim().substring(3);
            String go = goStringParts[1].trim();

            Pfam2GoEntry entry = new Pfam2GoEntry(go, goDescription);

            if(pfamMap.containsKey(pfam)) {
                pfamMap.get(pfam).add(entry);
            } else {
                ArrayList<Pfam2GoEntry> list = new ArrayList<>();
                list.add(entry);
                pfamMap.put(pfam, list);
            }
        }
    }

    public List<Pfam2GoEntry> getGo(String pfam) {
        if(pfamMap.containsKey(pfam))
            return pfamMap.get(pfam);
        else
            return new ArrayList<>();
    }
}
