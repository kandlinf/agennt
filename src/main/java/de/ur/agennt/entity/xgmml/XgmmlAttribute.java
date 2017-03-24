package de.ur.agennt.entity.xgmml;

/**
 * Representation of an XGMML attribute entity
 */
public class XgmmlAttribute implements XgmmlEntity {
    private String type;
    private String name;
    private String value;

    public XgmmlAttribute() {
    }

    public XgmmlAttribute(String type, String name, String value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public XgmmlAttribute(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
