package de.ur.agennt.data.gnn;

import de.ur.agennt.entity.gnn.*;
import de.ur.agennt.entity.xgmml.*;
import de.ur.agennt.data.xgmml.XgmmlEvent;
import de.ur.agennt.data.xgmml.XgmmlEventReader;
import de.ur.agennt.data.xgmml.XgmmlEventType;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GnnEventReader {
    private XgmmlEventReader xgmmlEventReader;
    private GnnEntity nextEntity;
    private List<GnnEntity> entityList;

    public GnnEventReader(File inputFile) throws FileNotFoundException, XMLStreamException {
        xgmmlEventReader = new XgmmlEventReader(inputFile);
        nextEntity = findNextEntity();
        entityList = new ArrayList<>();
    }

    public boolean hasNext() {
        return nextEntity != null;
    }

    public GnnEntity next() throws XMLStreamException {
        if(hasNext()) {
            GnnEntity returnValue = nextEntity;
            nextEntity = findNextEntity();
            return returnValue;
        } else {
            return null;
        }
    }

    private GnnEntity findNextEntity() throws XMLStreamException {
        while (xgmmlEventReader.hasNext()) {
            XgmmlEvent xgmmlEvent = xgmmlEventReader.next();
            XgmmlEventType xgmmlEventType = xgmmlEvent.getEventType();
            XgmmlEntity xgmmlEntity = xgmmlEvent.getXgmmlEntity();
            if(xgmmlEventType == XgmmlEventType.BEGIN) {
                if(xgmmlEntity instanceof XgmmlGraph) {
                    XgmmlGraph xgmmlGraph = (XgmmlGraph)xgmmlEntity;
                    return new Gnn(xgmmlGraph.getLabel());
                } else if(xgmmlEntity instanceof XgmmlEdge) {
                    XgmmlEdge xgmmlEdge = (XgmmlEdge)xgmmlEntity;
                    Neighborhood neighborhood = new Neighborhood( xgmmlEdge.getLabel(),
                            xgmmlEdge.getSource(), xgmmlEdge.getTarget());
                    entityList.add(neighborhood);
                } else if(xgmmlEntity instanceof XgmmlNode) {
                    XgmmlNode xgmmlNode = (XgmmlNode)xgmmlEntity;
                    if(xgmmlNode.getId().contains(":")) {
                        Cluster cluster = new Cluster(xgmmlNode.getId(), xgmmlNode.getLabel());
                        entityList.add(cluster);
                    } else {
                        Pfam pfam = new Pfam(xgmmlNode.getId(), xgmmlNode.getLabel());
                        entityList.add(pfam);
                    }
                } else {
                    XgmmlAttribute xgmmlAttribute = (XgmmlAttribute)xgmmlEntity;
                    GnnEntity gnnEntity = entityList.get(entityList.size()-1);
                    if(gnnEntity instanceof Cluster) {
                        Cluster cluster = (Cluster)gnnEntity;
                        if(xgmmlAttribute.getValue() != null)
                            addAttributeToCluster(cluster, xgmmlAttribute);
                    } else if(gnnEntity instanceof Pfam) {
                        Pfam pfam = (Pfam)gnnEntity;
                        if(xgmmlAttribute.getValue() != null)
                            addAttributeToPfam(pfam, xgmmlAttribute);
                    } else if(gnnEntity instanceof Neighborhood) {
                        Neighborhood neighborhood = (Neighborhood) gnnEntity;
                        if(xgmmlAttribute.getValue() != null)
                            addAttributeToNeighborhood(neighborhood, xgmmlAttribute);
                    }
                }
            } else {
                if(xgmmlEntity instanceof XgmmlGraph) {
                    return null;
                } else if(xgmmlEntity instanceof XgmmlEdge) {
                    GnnEntity gnnEntity = entityList.get(entityList.size()-1);
                    entityList.remove(entityList.size()-1);
                    return gnnEntity;
                } else if(xgmmlEntity instanceof XgmmlNode) {
                    GnnEntity gnnEntity = entityList.get(entityList.size()-1);
                    entityList.remove(entityList.size()-1);
                    return gnnEntity;
                }
            }
        }
        return null;
    }

    private void addAttributeToNeighborhood(Neighborhood neighborhood, XgmmlAttribute xgmmlAttribute) {
        switch (xgmmlAttribute.getName()) {
            /*case "SSNClusterSize":
                neighborhood.setSsnClusterSize(xgmmlAttribute.getValue());
                break;*/
        }
    }



    private void addAttributeToPfam(Pfam pfam, XgmmlAttribute xgmmlAttribute) {
        switch (xgmmlAttribute.getName()) {
            case "node.size":
                pfam.setNodeSize(xgmmlAttribute.getValue());
                break;
            case "node.shape":
                pfam.setNodeShape(xgmmlAttribute.getValue());
                break;
            case "Pfam":
                pfam.setPfam(xgmmlAttribute.getValue());
                break;
            case "Pfam Description":
                pfam.setPfamDescription(xgmmlAttribute.getValue());
                break;
            case "Total SSN Sequences":
                pfam.setTotalSsnSequences(xgmmlAttribute.getValue());
                break;
            case "Queriable SSN Sequences":
                pfam.setQueriableSsnSequences(xgmmlAttribute.getValue());
                break;
            case "Queries with Pfam Neighbors":
                pfam.setQueriesWithPfamNeighbors(xgmmlAttribute.getValue());
                break;
            case "Pfam Neighbors":
                pfam.setPfamNeighbors(xgmmlAttribute.getValue());
                break;
            case "node.fillColor":
                pfam.setNodeFillColor(xgmmlAttribute.getValue());
                break;
            case "Query-Neighbor Accessions":
                pfam.getQueryNeighborAccessions().add(xgmmlAttribute.getValue());
                break;
            case "Query-Neighbor Arrangement":
                pfam.getQueryNeighborArrangement().add(xgmmlAttribute.getValue());
                break;
            case "Hub Average and Median Distances":
                pfam.getHubAverageAndMedianDistances().add(xgmmlAttribute.getValue());
                break;
            case "Hub Co-occurrence and Ratio":
                pfam.getHubCooccurrenceAndRatio().add(xgmmlAttribute.getValue());
                break;
        }
    }

    private void addAttributeToCluster(Cluster cluster, XgmmlAttribute xgmmlAttribute) {
        switch (xgmmlAttribute.getName()) {
            case "node.fillColor":
                cluster.setNodeFillColor(xgmmlAttribute.getValue());
                break;
            case "Co-occurrence":
                cluster.setCoOccurrence(xgmmlAttribute.getValue());
                break;
            case "Co-occurrence Ratio":
                cluster.setCoOccurrenceRatio(xgmmlAttribute.getValue());
                break;
            case "Cluster Number":
                cluster.setClusterNumber(xgmmlAttribute.getValue());
                break;
            case "Total SSN Sequences":
                cluster.setTotalSsnSequences(xgmmlAttribute.getValue());
                break;
            case "Queries with Pfam Neighbors":
                cluster.setQueriesWithPfamNeighbors(xgmmlAttribute.getValue());
                break;
            case "Queriable SSN Sequences":
                cluster.setQueriableSsnSequences(xgmmlAttribute.getValue());
                break;
            case "node.size":
                cluster.setNodeSize(xgmmlAttribute.getValue());
                break;
            case "node.shape":
                cluster.setNodeShape(xgmmlAttribute.getValue());
                break;
            case "Average Distance":
                cluster.setAverageDistance(xgmmlAttribute.getValue());
                break;
            case "Median Distance":
                cluster.setMedianDistance(xgmmlAttribute.getValue());
                break;
            case "Pfam Neighbors":
                cluster.setPfamNeighbors(xgmmlAttribute.getValue());
                break;
            case "Query Accessions":
                cluster.getQueryAccessions().add(xgmmlAttribute.getValue());
                break;
            case "Query-Neighbor Accessions":
                cluster.getQueryNeighborAccessions().add(xgmmlAttribute.getValue());
                break;
            case "Query-Neighbor Arrangement":
                cluster.getQueryNeighborArrangement().add(xgmmlAttribute.getValue());
                break;
        }
    }

    public void close() throws XMLStreamException {
        xgmmlEventReader.close();
    }
}
