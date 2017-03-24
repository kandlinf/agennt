package de.ur.agennt.entity.xgnn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kandlinf on 15.06.2016.
 */
public class Family implements XGnnEntity {
    private String id;
    private String label;
    private String pfam;
    private String description;
    private List<String> go;
    private String uniqueness;
    private String seqCount;
    private String nodeSize;
    private String nodeTransparency;


    public Family(String id, String label) {
        this.id = id;
        this.label = label;
        this.go = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPfam() {
        return pfam;
    }

    public void setPfam(String pfam) {
        this.pfam = pfam;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getGo() {
        return go;
    }

    public void setGo(List<String> go) {
        this.go = go;
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

    public String getNodeSize() {
        return nodeSize;
    }

    public void setNodeSize(String nodeSize) {
        this.nodeSize = nodeSize;
    }

    public String getNodeTransparency() {
        return nodeTransparency;
    }

    public void setNodeTransparency(String nodeTransparency) {
        this.nodeTransparency = nodeTransparency;
    }

}
