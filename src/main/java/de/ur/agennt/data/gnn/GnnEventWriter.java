package de.ur.agennt.data.gnn;

import de.ur.agennt.data.xgmml.XgmmlEvent;
import de.ur.agennt.data.xgmml.XgmmlEventType;
import de.ur.agennt.data.xgmml.XgmmlEventWriter;
import de.ur.agennt.entity.gnn.*;
import de.ur.agennt.entity.ssn.*;
import de.ur.agennt.entity.xgmml.XgmmlAttribute;
import de.ur.agennt.entity.xgmml.XgmmlEdge;
import de.ur.agennt.entity.xgmml.XgmmlGraph;
import de.ur.agennt.entity.xgmml.XgmmlNode;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class GnnEventWriter {
    private XgmmlEventWriter xgmmlEventWriter;

    public GnnEventWriter(File outputFile) throws FileNotFoundException, XMLStreamException {
        xgmmlEventWriter = new XgmmlEventWriter(outputFile);
    }

    public void add(GnnEntity gnnEntity) throws XMLStreamException {
        if(gnnEntity instanceof Gnn) {
            writeGnn((Gnn) gnnEntity);
        } else if(gnnEntity instanceof Cluster) {
            writeCluster((Cluster) gnnEntity);
        } else if(gnnEntity instanceof Neighborhood) {
            writeNeighborhood((Neighborhood) gnnEntity);
        } else if(gnnEntity instanceof Pfam) {
            writePfam((Pfam) gnnEntity);
        }
    }

    private void writePfam(Pfam pfam) throws XMLStreamException {
        XgmmlNode xgmmlNode = new XgmmlNode(pfam.getId(), pfam.getLabel());
        XgmmlEvent xgmmlEvent = new XgmmlEvent(xgmmlNode, XgmmlEventType.BEGIN);
        xgmmlEventWriter.add(xgmmlEvent);

        writeAttribute("string", "node.size", pfam.getNodeSize());
        writeAttribute("string", "node.shape", pfam.getNodeShape());
        writeAttribute("string", "Pfam", pfam.getPfam());
        writeAttribute("string", "Pfam Description", pfam.getPfamDescription());
        writeAttribute("integer", "Total SSN Sequences", pfam.getTotalSsnSequences());
        writeAttribute("integer", "Queriable SSN Sequences", pfam.getQueriableSsnSequences());
        writeAttribute("integer", "Queries with Pfam Neighbors", pfam.getQueriesWithPfamNeighbors());
        writeAttribute("integer", "Pfam Neighbors", pfam.getPfamNeighbors());
        writeAttribute("string", "node.fillColor", pfam.getNodeFillColor());
        writeAttributeList("string", "Query-Neighbor Accessions", pfam.getQueryNeighborAccessions());
        writeAttributeList("string", "Query-Neighbor Arrangement", pfam.getQueryNeighborArrangement());
        writeAttributeList("string", "Hub Average and Median Distances", pfam.getHubAverageAndMedianDistances());
        writeAttributeList("string", "Hub Co-occurrence and Ratio", pfam.getHubCooccurrenceAndRatio());

        xgmmlEvent = new XgmmlEvent(xgmmlNode, XgmmlEventType.END);
        xgmmlEventWriter.add(xgmmlEvent);
    }

    private void writeCluster(Cluster cluster) throws XMLStreamException {
        XgmmlNode xgmmlNode = new XgmmlNode(cluster.getId(), cluster.getLabel());
        XgmmlEvent xgmmlEvent = new XgmmlEvent(xgmmlNode, XgmmlEventType.BEGIN);
        xgmmlEventWriter.add(xgmmlEvent);

        writeAttribute("string", "node.fillColor", cluster.getNodeFillColor());
        writeAttribute("real", "Co-occurrence", cluster.getCoOccurrence());
        writeAttribute("string", "Co-occurrence Ratio", cluster.getCoOccurrenceRatio());
        writeAttribute("integer", "Cluster Number", cluster.getClusterNumber());
        writeAttribute("integer", "Total SSN Sequences", cluster.getTotalSsnSequences());
        writeAttribute("integer", "Queries with Pfam Neighbors", cluster.getQueriesWithPfamNeighbors());
        writeAttribute("integer", "Queriable SSN Sequences", cluster.getQueriableSsnSequences());
        writeAttribute("string", "node.size", cluster.getNodeSize());
        writeAttribute("string", "node.shape", cluster.getNodeShape());
        writeAttribute("real", "Average Distance", cluster.getAverageDistance());
        writeAttribute("real", "Median Distance", cluster.getMedianDistance());
        writeAttribute("integer", "Pfam Neighbors", cluster.getPfamNeighbors());
        writeAttributeList("string", "Query Accessions", cluster.getQueryAccessions());
        writeAttributeList("string", "Query-Neighbor Accessions", cluster.getQueryNeighborAccessions());
        writeAttributeList("string", "Query-Neighbor Arrangement", cluster.getQueryNeighborArrangement());

        xgmmlEvent = new XgmmlEvent(xgmmlNode, XgmmlEventType.END);
        xgmmlEventWriter.add(xgmmlEvent);
    }

    private void writeNeighborhood(Neighborhood neighborhood) throws XMLStreamException {
        XgmmlEdge xgmmlEdge = new XgmmlEdge("", neighborhood.getLabel(),
                neighborhood.getSource(), neighborhood.getTarget());
        XgmmlEvent xgmmlEvent = new XgmmlEvent(xgmmlEdge, XgmmlEventType.BEGIN);
        xgmmlEventWriter.add(xgmmlEvent);

        //writeAttribute("string", "SSNClusterSize", neighborhood.getSsnClusterSize());

        xgmmlEvent = new XgmmlEvent(xgmmlEdge, XgmmlEventType.END);
        xgmmlEventWriter.add(xgmmlEvent);
    }

    private void writeGnn(Gnn gnn) throws XMLStreamException {
        XgmmlGraph xgmmlGraph = new XgmmlGraph(gnn.getLabel());
        XgmmlEvent xgmmlEvent = new XgmmlEvent(xgmmlGraph, XgmmlEventType.BEGIN);
        xgmmlEventWriter.add(xgmmlEvent);
    }

    private void writeAttribute(String type, String name, String value) throws XMLStreamException {
        if(value != null) {
            XgmmlAttribute xgmmlAttribute = new XgmmlAttribute(type, name, value);
            XgmmlEvent xgmmlEvent = new XgmmlEvent(xgmmlAttribute, XgmmlEventType.BEGIN);
            xgmmlEventWriter.add(xgmmlEvent);
            xgmmlEvent = new XgmmlEvent(xgmmlAttribute, XgmmlEventType.END);
            xgmmlEventWriter.add(xgmmlEvent);
        }
    }

    private void writeAttributeList(String type, String name, List<String> values) throws XMLStreamException {
        if(values != null && values.size() > 0) {
            XgmmlAttribute xgmmlAttribute = new XgmmlAttribute("list", name);
            XgmmlEvent xgmmlEvent = new XgmmlEvent(xgmmlAttribute, XgmmlEventType.BEGIN);
            xgmmlEventWriter.add(xgmmlEvent);

            for (String value : values) {
                writeAttribute(type, name, value);
            }

            xgmmlEvent = new XgmmlEvent(xgmmlAttribute, XgmmlEventType.END);
            xgmmlEventWriter.add(xgmmlEvent);
        }
    }

    public void close() throws XMLStreamException {
        XgmmlGraph xgmmlGraph = new XgmmlGraph();
        XgmmlEvent xgmmlEvent = new XgmmlEvent(xgmmlGraph, XgmmlEventType.END);
        xgmmlEventWriter.add(xgmmlEvent);
        xgmmlEventWriter.close();
    }
}
