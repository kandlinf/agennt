package de.ur.agennt.entity.ssn;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SsnAnalyzerResult implements Serializable {
    private boolean isRepresentative;
    private int similarityCount;
    private int sequenceCount;
    private int totalSequenceCount;
    private int maxThreshold;
    private int minThreshold;
    private boolean taxonomyFiltered;
    private String name;
    private Map<Integer, Integer> thresholdEdgeCountMap;
    private Map<Integer, Integer> seMap;
    private Map<Integer, Integer> nnMap;

    public SsnAnalyzerResult() {
        maxThreshold = Integer.MIN_VALUE;
        minThreshold = Integer.MAX_VALUE;
        thresholdEdgeCountMap = new HashMap<>();
        seMap = new HashMap<>();
        nnMap = new HashMap<>();
    }

    public boolean isRepresentative() {
        return isRepresentative;
    }

    public void setRepresentative(boolean representative) {
        isRepresentative = representative;
    }

    public int getSimilarityCount() {
        return similarityCount;
    }

    public void setSimilarityCount(int similarityCount) {
        this.similarityCount = similarityCount;
    }

    public int getSequenceCount() {
        return sequenceCount;
    }

    public void setSequenceCount(int sequenceCount) {
        this.sequenceCount = sequenceCount;
    }

    public int getTotalSequenceCount() {
        return totalSequenceCount;
    }

    public void setTotalSequenceCount(int totalSequenceCount) {
        this.totalSequenceCount = totalSequenceCount;
    }

    public int getMaxThreshold() {
        return maxThreshold;
    }

    public void setMaxThreshold(int maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    public int getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(int minThreshold) {
        this.minThreshold = minThreshold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Map<Integer, Integer> getThresholdEdgeCountMap() {
        return thresholdEdgeCountMap;
    }

    public Map<Integer, Integer> getSeMap() {
        return seMap;
    }

    public Map<Integer, Integer> getNnMap() {
        return nnMap;
    }

    public boolean isTaxonomyFiltered() {
        return taxonomyFiltered;
    }

    public void setTaxonomyFiltered(boolean taxonomyFiltered) {
        this.taxonomyFiltered = taxonomyFiltered;
    }
}
