package de.ur.agennt.entity.ssn;

public class Ssn implements SsnEntity {
    private String label;

    public Ssn(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
