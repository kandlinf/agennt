package de.ur.agennt.service.ssn;

import de.ur.agennt.entity.ssn.SsnAnalyzerResult;

public class ApeltsinThreshold {
    private SsnAnalyzerResult ssnAnalyzerResult;

    public ApeltsinThreshold(SsnAnalyzerResult ssnAnalyzerResult) {
        this.ssnAnalyzerResult = ssnAnalyzerResult;
    }

    public double nsv(Integer threshold) {
        return ((double) ssnAnalyzerResult.getSeMap().get(threshold))/((double) ssnAnalyzerResult.getNnMap().get(threshold));
    }

    public Integer getThreshold() {
        double oldNsv = nsv(ssnAnalyzerResult.getMinThreshold());
        for(int i = ssnAnalyzerResult.getMinThreshold()+1; i <= ssnAnalyzerResult.getMaxThreshold(); i++) {
            double currentNsv = nsv(i);
            if(currentNsv-oldNsv >= 0) return i;
            oldNsv = currentNsv;
        }
        return null;
    }
}
