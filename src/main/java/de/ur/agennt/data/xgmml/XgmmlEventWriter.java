package de.ur.agennt.data.xgmml;

import de.ur.agennt.entity.xgmml.*;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Event based XGMML Writer http://www.cs.rpi.edu/XGMML
 */
public class XgmmlEventWriter {
    private XMLEventFactory xmlEventFactory;
    private XMLEventWriter xmlEventWriter;
    private FileOutputStream fileOutputStream;

    public XgmmlEventWriter(File outputFile) throws FileNotFoundException, XMLStreamException {
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        fileOutputStream = new FileOutputStream(outputFile);
        xmlEventWriter = xmlOutputFactory.createXMLEventWriter(fileOutputStream);
        xmlEventFactory = XMLEventFactory.newInstance();
        xmlEventWriter.add(xmlEventFactory.createStartDocument());
    }

    public void add(XgmmlEvent xgmmlEvent) throws XMLStreamException {
        switch (xgmmlEvent.getEventType()) {
            case BEGIN:
                writeStartElement(xgmmlEvent.getXgmmlEntity());
                break;
            case END:
                writeEndElement(xgmmlEvent.getXgmmlEntity());
                break;
        }
    }

    public void close() throws XMLStreamException {
        xmlEventWriter.add(xmlEventFactory.createEndDocument());
        xmlEventWriter.close();
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            throw new XMLStreamException();
        }
    }

    private void writeStartElement(XgmmlEntity entity) throws XMLStreamException {
        if(entity instanceof XgmmlGraph) {
            XgmmlGraph xgmmlGraph = (XgmmlGraph)entity;
            xmlEventWriter.add(xmlEventFactory.createStartElement("","", "graph"));
            xmlEventWriter.add(xmlEventFactory.createNamespace("ns0", "http://www.cs.rpi.edu/XGMML"));
            if(xgmmlGraph.getLabel() != null)
                xmlEventWriter.add(xmlEventFactory.createAttribute("label",xgmmlGraph.getLabel()));
        } else if(entity instanceof XgmmlNode) {
            XgmmlNode xgmmlNode = (XgmmlNode)entity;
            xmlEventWriter.add(xmlEventFactory.createStartElement("","", "node"));
            if(xgmmlNode.getLabel() != null)
                xmlEventWriter.add(xmlEventFactory.createAttribute("label",xgmmlNode.getLabel()));
            if(xgmmlNode.getId() != null)
                xmlEventWriter.add(xmlEventFactory.createAttribute("id",xgmmlNode.getId()));
        } else if(entity instanceof XgmmlEdge) {
            XgmmlEdge xgmmlEdge = (XgmmlEdge)entity;
            xmlEventWriter.add(xmlEventFactory.createStartElement("","", "edge"));
            if(xgmmlEdge.getLabel() != null)
                xmlEventWriter.add(xmlEventFactory.createAttribute("label",xgmmlEdge.getLabel()));
            if(xgmmlEdge.getId() != null)
                xmlEventWriter.add(xmlEventFactory.createAttribute("id",xgmmlEdge.getId()));
            if(xgmmlEdge.getSource() != null)
                xmlEventWriter.add(xmlEventFactory.createAttribute("source",xgmmlEdge.getSource()));
            if(xgmmlEdge.getTarget() != null)
                xmlEventWriter.add(xmlEventFactory.createAttribute("target",xgmmlEdge.getTarget()));
        } else if(entity instanceof XgmmlAttribute) {
            XgmmlAttribute xgmmlAttribute = (XgmmlAttribute)entity;
            xmlEventWriter.add(xmlEventFactory.createStartElement("","", "att"));
            if(xgmmlAttribute.getName() != null)
                xmlEventWriter.add(xmlEventFactory.createAttribute("name",xgmmlAttribute.getName()));
            if(xgmmlAttribute.getType() != null)
                xmlEventWriter.add(xmlEventFactory.createAttribute("type",xgmmlAttribute.getType()));
            if(xgmmlAttribute.getValue() != null)
                xmlEventWriter.add(xmlEventFactory.createAttribute("value",xgmmlAttribute.getValue()));
        }
    }

    private void writeEndElement(XgmmlEntity entity) throws XMLStreamException {
        if(entity instanceof XgmmlGraph) {
            xmlEventWriter.add(xmlEventFactory.createEndElement("","", "graph"));
        } else if(entity instanceof XgmmlNode) {
            xmlEventWriter.add(xmlEventFactory.createEndElement("","","node"));
        } else if(entity instanceof XgmmlEdge) {
            xmlEventWriter.add(xmlEventFactory.createEndElement("","","edge"));
        } else if(entity instanceof XgmmlAttribute) {
            xmlEventWriter.add(xmlEventFactory.createEndElement("","","att"));
        }
    }

}
