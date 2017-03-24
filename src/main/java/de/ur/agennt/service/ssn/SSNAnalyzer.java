package de.ur.agennt.service.ssn;


import de.ur.agennt.data.DataFacade;
import de.ur.agennt.entity.ssn.SsnAnalyzerResult;
import de.ur.agennt.entity.ssn.*;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class SSNAnalyzer {
    private DataFacade dataFacade;
    private Map<String, Integer> nodeThresholdMap;
    private File file;

    public SSNAnalyzer(File inputFile) {
        dataFacade = DataFacade.getInstance();
        file = inputFile;
        this.nodeThresholdMap = new HashMap<>();
    }

    public SsnAnalyzerResult analyze(boolean taxonomyFiltered) throws XMLStreamException, FileNotFoundException {
        SsnAnalyzerResult result = new SsnAnalyzerResult();
        result.setTaxonomyFiltered(taxonomyFiltered);
        SsnEntity entity = dataFacade.readSsnEvent(file);
        while(entity != null) {
            if(entity instanceof Ssn) {
                Ssn ssn = (Ssn)entity;
                result.setName(ssn.getLabel());
            } else if(entity instanceof Similarity) {
                Similarity similarity = (Similarity)entity;
                Double alignmentScore = Double.parseDouble(similarity.getAlignmentScore());
                result.setSimilarityCount(result.getSimilarityCount()+1);

                if(result.getThresholdEdgeCountMap().containsKey(alignmentScore.intValue())) {
                    Integer currentCount = result.getThresholdEdgeCountMap().get(alignmentScore.intValue());
                    result.getThresholdEdgeCountMap().put(alignmentScore.intValue(), currentCount+1);
                } else {
                    result.getThresholdEdgeCountMap().put(alignmentScore.intValue(),1);
                }

                String source = similarity.getSource();
                String target = similarity.getTarget();
                if(nodeThresholdMap.containsKey(source)) {
                    if(nodeThresholdMap.get(source) < alignmentScore.intValue()) {
                        nodeThresholdMap.put(source, alignmentScore.intValue());
                    }
                } else {
                    nodeThresholdMap.put(source, alignmentScore.intValue());
                }
                if(nodeThresholdMap.containsKey(target)) {
                    if(nodeThresholdMap.get(target) < alignmentScore.intValue()) {
                        nodeThresholdMap.put(target, alignmentScore.intValue());
                    }
                } else {
                    nodeThresholdMap.put(target, alignmentScore.intValue());
                }

                if(result.getMinThreshold() > alignmentScore.intValue())
                    result.setMinThreshold(alignmentScore.intValue());
                if(result.getMaxThreshold() < alignmentScore.intValue())
                    result.setMaxThreshold(alignmentScore.intValue());
            } else if(entity instanceof Sequence) {
                result.setRepresentative(false);
                result.setSequenceCount(result.getSequenceCount()+1);
                result.setTotalSequenceCount(result.getTotalSequenceCount()+1);
            } else if(entity instanceof RepresentativeSequence) {
                RepresentativeSequence sequence = (RepresentativeSequence)entity;
                Integer clusterSize = Integer.parseInt(sequence.getClusterSize());
                result.setRepresentative(true);
                result.setSequenceCount(result.getSequenceCount()+1);
                result.setTotalSequenceCount(result.getTotalSequenceCount()+ clusterSize);
            }
            entity = dataFacade.readSsnEvent(file);
        }

        for(int th = result.getMinThreshold(); th <= result.getMaxThreshold(); th++) {
            if (!result.getThresholdEdgeCountMap().containsKey(th)) {
                result.getThresholdEdgeCountMap().put(th,0);
            }
        }

        int totalEdges = 0;
        for(int th = result.getMaxThreshold(); th >= result.getMinThreshold(); th--) {
            if(result.getThresholdEdgeCountMap().containsKey(th)) {
                totalEdges += result.getThresholdEdgeCountMap().get(th);
            }
            result.getSeMap().put(th, totalEdges);
        }

        for(int th = result.getMinThreshold(); th <= result.getMaxThreshold(); th++) {
            int nodeCount = 0;
            for(String node : nodeThresholdMap.keySet()) {
                if(nodeThresholdMap.get(node) >= th) {
                    nodeCount++;
                }
            }
            result.getNnMap().put(th, nodeCount);
        }

        return result;
    }


}
