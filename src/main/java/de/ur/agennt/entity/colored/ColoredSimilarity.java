package de.ur.agennt.entity.colored;

import de.ur.agennt.entity.ssn.SsnEntity;

public class ColoredSimilarity implements ColoredSsnEntity {
    private String id;
    private String label;
    private String source;
    private String target;
    private String percId;
    private String alignmentScore;
    private String alignmentLen;

    public ColoredSimilarity(String id, String label, String source, String target) {
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

    public String getPercId() {
        return percId;
    }

    public String getAlignmentScore() {
        return alignmentScore;
    }

    public String getAlignmentLen() {
        return alignmentLen;
    }

    public void setPercId(String percId) {
        this.percId = percId;
    }

    public void setAlignmentScore(String alignmentScore) {
        this.alignmentScore = alignmentScore;
    }

    public void setAlignmentLen(String alignmentLen) {
        this.alignmentLen = alignmentLen;
    }
}
