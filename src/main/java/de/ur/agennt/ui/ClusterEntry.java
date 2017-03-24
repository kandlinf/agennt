package de.ur.agennt.ui;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ClusterEntry {
    private SimpleIntegerProperty cluster;
    private SimpleIntegerProperty clusterSequences;
    private SimpleDoubleProperty uniqueness;
    private SimpleIntegerProperty clusterNodes;
    private SimpleStringProperty phylumStat;

    public ClusterEntry(Integer cluster, Integer clusterSequences, Double uniqueness, Integer clusterNodes, String phylumStats) {
        this.cluster = new SimpleIntegerProperty(cluster);
        this.clusterSequences = new SimpleIntegerProperty(clusterSequences);
        this.uniqueness = new SimpleDoubleProperty(uniqueness);
        this.clusterNodes = new SimpleIntegerProperty(clusterNodes);
        this.phylumStat = new SimpleStringProperty(phylumStats);
    }

    public int getCluster() {
        return cluster.get();
    }

    public SimpleIntegerProperty clusterProperty() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster.set(cluster);
    }

    public int getClusterSequences() {
        return clusterSequences.get();
    }

    public SimpleIntegerProperty clusterSequencesProperty() {
        return clusterSequences;
    }

    public void setClusterSequences(int clusterSequences) {
        this.clusterSequences.set(clusterSequences);
    }

    public double getUniqueness() {
        return uniqueness.get();
    }

    public SimpleDoubleProperty uniquenessProperty() {
        return uniqueness;
    }

    public void setUniqueness(double uniqueness) {
        this.uniqueness.set(uniqueness);
    }

    public int getClusterNodes() {
        return clusterNodes.get();
    }

    public SimpleIntegerProperty clusterNodesProperty() {
        return clusterNodes;
    }

    public void setClusterNodes(int clusterNodes) {
        this.clusterNodes.set(clusterNodes);
    }

    public String getPhylumStat() {
        return phylumStat.get();
    }

    public SimpleStringProperty phylumStatProperty() {
        return phylumStat;
    }

    public void setPhylumStat(String phylumStat) {
        this.phylumStat.set(phylumStat);
    }
}
