package de.ur.agennt.ui;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class NeighborhoodEntry {
    private SimpleIntegerProperty cluster;
    private SimpleStringProperty pfam;
    private SimpleIntegerProperty pfamSequences;
    private SimpleDoubleProperty uniqueness;
    private SimpleIntegerProperty clusterSequences;
    private SimpleIntegerProperty neighborhoodSequences;
    private SimpleStringProperty pfamGo;
    private SimpleStringProperty pfamDescription;
    private SimpleDoubleProperty coverage;

    public NeighborhoodEntry(Integer cluster,Integer clusterSequences, String pfam,Integer pfamSequences, Integer neighborhoodSequences, Double uniqueness, String pfamGo, String pfamDescription, Double coverage) {
        this.cluster = new SimpleIntegerProperty(cluster);
        this.pfam = new SimpleStringProperty(pfam);
        this.uniqueness = new SimpleDoubleProperty(uniqueness);
        this.clusterSequences = new SimpleIntegerProperty(clusterSequences);
        this.pfamSequences = new SimpleIntegerProperty(pfamSequences);
        this.neighborhoodSequences = new SimpleIntegerProperty(neighborhoodSequences);
        this.pfamGo = new SimpleStringProperty(pfamGo);
        this.pfamDescription = new SimpleStringProperty(pfamDescription);
        this.coverage = new SimpleDoubleProperty(coverage);
    }

    public Integer getCluster() {
        return cluster.get();
    }

    public SimpleIntegerProperty clusterProperty() {
        return cluster;
    }

    public void setCluster(Integer cluster) {
        this.cluster.set(cluster);
    }

    public String getPfam() {
        return pfam.get();
    }

    public SimpleStringProperty pfamProperty() {
        return pfam;
    }

    public void setPfam(String pfam) {
        this.pfam.set(pfam);
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

    public int getClusterSequences() {
        return clusterSequences.get();
    }

    public SimpleIntegerProperty clusterSequencesProperty() {
        return clusterSequences;
    }

    public void setClusterSequences(int clusterSequences) {
        this.clusterSequences.set(clusterSequences);
    }

    public int getPfamSequences() {
        return pfamSequences.get();
    }

    public SimpleIntegerProperty pfamSequencesProperty() {
        return pfamSequences;
    }

    public void setPfamSequences(int pfamSequences) {
        this.pfamSequences.set(pfamSequences);
    }

    public int getNeighborhoodSequences() {
        return neighborhoodSequences.get();
    }

    public SimpleIntegerProperty neighborhoodSequencesProperty() {
        return neighborhoodSequences;
    }

    public void setNeighborhoodSequences(int neighborhoodSequences) {
        this.neighborhoodSequences.set(neighborhoodSequences);
    }

    public String getPfamGo() {
        return pfamGo.get();
    }

    public SimpleStringProperty pfamGoProperty() {
        return pfamGo;
    }

    public void setPfamGo(String pfamGo) {
        this.pfamGo.set(pfamGo);
    }

    public String getPfamDescription() {
        return pfamDescription.get();
    }

    public SimpleStringProperty pfamDescriptionProperty() {
        return pfamDescription;
    }

    public void setPfamDescription(String pfamDescription) {
        this.pfamDescription.set(pfamDescription);
    }

    public double getCoverage() {
        return coverage.get();
    }

    public SimpleDoubleProperty coverageProperty() {
        return coverage;
    }

    public void setCoverage(double coverage) {
        this.coverage.set(coverage);
    }
}
