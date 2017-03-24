package de.ur.agennt.entity.xgnn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kandlinf on 15.06.2016.
 */
public class Group implements XGnnEntity {
    private String id;
    private String label;
    private String clusterNumber;
    private String uniqueness;
    private String nodeCount;
    private String seqCount;
    private String nodeFillColor;
    private String nodeSize;
    private String nodeShape;
    private String nodeTransparency;
    private List<String> phylumStat;

    public Group(String id, String label) {
        this.id = id;
        this.label = label;
        this.phylumStat = new ArrayList<>();
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

    public String getClusterNumber() {
        return clusterNumber;
    }

    public void setClusterNumber(String clusterNumber) {
        this.clusterNumber = clusterNumber;
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

    public String getNodeFillColor() {
        return nodeFillColor;
    }

    public void setNodeFillColor(String nodeFillColor) {
        this.nodeFillColor = nodeFillColor;
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

    public String getNodeShape() {
        return nodeShape;
    }

    public void setNodeShape(String nodeShape) {
        this.nodeShape = nodeShape;
    }

    public String getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(String nodeCount) {
        this.nodeCount = nodeCount;
    }

    public List<String> getPhylumStat() {
        return phylumStat;
    }

    public void setPhylumStat(List<String> phylumStat) {
        this.phylumStat = phylumStat;
    }
}
