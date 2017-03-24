package de.ur.agennt.data.pfam;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class PfamWhitelistReader {
    private Set<String> whiteList = new HashSet<>();

    public PfamWhitelistReader() {
        InputStream filterStream = getClass().getResourceAsStream("whitelist.txt");
        try {
            parseFile(filterStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PfamWhitelistReader(File inputFile) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(inputFile);
        parseFile(fileInputStream);
        fileInputStream.close();
    }

    private void parseFile(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String pfamString = bufferedReader.readLine();
        while (pfamString != null) {
            whiteList.add(pfamString.trim());
            pfamString = bufferedReader.readLine();
        }
    }

    public Set<String> getWhiteList() {
        return whiteList;
    }

}
