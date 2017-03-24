package de.ur.agennt.data.ssn;

import de.ur.agennt.entity.ssn.*;
import de.ur.agennt.entity.xgmml.XgmmlAttribute;
import de.ur.agennt.entity.xgmml.XgmmlEdge;
import de.ur.agennt.entity.xgmml.XgmmlGraph;
import de.ur.agennt.entity.xgmml.XgmmlNode;
import de.ur.agennt.data.xgmml.XgmmlEvent;
import de.ur.agennt.data.xgmml.XgmmlEventType;
import de.ur.agennt.data.xgmml.XgmmlEventWriter;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class SsnEventWriter {
    private XgmmlEventWriter xgmmlEventWriter;

    public SsnEventWriter(File outputFile) throws FileNotFoundException, XMLStreamException {
        xgmmlEventWriter = new XgmmlEventWriter(outputFile);
    }

    public void add(SsnEntity ssnEntity) throws XMLStreamException {
        if(ssnEntity instanceof Ssn) {
            writeSsn((Ssn) ssnEntity);
        } else if(ssnEntity instanceof Similarity) {
            writeSimilarity((Similarity) ssnEntity);
        } else if(ssnEntity instanceof Sequence) {
            writeSequence((Sequence) ssnEntity);
        } else if(ssnEntity instanceof RepresentativeSequence) {
            writeRepSequence((RepresentativeSequence) ssnEntity);
        }

    }

    private void writeSsn(Ssn ssn) throws XMLStreamException {
        XgmmlGraph xgmmlGraph = new XgmmlGraph(ssn.getLabel());
        XgmmlEvent xgmmlEvent = new XgmmlEvent(xgmmlGraph, XgmmlEventType.BEGIN);
        xgmmlEventWriter.add(xgmmlEvent);
    }

    private void writeSimilarity(Similarity similarity) throws XMLStreamException {
        XgmmlEdge xgmmlEdge = new XgmmlEdge(similarity.getId(), similarity.getLabel(),
                similarity.getSource(), similarity.getTarget());
        XgmmlEvent xgmmlEvent = new XgmmlEvent(xgmmlEdge, XgmmlEventType.BEGIN);
        xgmmlEventWriter.add(xgmmlEvent);

        writeAttribute("real", "%id", similarity.getPercId());
        writeAttribute("real", "alignment_score", similarity.getAlignmentScore());
        writeAttribute("integer", "alignment_length", similarity.getAlignmentLen());

        xgmmlEvent = new XgmmlEvent(xgmmlEdge, XgmmlEventType.END);
        xgmmlEventWriter.add(xgmmlEvent);
    }

    private void writeSequence(Sequence sequence) throws XMLStreamException {
        XgmmlNode xgmmlNode = new XgmmlNode(sequence.getId(), sequence.getLabel());
        XgmmlEvent xgmmlEvent = new XgmmlEvent(xgmmlNode, XgmmlEventType.BEGIN);
        xgmmlEventWriter.add(xgmmlEvent);

        writeAttribute("string", "Uniprot_ID", sequence.getUniprotId());
        writeAttribute("string", "STATUS", sequence.getStatus());
        writeAttribute("integer", "Sequence_Length", sequence.getSequenceLength());
        writeAttribute("string", "Taxonomy_ID", sequence.getTaxonomyId());
        writeAttribute("string", "GDNA", sequence.getGdna());
        writeAttribute("string", "Description", sequence.getDescription());
        writeAttribute("string", "Swissprot_Description", sequence.getSwissprotDescription());
        writeAttribute("string", "Organism", sequence.getOrganism());
        writeAttribute("string", "Domain", sequence.getDomain());
        writeAttribute("string", "GN", sequence.getGn());
        writeAttributeList("string", "PFAM", sequence.getPfam());
        writeAttributeList("string", "PDB", sequence.getPdb());
        writeAttributeList("string", "IPRO", sequence.getIpro());
        writeAttributeList("string", "GO", sequence.getGo());
        writeAttributeList("string", "GI", sequence.getGi());
        writeAttributeList("string", "HMP_Body_Site", sequence.getHmpBodySite());
        writeAttribute("string", "HMP_Oxygen", sequence.getHmpOxygen());
        writeAttribute("string", "EFI_ID", sequence.getEfiId());
        writeAttribute("string", "EC", sequence.getEc());
        writeAttribute("string", "PHYLUM", sequence.getPhylum());
        writeAttribute("string", "CLASS", sequence.getClazz());
        writeAttribute("string", "ORDER", sequence.getOrder());
        writeAttribute("string", "FAMILY", sequence.getFamily());
        writeAttribute("string", "GENUS", sequence.getGenus());
        writeAttribute("string", "SPECIES", sequence.getSpecies());
        writeAttributeList("string", "CAZY", sequence.getCazy());

        xgmmlEvent = new XgmmlEvent(xgmmlNode, XgmmlEventType.END);
        xgmmlEventWriter.add(xgmmlEvent);
    }

    private void writeRepSequence(RepresentativeSequence sequence) throws XMLStreamException {
        XgmmlNode xgmmlNode = new XgmmlNode(sequence.getId(), sequence.getLabel());
        XgmmlEvent xgmmlEvent = new XgmmlEvent(xgmmlNode, XgmmlEventType.BEGIN);
        xgmmlEventWriter.add(xgmmlEvent);

        writeAttributeList("string", "ACC", sequence.getAcc());
        writeAttributeList("string", "Uniprot_ID", sequence.getUniprotId());
        writeAttributeList("string", "STATUS", sequence.getStatus());
        writeAttributeList("integer", "Sequence_Length", sequence.getSequenceLength());
        writeAttributeList("string", "Taxonomy_ID", sequence.getTaxonomyId());
        writeAttributeList("string", "GDNA", sequence.getGdna());
        writeAttributeList("string", "Description", sequence.getDescription());
        writeAttributeList("string", "Swissprot_Description", sequence.getSwissprotDescription());
        writeAttributeList("string", "Organism", sequence.getOrganism());
        writeAttributeList("string", "Domain", sequence.getDomain());
        writeAttributeList("string", "GN", sequence.getGn());
        writeAttributeList("string", "PFAM", sequence.getPfam());
        writeAttributeList("string", "PDB", sequence.getPdb());
        writeAttributeList("string", "IPRO", sequence.getIpro());
        writeAttributeList("string", "GO", sequence.getGo());
        writeAttributeList("string", "GI", sequence.getGi());
        writeAttributeList("string", "HMP_Body_Site", sequence.getHmpBodySite());
        writeAttributeList("string", "HMP_Oxygen", sequence.getHmpOxygen());
        writeAttributeList("string", "EFI_ID", sequence.getEfiId());
        writeAttributeList("string", "EC", sequence.getEc());
        writeAttributeList("string", "PHYLUM", sequence.getPhylum());
        writeAttributeList("string", "CLASS", sequence.getClazz());
        writeAttributeList("string", "ORDER", sequence.getOrder());
        writeAttributeList("string", "FAMILY", sequence.getFamily());
        writeAttributeList("string", "GENUS", sequence.getGenus());
        writeAttributeList("string", "SPECIES", sequence.getSpecies());
        writeAttributeList("string", "CAZY", sequence.getCazy());
        writeAttribute("integer", "Cluster Size", sequence.getClusterSize());
        xgmmlEvent = new XgmmlEvent(xgmmlNode, XgmmlEventType.END);
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
