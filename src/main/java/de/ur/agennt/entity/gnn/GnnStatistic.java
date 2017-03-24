package de.ur.agennt.entity.gnn;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GnnStatistic implements Serializable{
    private Map<String, Double> neighborhoodUniqueness;
    private Map<String, Double> clusterUniqueness;
    private Map<String, Double> pfamUniqueness;
    private Integer minimumClusterSize;
    private Integer maximumClusterSize;
    private Integer minimumPfamSize;
    private Integer maximumPfamSize;

    public GnnStatistic() {
        this.neighborhoodUniqueness = new HashMap<>();
        this.clusterUniqueness = new HashMap<>();
        this.pfamUniqueness = new HashMap<>();
        this.maximumClusterSize = Integer.MIN_VALUE;
        this.minimumClusterSize = Integer.MAX_VALUE;
        this.maximumPfamSize = Integer.MIN_VALUE;
        this.minimumPfamSize = Integer.MAX_VALUE;
    }


    public Map<String, Double> getNeighborhoodUniqueness() {
        return neighborhoodUniqueness;
    }

    public Map<String, Double> getClusterUniqueness() {
        return clusterUniqueness;
    }

    public Map<String, Double> getPfamUniqueness() {
        return pfamUniqueness;
    }

    public Integer getMaximumClusterSize() {
        return maximumClusterSize;
    }

    public void setMaximumClusterSize(Integer maximumClusterSize) {
        this.maximumClusterSize = maximumClusterSize;
    }

    public Integer getMinimumClusterSize() {
        return minimumClusterSize;
    }

    public void setMinimumClusterSize(Integer minimumClusterSize) {
        this.minimumClusterSize = minimumClusterSize;
    }

    public Integer getMinimumPfamSize() {
        return minimumPfamSize;
    }

    public void setMinimumPfamSize(Integer minimumPfamSize) {
        this.minimumPfamSize = minimumPfamSize;
    }

    public Integer getMaximumPfamSize() {
        return maximumPfamSize;
    }

    public void setMaximumPfamSize(Integer maximumPfamSize) {
        this.maximumPfamSize = maximumPfamSize;
    }
}
