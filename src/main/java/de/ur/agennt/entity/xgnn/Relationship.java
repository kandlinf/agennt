package de.ur.agennt.entity.xgnn;

/**
 * Created by kandlinf on 15.06.2016.
 */
public class Relationship implements XGnnEntity{
    private String label;
    private String source;
    private String target;
    private String uniqueness;
    private String seqCount;
    private String coverage;

    public Relationship(String label, String source, String target) {
        this.label = label;
        this.source = source;
        this.target = target;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getUniqueness() {
        return uniqueness;
    }

    public void setUniqueness(String uniqueness) {
        this.uniqueness = uniqueness;
    }

    public String getSeqCount() {
        return seqCount;
    }

    public void setSeqCount(String seqCount) {
        this.seqCount = seqCount;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }
}
