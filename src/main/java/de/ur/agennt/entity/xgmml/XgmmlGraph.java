package de.ur.agennt.entity.xgmml;

/**
 * Representation of an XGMML graph entity
 */
public class XgmmlGraph implements XgmmlEntity {
    private String label;

    public XgmmlGraph() {
    }

    public XgmmlGraph(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
