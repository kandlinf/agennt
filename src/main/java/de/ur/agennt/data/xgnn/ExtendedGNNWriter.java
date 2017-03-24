package de.ur.agennt.data.xgnn;

import de.ur.agennt.data.xgmml.XgmmlEvent;
import de.ur.agennt.data.xgmml.XgmmlEventType;
import de.ur.agennt.data.xgmml.XgmmlEventWriter;
import de.ur.agennt.entity.gnn.*;
import de.ur.agennt.entity.xgmml.XgmmlAttribute;
import de.ur.agennt.entity.xgmml.XgmmlEdge;
import de.ur.agennt.entity.xgmml.XgmmlGraph;
import de.ur.agennt.entity.xgmml.XgmmlNode;
import de.ur.agennt.entity.xgnn.*;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by kandlinf on 14.06.2016.
 */
public class ExtendedGNNWriter {
    private XgmmlEventWriter xgmmlEventWriter;

    public ExtendedGNNWriter(File outputFile) throws FileNotFoundException, XMLStreamException {
        xgmmlEventWriter = new XgmmlEventWriter(outputFile);
    }

    public void add(XGnnEntity gnnEntity) throws XMLStreamException {
        if(gnnEntity instanceof XGnn) {
            writeXGnn((XGnn) gnnEntity);
        } else if(gnnEntity instanceof Group) {
            writeGroup((Group) gnnEntity);
        } else if(gnnEntity instanceof Family) {
            writeFamily((Family) gnnEntity);
        } else if(gnnEntity instanceof Relationship) {
            writeRelationship((Relationship) gnnEntity);
        }
    }

    private void writeRelationship(Relationship relationship) throws XMLStreamException {
        XgmmlEdge xgmmlEdge = new XgmmlEdge("", relationship.getLabel(),
                relationship.getSource(), relationship.getTarget());
        XgmmlEvent xgmmlEvent = new XgmmlEvent(xgmmlEdge, XgmmlEventType.BEGIN);
        xgmmlEventWriter.add(xgmmlEvent);

        writeAttribute("integer", "SeqCount", relationship.getSeqCount());
        writeAttribute("real", "Uniqueness", relationship.getUniqueness());
        writeAttribute("real", "Coverage", relationship.getCoverage());
        xgmmlEvent = new XgmmlEvent(xgmmlEdge, XgmmlEventType.END);
        xgmmlEventWriter.add(xgmmlEvent);
    }

    private void writeFamily(Family family) throws XMLStreamException {
        XgmmlNode xgmmlNode = new XgmmlNode(family.getId(), family.getLabel());
        XgmmlEvent xgmmlEvent = new XgmmlEvent(xgmmlNode, XgmmlEventType.BEGIN);
        xgmmlEventWriter.add(xgmmlEvent);

        writeAttribute("string", "Pfam", family.getPfam());
        writeAttribute("string", "Description", family.getDescription());
        writeAttribute("integer", "SeqCount", family.getSeqCount());
        writeAttributeList("string", "GO", family.getGo());
        writeAttribute("real", "Uniqueness", family.getUniqueness());
        writeAttribute("string", "node.size", family.getNodeSize());
        writeAttribute("string", "node.shape", "hexagon");
        //writeAttribute("string", "node.transparency", family.getNodeTransparency());

        xgmmlEvent = new XgmmlEvent(xgmmlNode, XgmmlEventType.END);
        xgmmlEventWriter.add(xgmmlEvent);
    }

    private void writeGroup(Group group) throws XMLStreamException {
        XgmmlNode xgmmlNode = new XgmmlNode(group.getId(), group.getLabel());
        XgmmlEvent xgmmlEvent = new XgmmlEvent(xgmmlNode, XgmmlEventType.BEGIN);
        xgmmlEventWriter.add(xgmmlEvent);

        writeAttribute("integer", "ClusterNumber", group.getClusterNumber());
        writeAttribute("integer", "SeqCount", group.getSeqCount());
        writeAttribute("real", "Uniqueness", group.getUniqueness());
        writeAttribute("string", "node.fillColor", group.getNodeFillColor());
        writeAttribute("string", "node.size", group.getNodeSize());
        writeAttribute("string", "node.shape", group.getNodeShape());
        writeAttribute("string", "NodeCount", group.getNodeCount());
        writeAttributeList("string", "PhylumStat", group.getPhylumStat());
        //writeAttribute("string", "node.transparency", group.getNodeTransparency());

        xgmmlEvent = new XgmmlEvent(xgmmlNode, XgmmlEventType.END);
        xgmmlEventWriter.add(xgmmlEvent);
    }

    private void writeXGnn(XGnn xgnn) throws XMLStreamException {
        XgmmlGraph xgmmlGraph = new XgmmlGraph(xgnn.getLabel());
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
