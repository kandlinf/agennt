package de.ur.agennt.entity.colored;

import java.awt.geom.Arc2D;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColoredSsnAnalyzerResult implements Serializable {
    private Map<Integer, Integer> clusterNodeCountMap;
    private Map<Integer, Map<String, Double>> clusterPhylumPercentageMap;

    public ColoredSsnAnalyzerResult() {
        this.clusterNodeCountMap = new HashMap<>();
        this.clusterPhylumPercentageMap = new HashMap<>();
    }

    public Map<Integer, Integer> getClusterNodeCountMap() {
        return clusterNodeCountMap;
    }

    public Map<Integer, Map<String, Double>> getClusterPhylumPercentageMap() {
        return clusterPhylumPercentageMap;
    }
}
