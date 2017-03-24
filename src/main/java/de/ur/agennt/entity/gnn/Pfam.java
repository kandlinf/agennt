package de.ur.agennt.entity.gnn;

import de.ur.agennt.entity.gnn.GnnEntity;

import java.util.ArrayList;
import java.util.List;

public class Pfam implements GnnEntity {
    private String id;
    private String label;
    private String nodeSize;
    private String nodeShape;
    private String pfam;
    private String pfamDescription;
    private String totalSsnSequences;
    private String queriableSsnSequences;
    private String queriesWithPfamNeighbors;
    private String pfamNeighbors;
    private List<String> queryNeighborAccessions;
    private List<String> queryNeighborArrangement;
    private List<String> hubAverageAndMedianDistances;
    private List<String> hubCooccurrenceAndRatio;
    private String nodeFillColor;

    public Pfam(String id, String label) {
        this.id = id;
        this.label = label;
        this.queryNeighborAccessions = new ArrayList<>();
        this.queryNeighborArrangement = new ArrayList<>();
        this.hubAverageAndMedianDistances = new ArrayList<>();
        this.hubCooccurrenceAndRatio = new ArrayList<>();
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

    public String getNodeSize() {
        return nodeSize;
    }

    public void setNodeSize(String nodeSize) {
        this.nodeSize = nodeSize;
    }

    public String getNodeShape() {
        return nodeShape;
    }

    public void setNodeShape(String nodeShape) {
        this.nodeShape = nodeShape;
    }

    public String getPfam() {
        return pfam;
    }

    public void setPfam(String pfam) {
        this.pfam = pfam;
    }

    public String getPfamDescription() {
        return pfamDescription;
    }

    public void setPfamDescription(String pfamDescription) {
        this.pfamDescription = pfamDescription;
    }

    public String getTotalSsnSequences() {
        return totalSsnSequences;
    }

    public void setTotalSsnSequences(String totalSsnSequences) {
        this.totalSsnSequences = totalSsnSequences;
    }

    public String getQueriableSsnSequences() {
        return queriableSsnSequences;
    }

    public void setQueriableSsnSequences(String queriableSsnSequences) {
        this.queriableSsnSequences = queriableSsnSequences;
    }

    public String getQueriesWithPfamNeighbors() {
        return queriesWithPfamNeighbors;
    }

    public void setQueriesWithPfamNeighbors(String queriesWithPfamNeighbors) {
        this.queriesWithPfamNeighbors = queriesWithPfamNeighbors;
    }

    public String getPfamNeighbors() {
        return pfamNeighbors;
    }

    public void setPfamNeighbors(String pfamNeighbors) {
        this.pfamNeighbors = pfamNeighbors;
    }

    public List<String> getQueryNeighborAccessions() {
        return queryNeighborAccessions;
    }

    public void setQueryNeighborAccessions(List<String> queryNeighborAccessions) {
        this.queryNeighborAccessions = queryNeighborAccessions;
    }

    public List<String> getQueryNeighborArrangement() {
        return queryNeighborArrangement;
    }

    public void setQueryNeighborArrangement(List<String> queryNeighborArrangement) {
        this.queryNeighborArrangement = queryNeighborArrangement;
    }

    public List<String> getHubAverageAndMedianDistances() {
        return hubAverageAndMedianDistances;
    }

    public void setHubAverageAndMedianDistances(List<String> hubAverageAndMedianDistances) {
        this.hubAverageAndMedianDistances = hubAverageAndMedianDistances;
    }

    public List<String> getHubCooccurrenceAndRatio() {
        return hubCooccurrenceAndRatio;
    }

    public void setHubCooccurrenceAndRatio(List<String> hubCooccurrenceAndRatio) {
        this.hubCooccurrenceAndRatio = hubCooccurrenceAndRatio;
    }

    public String getNodeFillColor() {
        return nodeFillColor;
    }

    public void setNodeFillColor(String nodeFillColor) {
        this.nodeFillColor = nodeFillColor;
    }
}
