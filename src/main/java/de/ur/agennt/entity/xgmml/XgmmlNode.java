package de.ur.agennt.entity.xgmml;

/**
 * Representation of an XGMML node entity
 */
public class XgmmlNode implements XgmmlEntity {
    private String id;
    private String label;

    public XgmmlNode() {
    }

    public XgmmlNode(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
