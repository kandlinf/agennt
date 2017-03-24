package de.ur.agennt.entity.xgmml;

/**
 * Representation of an XGMML edge entity
 */
public class XgmmlEdge implements XgmmlEntity {
    private String id;
    private String label;
    private String source;
    private String target;

    public XgmmlEdge() {
    }

    public XgmmlEdge(String id, String label, String source, String target) {
        this.id = id;
        this.label = label;
        this.source = source;
        this.target = target;
    }

    public String getId() {
        return id;
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
