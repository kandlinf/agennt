package de.ur.agennt.entity.gnn;

import java.util.ArrayList;
import java.util.List;

public class Cluster implements GnnEntity {
    private String id;
    private String label;
    private String nodeFillColor;
    private String coOccurrence;
    private String coOccurrenceRatio;
    private String clusterNumber;
    private String totalSsnSequences;
    private String queriesWithPfamNeighbors;
    private String queriableSsnSequences;
    private String nodeSize;
    private String nodeShape;
    private String averageDistance;
    private String medianDistance;
    private String pfamNeighbors;
    private List<String> queryAccessions;
    private List<String> queryNeighborArrangement;
    private List<String> queryNeighborAccessions;

    public Cluster(String id, String label) {
        this.id = id;
        this.label = label;
        this.queryAccessions = new ArrayList<>();
        this.queryNeighborArrangement = new ArrayList<>();
        this.queryNeighborAccessions = new ArrayList<>();
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

    public String getNodeFillColor() {
        return nodeFillColor;
    }

    public void setNodeFillColor(String nodeFillColor) {
        this.nodeFillColor = nodeFillColor;
    }

    public String getCoOccurrence() {
        return coOccurrence;
    }

    public void setCoOccurrence(String coOccurrence) {
        this.coOccurrence = coOccurrence;
    }

    public String getCoOccurrenceRatio() {
        return coOccurrenceRatio;
    }

    public void setCoOccurrenceRatio(String coOccurrenceRatio) {
        this.coOccurrenceRatio = coOccurrenceRatio;
    }

    public String getClusterNumber() {
        return clusterNumber;
    }

    public void setClusterNumber(String clusterNumber) {
        this.clusterNumber = clusterNumber;
    }

    public String getTotalSsnSequences() {
        return totalSsnSequences;
    }

    public void setTotalSsnSequences(String totalSsnSequences) {
        this.totalSsnSequences = totalSsnSequences;
    }

    public String getQueriesWithPfamNeighbors() {
        return queriesWithPfamNeighbors;
    }

    public void setQueriesWithPfamNeighbors(String queriesWithPfamNeighbors) {
        this.queriesWithPfamNeighbors = queriesWithPfamNeighbors;
    }

    public String getQueriableSsnSequences() {
        return queriableSsnSequences;
    }

    public void setQueriableSsnSequences(String queriableSsnSequences) {
        this.queriableSsnSequences = queriableSsnSequences;
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

    public String getAverageDistance() {
        return averageDistance;
    }

    public void setAverageDistance(String averageDistance) {
        this.averageDistance = averageDistance;
    }

    public String getMedianDistance() {
        return medianDistance;
    }

    public void setMedianDistance(String medianDistance) {
        this.medianDistance = medianDistance;
    }

    public String getPfamNeighbors() {
        return pfamNeighbors;
    }

    public void setPfamNeighbors(String pfamNeighbors) {
        this.pfamNeighbors = pfamNeighbors;
    }

    public List<String> getQueryAccessions() {
        return queryAccessions;
    }

    public void setQueryAccessions(List<String> queryAccessions) {
        this.queryAccessions = queryAccessions;
    }

    public List<String> getQueryNeighborArrangement() {
        return queryNeighborArrangement;
    }

    public void setQueryNeighborArrangement(List<String> queryNeighborArrangement) {
        this.queryNeighborArrangement = queryNeighborArrangement;
    }

    public List<String> getQueryNeighborAccessions() {
        return queryNeighborAccessions;
    }

    public void setQueryNeighborAccessions(List<String> queryNeighborAccessions) {
        this.queryNeighborAccessions = queryNeighborAccessions;
    }
}
