package de.ur.agennt.entity.go;

public class Pfam2GoEntry {
    private String go;
    private String description;

    public Pfam2GoEntry(String go, String description) {
        this.go = go;
        this.description = description;
    }

    public String getGo() {
        return go;
    }

    public String getDescription() {
        return description;
    }
}
