package de.ur.agennt.service.gnn;

import de.ur.agennt.data.DataFacade;
import de.ur.agennt.entity.gnn.*;
import de.ur.agennt.entity.go.Pfam2GoEntry;
import de.ur.agennt.entity.colored.ColoredSsnAnalyzerResult;
import de.ur.agennt.entity.xgnn.Family;
import de.ur.agennt.entity.xgnn.Group;
import de.ur.agennt.entity.xgnn.Relationship;
import de.ur.agennt.entity.xgnn.XGnn;
import de.ur.agennt.service.ServiceFacade;

import javax.xml.stream.XMLStreamException;
import java.awt.geom.Arc2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class ExtendedGnnGenerator {
    private static final double MAXIMUM_CLUSTER_SIZE = 150;
    private static final double MINIMUM_CLUSTER_SIZE = 30;
    private static final double MAXIMUM_PFAM_SIZE = 150;
    private static final double MINIMUM_PFAM_SIZE = 30;
    private static final double MAXIMUM_GROUP_TRANSPARENCY = 255;
    private static final double MINIMUM_GROUP_TRANSPARENCY = 30;
    private static final double MAXIMUM_FAMILY_TRANSPARENCY = 255;
    private static final double MINIMUM_FAMILY_TRANSPARENCY = 30;

    private DataFacade dataFacade;
    private FullGnn fullGnn;
    private ColoredSsnAnalyzerResult coloredSsnAnalyzerResult;
    private GnnStatistic gnnStatistic;
    private Set<Integer> writtenClusters;
    private File outputFile;
    private Double minimumGroupUniqueness = Double.MAX_VALUE;
    private Double maximumGroupUniqueness = Double.MIN_VALUE;
    private Double minimumFamilyUniqueness = Double.MAX_VALUE;
    private Double maximumFamilyUniqueness = Double.MIN_VALUE;
    private NumberFormat uniquenessDF = DecimalFormat.getNumberInstance(Locale.US);

    public ExtendedGnnGenerator(FullGnn fullGnn, GnnStatistic gnnStatistic, ColoredSsnAnalyzerResult coloredSsnAnalyzerResult) {
        dataFacade = DataFacade.getInstance();
        writtenClusters = new HashSet<>();
        uniquenessDF.setMaximumFractionDigits(2);
        uniquenessDF.setGroupingUsed(false);
        this.coloredSsnAnalyzerResult = coloredSsnAnalyzerResult;
        this.fullGnn = fullGnn;
        this.gnnStatistic = gnnStatistic;
        for(Double uniqueness : gnnStatistic.getClusterUniqueness().values()) {
            if(uniqueness < minimumGroupUniqueness)
                minimumGroupUniqueness = uniqueness;
            if(uniqueness > maximumGroupUniqueness)
                maximumGroupUniqueness = uniqueness;
        }
        for(Double unqiqueness : gnnStatistic.getPfamUniqueness().values()) {
            if(unqiqueness < minimumFamilyUniqueness)
                minimumFamilyUniqueness = unqiqueness;
            if(unqiqueness > maximumFamilyUniqueness)
                maximumFamilyUniqueness = unqiqueness;
        }
    }

    public void writeToFile(File outputFile) throws FileNotFoundException, XMLStreamException {
        this.outputFile = outputFile;
        writeXGnn(fullGnn.getGnn());
        for(Cluster cluster : fullGnn.getClusterMap().values()) {
            writeGroup(cluster);
        }
        for(Pfam pfam : fullGnn.getPfamMap().values()) {
            writeFamily(pfam);
        }
        for(List<Neighborhood> neighborhoodList: fullGnn.getPfamNeighborhoodMap().values()) {
            for(Neighborhood neighborhood : neighborhoodList) {
                writeRelationship(neighborhood);
            }
        }
        dataFacade.writeExtendedGnnEvent(outputFile, null);
    }

    private void writeRelationship(Neighborhood neighborhood) throws XMLStreamException, FileNotFoundException {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        numberFormat.setMaximumFractionDigits(2);

        Pfam pfam = fullGnn.getPfamMap().get(neighborhood.getSource());
        Cluster cluster = fullGnn.getClusterMap().get(neighborhood.getTarget());
        Relationship relationship = new Relationship(pfam.getPfam() + "-" + cluster.getClusterNumber(), pfam.getPfam(), cluster.getClusterNumber());
        relationship.setSeqCount(Integer.toString(fullGnn.getNeighborhoodSequenceCount(neighborhood)));
        relationship.setUniqueness(uniquenessDF.format(gnnStatistic.getNeighborhoodUniqueness().get(neighborhood.getLabel())));
        Double coverage = ((double)fullGnn.getNeighborhoodSequenceCount(neighborhood)) / Double.parseDouble(cluster.getTotalSsnSequences());
        relationship.setCoverage(uniquenessDF.format(coverage));
        dataFacade.writeExtendedGnnEvent(outputFile, relationship);
    }

    private void writeFamily(Pfam pfam) throws XMLStreamException, FileNotFoundException {
        Family family = new Family(pfam.getPfam(), pfam.getPfam());
        family.setSeqCount(Integer.toString(fullGnn.getPfamSequenceCount(pfam.getId())));
        family.setDescription(pfam.getPfamDescription());
        family.setPfam(pfam.getPfam());
        family.setGo(getGo(pfam.getPfam()));
        Double uniqueness = gnnStatistic.getPfamUniqueness().get(pfam.getPfam());
        family.setUniqueness(uniquenessDF.format(uniqueness));
        family.setNodeSize(mapFamilyNodeSize((double)fullGnn.getPfamSequenceCount(pfam.getId())).toString());
        family.setNodeTransparency(mapFamilyTransparency(gnnStatistic.getPfamUniqueness().get(pfam.getPfam())).toString());
        dataFacade.writeExtendedGnnEvent(outputFile, family);
    }

    private void writeGroup(Cluster cluster) throws XMLStreamException, FileNotFoundException {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        numberFormat.setMaximumFractionDigits(2);

        Integer clusterNumber = Integer.parseInt(cluster.getClusterNumber());
        if(!writtenClusters.contains(clusterNumber)) {
            Group group = new Group(cluster.getClusterNumber(), cluster.getClusterNumber());
            group.setClusterNumber(cluster.getClusterNumber());
            group.setNodeCount(coloredSsnAnalyzerResult.getClusterNodeCountMap().get(clusterNumber).toString());
            group.setSeqCount(cluster.getTotalSsnSequences());
            group.setUniqueness(uniquenessDF.format(gnnStatistic.getClusterUniqueness().get(cluster.getClusterNumber())));
            group.setNodeFillColor(cluster.getNodeFillColor());
            group.setNodeShape(cluster.getNodeShape());
            group.setNodeSize(mapGroupNodeSize(Double.parseDouble(cluster.getTotalSsnSequences())).toString());
            group.setNodeTransparency(mapGroupTransparency(gnnStatistic.getClusterUniqueness().get(cluster.getClusterNumber())).toString());

            Map<String, Double> unsortedPhylumStats = coloredSsnAnalyzerResult.getClusterPhylumPercentageMap().get(clusterNumber);
            if(unsortedPhylumStats != null) {
                Map<String, Double> phylumStats = ServiceFacade.sortByValue(unsortedPhylumStats);
                for (String phylum : phylumStats.keySet()) {
                    Double value = phylumStats.get(phylum);
                    if(value != null) group.getPhylumStat().add(phylum + ": " + numberFormat.format(value));
                }
            }

            dataFacade.writeExtendedGnnEvent(outputFile, group);
            writtenClusters.add(Integer.parseInt(cluster.getClusterNumber()));

        }
    }

    private Double mapGroupTransparency(Double uniqueness) {
        double perc = uniqueness/(double)(maximumGroupUniqueness);
        return MINIMUM_GROUP_TRANSPARENCY + perc*(MAXIMUM_GROUP_TRANSPARENCY-MINIMUM_GROUP_TRANSPARENCY);
    }

    private Double mapFamilyTransparency(Double uniqueness) {
        double perc = uniqueness/(double)(maximumFamilyUniqueness);
        return MINIMUM_FAMILY_TRANSPARENCY + perc*(MAXIMUM_FAMILY_TRANSPARENCY-MINIMUM_FAMILY_TRANSPARENCY);
    }

    private Double mapGroupNodeSize(Double ssnClusterSize) {
        double perc = ssnClusterSize/(double)(gnnStatistic.getMaximumClusterSize());
        return MINIMUM_CLUSTER_SIZE + (MAXIMUM_CLUSTER_SIZE-MINIMUM_CLUSTER_SIZE)*perc;
    }

    private Double mapFamilyNodeSize(Double familySize) {
        double perc = familySize/(double)(gnnStatistic.getMaximumPfamSize());
        return MINIMUM_PFAM_SIZE + (MAXIMUM_PFAM_SIZE-MINIMUM_PFAM_SIZE)*perc;
    }

    private void writeXGnn(Gnn gnn) throws XMLStreamException, FileNotFoundException {
        XGnn xGnn = new XGnn(gnn.getLabel());
        dataFacade.writeExtendedGnnEvent(outputFile, xGnn);
    }

    private List<String> getGo(String pfam) {
        List<String> returnValue = new ArrayList<>();
        List<Pfam2GoEntry> result = dataFacade.getGo(pfam);
        for(Pfam2GoEntry entry : result) {
            String go = entry.getGo();
            String description = entry.getDescription();
            returnValue.add(go + ": " + description);
        }
        return returnValue;
    }
}
