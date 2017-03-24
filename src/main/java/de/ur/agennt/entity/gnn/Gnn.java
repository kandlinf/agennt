package de.ur.agennt.entity.gnn;

public class Gnn implements GnnEntity {
    private String label;

    public Gnn(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
