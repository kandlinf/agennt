package de.ur.agennt.ui;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PfamEntry {
    private SimpleStringProperty pfam;
    private SimpleIntegerProperty pfamSequences;
    private SimpleDoubleProperty uniqueness;
    private SimpleStringProperty pfamGo;
    private SimpleStringProperty pfamDescription;

    public PfamEntry(String pfam, Integer pfamSequences, Double uniqueness, String pfamGo, String pfamDescription) {
        this.pfam = new SimpleStringProperty(pfam);
        this.pfamSequences = new SimpleIntegerProperty(pfamSequences);
        this.uniqueness = new SimpleDoubleProperty(uniqueness);
        this.pfamGo = new SimpleStringProperty(pfamGo);
        this.pfamDescription = new SimpleStringProperty(pfamDescription);
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

    public int getPfamSequences() {
        return pfamSequences.get();
    }

    public SimpleIntegerProperty pfamSequencesProperty() {
        return pfamSequences;
    }

    public void setPfamSequences(int pfamSequences) {
        this.pfamSequences.set(pfamSequences);
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
}
