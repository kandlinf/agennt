package de.ur.agennt.entity.colored;

import de.ur.agennt.entity.ssn.SsnEntity;

public class ColoredSsn implements ColoredSsnEntity {
    private String label;

    public ColoredSsn(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
