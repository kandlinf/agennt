package de.ur.agennt.entity.gnn;

import de.ur.agennt.entity.gnn.GnnEntity;

/**
 * Created by kandlinf on 04.04.2016.
 */
public class Neighborhood implements GnnEntity {
    private String label;
    private String source;
    private String target;

    public Neighborhood(String label, String source, String target) {
        this.label = label;
        this.source = source;
        this.target = target;
    }

    public String getLabel() {
        return label;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }
}
