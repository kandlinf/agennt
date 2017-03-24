package de.ur.agennt.service.gnn;

import de.ur.agennt.entity.gnn.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NeighborhoodStatistic {
    private FullGnn fullGnn;

    public NeighborhoodStatistic(FullGnn fullGnn) {
        this.fullGnn = fullGnn;
    }

    public GnnStatistic calculate() {
        try {
            GnnStatistic result = new GnnStatistic();
            for (Neighborhood neighborhood : fullGnn.getClusterNeighborhoodMap().values()) {
                Pfam pfam = fullGnn.getPfamMap().get(neighborhood.getSource());
                Cluster cluster = fullGnn.getClusterMap().get(neighborhood.getTarget());
                Integer N = fullGnn.getSequenceCount();
                Integer n = Integer.parseInt(cluster.getTotalSsnSequences());
                Integer K = fullGnn.getPfamSequenceCount(pfam.getId());
                Integer k = fullGnn.getNeighborhoodSequenceCount(neighborhood);
                Double uniqueness = -1 * (logBinom(K, k) + logBinom(N - K, n - k) - logBinom(N, n));
                result.getNeighborhoodUniqueness().put(neighborhood.getLabel(), uniqueness);

                if (n > result.getMaximumClusterSize()) {
                    result.setMaximumClusterSize(n);
                }
                if (n < result.getMinimumClusterSize()) {
                    result.setMinimumClusterSize(n);
                }

                if (K > result.getMaximumPfamSize()) {
                    result.setMaximumPfamSize(K);
                }
                if (K < result.getMinimumPfamSize()) {
                    result.setMinimumPfamSize(K);
                }

                if (result.getClusterUniqueness().containsKey(cluster.getClusterNumber())) {
                    Double newUniqueness = result.getClusterUniqueness().get(cluster.getClusterNumber()) + uniqueness;
                    result.getClusterUniqueness().put(cluster.getClusterNumber(), newUniqueness);
                } else {
                    result.getClusterUniqueness().put(cluster.getClusterNumber(), uniqueness);
                }

                if (result.getPfamUniqueness().containsKey(pfam.getPfam())) {
                    Double newUniqueness = result.getPfamUniqueness().get(pfam.getPfam()) + uniqueness;
                    result.getPfamUniqueness().put(pfam.getPfam(), newUniqueness);
                } else {
                    result.getPfamUniqueness().put(pfam.getPfam(), uniqueness);
                }

            }
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static double logBinom(double n, double k) {
        double sum = 0;
        for(int i = 1; i <=k; i++) {
            sum += Math.log10((n+1-i)/i);
        }
        return sum;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bufferedReader.readLine());
        int n = Integer.parseInt(bufferedReader.readLine());
        int K = Integer.parseInt(bufferedReader.readLine());
        int k = Integer.parseInt(bufferedReader.readLine());
        double uniqueness = -1*(logBinom(K,k) + logBinom(N-K,n-k) - logBinom(N,n));

    }

}
