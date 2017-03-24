package de.ur.agennt.service.ssn;

import de.ur.agennt.entity.ssn.SsnAnalyzerResult;

public class SmoothThreshold {
    private SsnAnalyzerResult ssnAnalyzerResult;

    public SmoothThreshold(SsnAnalyzerResult ssnAnalyzerResult) {
        this.ssnAnalyzerResult = ssnAnalyzerResult;
    }

    public Integer getThreshold() {
        int lastSE = ssnAnalyzerResult.getSeMap().get(ssnAnalyzerResult.getMinThreshold());
        int lastNN = ssnAnalyzerResult.getNnMap().get(ssnAnalyzerResult.getMinThreshold());
        for(int i = ssnAnalyzerResult.getMinThreshold()+1; i <= ssnAnalyzerResult.getMaxThreshold(); i++) {
            int currentSE = ssnAnalyzerResult.getSeMap().get(i);
            int currrentNN = ssnAnalyzerResult.getNnMap().get(i);

            double percSE = ((double)(lastSE-currentSE))/((double) ssnAnalyzerResult.getSimilarityCount());
            double percNN = ((double)(lastNN-currrentNN))/((double) ssnAnalyzerResult.getSequenceCount());

            if(percNN > percSE) return i;

            lastSE = currentSE;
            lastNN = currrentNN;

        }
        return null;
    }
}
