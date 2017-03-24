package de.ur.agennt.data.xgmml;

import de.ur.agennt.entity.xgmml.XgmmlAttribute;
import de.ur.agennt.entity.xgmml.XgmmlEdge;
import de.ur.agennt.entity.xgmml.XgmmlGraph;
import de.ur.agennt.entity.xgmml.XgmmlNode;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;

/**
 * Event based XGMML Reader
 */
public class XgmmlEventReader{
    private XMLEventReader xmlEventReader;
    private XgmmlEvent nextEvent;
    private InputStream inputStream;
    /**
     * Constructor accepting an XGMML file as input
     * @param inputFile The XGMML file to be parsed
     * @throws FileNotFoundException
     * @throws XMLStreamException
     */
    public XgmmlEventReader(File inputFile) throws FileNotFoundException, XMLStreamException {
        inputStream = new FileInputStream(inputFile);
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);
        nextEvent = findNextEntity();
    }

    private XgmmlEvent findNextEntity() throws XMLStreamException {
        while(true) {
            if(xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                switch (xmlEvent.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        return parseStartElement(xmlEvent);
                    case XMLStreamConstants.END_ELEMENT:
                        return parseEndElement(xmlEvent);
                    default:
                        break;
                }
            } else {
                return null;
            }
        }
    }

    private XgmmlEvent parseStartElement(XMLEvent xmlEvent) {
        StartElement startElement = xmlEvent.asStartElement();

        Attribute labelAttribute = startElement.getAttributeByName(QName.valueOf("label"));
        Attribute idAttribute = startElement.getAttributeByName(QName.valueOf("id"));
        Attribute sourceAttribute = startElement.getAttributeByName(QName.valueOf("source"));
        Attribute targetAttribute = startElement.getAttributeByName(QName.valueOf("target"));
        Attribute typeAttribute = startElement.getAttributeByName(QName.valueOf("type"));
        Attribute nameAttribute = startElement.getAttributeByName(QName.valueOf("name"));
        Attribute valueAttribute = startElement.getAttributeByName(QName.valueOf("value"));

        String label = null;
        String id = null;
        String source = null;
        String target = null;
        String type = null;
        String name = null;
        String value = null;

        if(labelAttribute != null) label = labelAttribute.getValue();
        if(idAttribute != null) id = idAttribute.getValue();
        if(sourceAttribute != null) source = sourceAttribute.getValue();
        if(targetAttribute != null) target = targetAttribute.getValue();
        if(typeAttribute != null) type = typeAttribute.getValue();
        if(nameAttribute != null) name = nameAttribute.getValue();
        if(valueAttribute != null) value = valueAttribute.getValue();

        switch (startElement.getName().getLocalPart()) {
            case "graph":
                return new XgmmlEvent(new XgmmlGraph(label), XgmmlEventType.BEGIN);
            case "node":
                return new XgmmlEvent(new XgmmlNode(id, label), XgmmlEventType.BEGIN);
            case "edge":
                return new XgmmlEvent(new XgmmlEdge(id, label, source, target), XgmmlEventType.BEGIN);
            case "att":
                return new XgmmlEvent(new XgmmlAttribute(type, name, value), XgmmlEventType.BEGIN);
            default:
                return null;
        }
    }

    private XgmmlEvent parseEndElement(XMLEvent element) {
        EndElement endElement = element.asEndElement();

        switch (endElement.getName().getLocalPart()) {
            case "graph":
                return new XgmmlEvent(new XgmmlGraph(), XgmmlEventType.END);
            case "node":
                return new XgmmlEvent(new XgmmlNode(), XgmmlEventType.END);
            case "edge":
                return new XgmmlEvent(new XgmmlEdge(), XgmmlEventType.END);
            case "att":
                return new XgmmlEvent(new XgmmlAttribute(), XgmmlEventType.END);
            default:
                return null;
        }
    }

    /**
     * Check if there are still unparsed XGMML elements in the file
     * @return true: there are still elements, false: all elements have been parsed
     */
    public boolean hasNext() {
        return nextEvent != null;
    }

    /**
     * Return the next unparsed XGMML element
     * @return An event object containing the XGMML element and the element type
     */
    public XgmmlEvent next() throws XMLStreamException {
        if (hasNext()) {
            XgmmlEvent returnEntity = nextEvent;
            nextEvent = findNextEntity();
            return returnEntity;
        } else {
            return null;
        }
    }

    public void close() throws XMLStreamException {
        xmlEventReader.close();
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new XMLStreamException(e);
        }
    }
}
