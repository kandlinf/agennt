package de.ur.agennt.entity.gnn;

import de.ur.agennt.entity.gnn.Cluster;
import de.ur.agennt.entity.gnn.Gnn;
import de.ur.agennt.entity.gnn.Neighborhood;
import de.ur.agennt.entity.gnn.Pfam;

import java.io.Serializable;
import java.util.*;

public class FullGnn implements Serializable {
    private static final long serialVersionUID = 42L;

    private Gnn gnn;
    private Map<String, Cluster> clusterMap;
    private Map<String, Pfam> pfamMap;
    private Map<String, Neighborhood> clusterNeighborhoodMap;
    private Map<String, List<Neighborhood>> pfamNeighborhoodMap;
    private Integer neighborhoodSize;
    private Integer coocurrence;

    public FullGnn() {
        this.clusterMap = new HashMap<>();
        this.pfamMap = new HashMap<>();
        this.clusterNeighborhoodMap = new HashMap<>();
        this.pfamNeighborhoodMap = new HashMap<>();
    }

    public FullGnn(FullGnn input) {
        this.gnn = input.getGnn();
        this.clusterMap = new HashMap<>(input.getClusterMap());
        this.pfamMap = new HashMap<>(input.getPfamMap());
        this.clusterNeighborhoodMap = new HashMap<>(input.getClusterNeighborhoodMap());
        this.pfamNeighborhoodMap = new HashMap<>(input.getPfamNeighborhoodMap());
        this.neighborhoodSize = input.getNeighborhoodSize();
        this.coocurrence = input.getCoocurrence();
    }

    public Integer getCoocurrence() {
        return coocurrence;
    }

    public void setCoocurrence(Integer coocurrence) {
        this.coocurrence = coocurrence;
    }

    public Integer getNeighborhoodSize() {
        return neighborhoodSize;
    }

    public void setNeighborhoodSize(Integer neighborhoodSize) {
        this.neighborhoodSize = neighborhoodSize;
    }

    public Map<String, List<Neighborhood>> getPfamNeighborhoodMap() {
        return pfamNeighborhoodMap;
    }

    public void setPfamNeighborhoodMap(Map<String, List<Neighborhood>> pfamNeighborhoodMap) {
        this.pfamNeighborhoodMap = pfamNeighborhoodMap;
    }

    public Map<String, Neighborhood> getClusterNeighborhoodMap() {
        return clusterNeighborhoodMap;
    }

    public void setClusterNeighborhoodMap(Map<String, Neighborhood> clusterNeighborhoodMap) {
        this.clusterNeighborhoodMap = clusterNeighborhoodMap;
    }

    public Map<String, Pfam> getPfamMap() {
        return pfamMap;
    }

    public void setPfamMap(Map<String, Pfam> pfamMap) {
        this.pfamMap = pfamMap;
    }

    public Map<String, Cluster> getClusterMap() {
        return clusterMap;
    }

    public void setClusterMap(Map<String, Cluster> clusterMap) {
        this.clusterMap = clusterMap;
    }

    public Gnn getGnn() {
        return gnn;
    }

    public void setGnn(Gnn gnn) {
        this.gnn = gnn;
    }

    public int getNeighborhoodSequenceCount(Neighborhood neighborhood) {
        Cluster cluster = clusterMap.get(neighborhood.getTarget());
        return Integer.parseInt(cluster.getQueriesWithPfamNeighbors());

    }

    public int getPfamSequenceCount(String pfamId) {
        int count = 0;
        for(Neighborhood neighborhood : pfamNeighborhoodMap.get(pfamId)) {
            count += getNeighborhoodSequenceCount(neighborhood);
        }
        return count;
    }

    public int clusterCount() {
        int count = 0;
        Set<String> countedClusters = new HashSet<>();
        for(Cluster cluster : clusterMap.values()) {
            if(!countedClusters.contains(cluster.getClusterNumber())) {
                count ++;
                countedClusters.add(cluster.getClusterNumber());
            }
        }
        return count;
    }

    public int getSequenceCount() {
        int count = 0;
        Set<String> countedClusters = new HashSet<>();
        for(Cluster cluster : clusterMap.values()) {
            if(!countedClusters.contains(cluster.getClusterNumber())) {
                count += Integer.parseInt(cluster.getTotalSsnSequences());
                countedClusters.add(cluster.getClusterNumber());
            }
        }
        return count;
    }
}
