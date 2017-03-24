package de.ur.agennt.entity.xgnn;

/**
 * Created by kandlinf on 15.06.2016.
 */
public class XGnn implements XGnnEntity {
    private String label;

    public XGnn(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
