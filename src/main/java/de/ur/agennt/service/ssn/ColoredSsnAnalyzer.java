package de.ur.agennt.service.ssn;

import de.ur.agennt.data.DataFacade;
import de.ur.agennt.entity.colored.ColoredRepresentativeSequence;
import de.ur.agennt.entity.colored.ColoredSequence;
import de.ur.agennt.entity.colored.ColoredSsnAnalyzerResult;
import de.ur.agennt.entity.colored.ColoredSsnEntity;
import de.ur.agennt.entity.ssn.SsnEntity;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class ColoredSsnAnalyzer {
    private DataFacade dataFacade;
    private File file;

    public ColoredSsnAnalyzer(File inputFile) {
        dataFacade = DataFacade.getInstance();
        file = inputFile;
    }

    public ColoredSsnAnalyzerResult analyze() throws XMLStreamException, FileNotFoundException {
        ColoredSsnAnalyzerResult coloredSsnAnalyzerResult = new ColoredSsnAnalyzerResult();
        ColoredSsnEntity entity = dataFacade.readColoredSsnEvent(file);
        while (entity != null) {
            if (entity instanceof ColoredSequence) {
                ColoredSequence coloredSequence = (ColoredSequence) entity;
                if (!coloredSequence.getSupercluster().isEmpty()) {
                    Integer supercluster = Integer.parseInt(((ColoredSequence) entity).getSupercluster());
                    if (coloredSsnAnalyzerResult.getClusterNodeCountMap().containsKey(supercluster)) {
                        Integer count = coloredSsnAnalyzerResult.getClusterNodeCountMap().get(supercluster);
                        count++;
                        coloredSsnAnalyzerResult.getClusterNodeCountMap().put(supercluster, count);
                    } else {
                        coloredSsnAnalyzerResult.getClusterNodeCountMap().put(supercluster, 1);
                    }
                }

                if (!coloredSequence.getPhylum().isEmpty() && !coloredSequence.getSupercluster().isEmpty()) {
                    String phylum = coloredSequence.getPhylum();
                    Integer supercluster = Integer.parseInt(((ColoredSequence) entity).getSupercluster());
                    if (coloredSsnAnalyzerResult.getClusterPhylumPercentageMap().containsKey(supercluster)) {
                        Map<String, Double> phylumStatMap = coloredSsnAnalyzerResult.
                                getClusterPhylumPercentageMap().get(supercluster);
                        if (phylumStatMap.containsKey(phylum)) {
                            Double count = phylumStatMap.get(phylum);
                            count++;
                            phylumStatMap.put(phylum, count);
                        } else {
                            phylumStatMap.put(phylum, 1.0);
                        }
                    } else {
                        Map<String, Double> phylumStatMap = new HashMap<>();
                        phylumStatMap.put(phylum, 1.0);
                        coloredSsnAnalyzerResult.getClusterPhylumPercentageMap().put(supercluster, phylumStatMap);
                    }

                }

            } else if (entity instanceof ColoredRepresentativeSequence) {
                ColoredRepresentativeSequence coloredRepresentativeSequence = (ColoredRepresentativeSequence) entity;
                if (!coloredRepresentativeSequence.getSupercluster().isEmpty()) {
                    Integer supercluster = Integer.parseInt(((ColoredRepresentativeSequence) entity).getSupercluster());
                    if (coloredSsnAnalyzerResult.getClusterNodeCountMap().containsKey(supercluster)) {
                        Integer count = coloredSsnAnalyzerResult.getClusterNodeCountMap().get(supercluster);
                        count++;
                        coloredSsnAnalyzerResult.getClusterNodeCountMap().put(supercluster, count);
                    } else {
                        coloredSsnAnalyzerResult.getClusterNodeCountMap().put(supercluster, 1);
                    }
                }

                if (!coloredRepresentativeSequence.getPhylum().isEmpty() &&
                        !coloredRepresentativeSequence.getSupercluster().isEmpty()) {
                    for (String phylum : coloredRepresentativeSequence.getPhylum()) {
                        Integer supercluster = Integer.parseInt(((ColoredRepresentativeSequence) entity).getSupercluster());
                        if (coloredSsnAnalyzerResult.getClusterPhylumPercentageMap().containsKey(supercluster)) {
                            Map<String, Double> phylumStatMap = coloredSsnAnalyzerResult.
                                    getClusterPhylumPercentageMap().get(supercluster);
                            if (phylumStatMap.containsKey(phylum)) {
                                Double count = phylumStatMap.get(phylum);
                                count++;
                                phylumStatMap.put(phylum, count);
                            } else {
                                phylumStatMap.put(phylum, 1.0);
                            }
                        } else {
                            Map<String, Double> phylumStatMap = new HashMap<>();
                            phylumStatMap.put(phylum, 1.0);
                            coloredSsnAnalyzerResult.getClusterPhylumPercentageMap().put(supercluster, phylumStatMap);
                        }
                    }
                }
            }
            entity = dataFacade.readColoredSsnEvent(file);
        }

        for(Integer cluster : coloredSsnAnalyzerResult.getClusterPhylumPercentageMap().keySet()) {
            Map<String, Double> phylumMap = coloredSsnAnalyzerResult.getClusterPhylumPercentageMap().get(cluster);
            int count = 0;
            for (String phylum : phylumMap.keySet()) {
                count += phylumMap.get(phylum);
            }
            for (String phylum : phylumMap.keySet()) {
                phylumMap.replace(phylum, phylumMap.get(phylum) / count);
            }
        }

        return coloredSsnAnalyzerResult;
    }
}