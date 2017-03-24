package de.ur.agennt.data.ssn;

import de.ur.agennt.entity.ssn.*;
import de.ur.agennt.entity.xgmml.*;
import de.ur.agennt.data.xgmml.XgmmlEvent;
import de.ur.agennt.data.xgmml.XgmmlEventReader;
import de.ur.agennt.data.xgmml.XgmmlEventType;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class SsnEventReader {
    private XgmmlEventReader xgmmlEventReader;
    private SsnEntity nextEntity;
    private List<SsnEntity> entityList;

    public SsnEventReader(File inputFile) throws FileNotFoundException, XMLStreamException {
        xgmmlEventReader = new XgmmlEventReader(inputFile);
        nextEntity = findNextEntity();
        entityList = new ArrayList<>();
    }

    private SsnEntity findNextEntity() throws XMLStreamException {
        while (xgmmlEventReader.hasNext()) {
            XgmmlEvent xgmmlEvent = xgmmlEventReader.next();
            XgmmlEventType xgmmlEventType = xgmmlEvent.getEventType();
            XgmmlEntity xgmmlEntity = xgmmlEvent.getXgmmlEntity();
            if (xgmmlEventType == XgmmlEventType.BEGIN) {
                if (xgmmlEntity instanceof XgmmlGraph) {
                    XgmmlGraph xgmmlGraph = (XgmmlGraph) xgmmlEntity;
                    return new Ssn(xgmmlGraph.getLabel());
                } else if (xgmmlEntity instanceof XgmmlEdge) {
                    XgmmlEdge xgmmlEdge = (XgmmlEdge) xgmmlEntity;
                    Similarity similarity = new Similarity(xgmmlEdge.getId(), xgmmlEdge.getLabel(),
                            xgmmlEdge.getSource(), xgmmlEdge.getTarget());
                    entityList.add(similarity);
                } else if (xgmmlEntity instanceof XgmmlNode) {
                    XgmmlNode xgmmlNode = (XgmmlNode) xgmmlEntity;
                    Sequence sequence = new Sequence(xgmmlNode.getId(), xgmmlNode.getLabel());
                    entityList.add(sequence);
                } else {
                    XgmmlAttribute xgmmlAttribute = (XgmmlAttribute) xgmmlEntity;
                    if (xgmmlAttribute.getName().equals("ACC")) {
                        if (entityList.get(entityList.size() - 1) instanceof Sequence) {
                            Sequence sequence = (Sequence) entityList.get(entityList.size() - 1);
                            entityList.remove(entityList.size() - 1);
                            RepresentativeSequence repSequence = new RepresentativeSequence(sequence.getId(), sequence.getLabel());
                            entityList.add(repSequence);
                        }
                    }
                    SsnEntity ssnEntity = entityList.get(entityList.size() - 1);
                    if (ssnEntity instanceof Sequence) {
                        Sequence sequence = (Sequence) ssnEntity;
                        if (xgmmlAttribute.getValue() != null)
                            addAttributeToSequence(sequence, xgmmlAttribute);
                    } else if (ssnEntity instanceof RepresentativeSequence) {
                        RepresentativeSequence repSequence = (RepresentativeSequence) ssnEntity;
                        if (xgmmlAttribute.getValue() != null)
                            addAttributeToRepresentativeSequence(repSequence, xgmmlAttribute);
                    } else if (ssnEntity instanceof Similarity) {
                        Similarity similarity = (Similarity) ssnEntity;
                        if (xgmmlAttribute.getValue() != null)
                            addAttributeToSimilarity(similarity, xgmmlAttribute);
                    }
                }
            } else {
                if (xgmmlEntity instanceof XgmmlGraph) {
                    return null;
                } else if (xgmmlEntity instanceof XgmmlEdge) {
                    SsnEntity ssnEntity = entityList.get(entityList.size() - 1);
                    entityList.remove(entityList.size() - 1);
                    return ssnEntity;
                } else if (xgmmlEntity instanceof XgmmlNode) {
                    SsnEntity ssnEntity = entityList.get(entityList.size() - 1);
                    entityList.remove(entityList.size() - 1);
                    return ssnEntity;
                }
            }
        }
        return null;
    }

    private void addAttributeToSimilarity(Similarity similarity, XgmmlAttribute xgmmlAttribute) {
        switch (xgmmlAttribute.getName()) {
            case "-log10(E)":
                similarity.setAlignmentScore(xgmmlAttribute.getValue());
                break;
            case "%id":
                similarity.setPercId(xgmmlAttribute.getValue());
                break;
            case "alignment_score":
                similarity.setAlignmentScore(xgmmlAttribute.getValue());
                break;
            case "alignment_length":
                similarity.setAlignmentLen(xgmmlAttribute.getValue());
                break;
        }
    }

    private void addAttributeToRepresentativeSequence(RepresentativeSequence sequence, XgmmlAttribute xgmmlAttribute) {
        switch (xgmmlAttribute.getName()) {
            case "ACC":
                sequence.getAcc().add(xgmmlAttribute.getValue());
                break;
            case "Cluster Size":
                sequence.setClusterSize(xgmmlAttribute.getValue());
                break;
            case "Uniprot_ID":
                sequence.getUniprotId().add(xgmmlAttribute.getValue());
                break;
            case "STATUS":
                sequence.getStatus().add(xgmmlAttribute.getValue());
                break;
            case "Sequence_Length":
                sequence.getSequenceLength().add(xgmmlAttribute.getValue());
                break;
            case "Taxonomy_ID":
                sequence.getTaxonomyId().add(xgmmlAttribute.getValue());
                break;
            case "GDNA":
                sequence.getGdna().add(xgmmlAttribute.getValue());
                break;
            case "Description":
                sequence.getDescription().add(xgmmlAttribute.getValue());
                break;
            case "Swissprot_Description":
                sequence.getSwissprotDescription().add(xgmmlAttribute.getValue());
                break;
            case "Organism":
                sequence.getOrganism().add(xgmmlAttribute.getValue());
                break;
            case "Domain":
                sequence.getDomain().add(xgmmlAttribute.getValue());
                break;
            case "GN":
                sequence.getGn().add(xgmmlAttribute.getValue());
                break;
            case "PFAM":
                sequence.getPfam().add(xgmmlAttribute.getValue());
                break;
            case "PDB":
                sequence.getPdb().add(xgmmlAttribute.getValue());
                break;
            case "IPRO":
                sequence.getIpro().add(xgmmlAttribute.getValue());
                break;
            case "GO":
                sequence.getGo().add(xgmmlAttribute.getValue());
                break;
            case "GI":
                sequence.getGi().add(xgmmlAttribute.getValue());
                break;
            case "HMP_Body_Site":
                sequence.getHmpBodySite().add(xgmmlAttribute.getValue());
                break;
            case "HMP_Oxygen":
                sequence.getHmpOxygen().add(xgmmlAttribute.getValue());
                break;
            case "EFI_ID":
                sequence.getEfiId().add(xgmmlAttribute.getValue());
                break;
            case "EC":
                sequence.getEc().add(xgmmlAttribute.getValue());
                break;
            case "PHYLUM":
                sequence.getPhylum().add(xgmmlAttribute.getValue());
                break;
            case "CLASS":
                sequence.getClazz().add(xgmmlAttribute.getValue());
                break;
            case "ORDER":
                sequence.getOrder().add(xgmmlAttribute.getValue());
                break;
            case "FAMILY":
                sequence.getFamily().add(xgmmlAttribute.getValue());
                break;
            case "GENUS":
                sequence.getGenus().add(xgmmlAttribute.getValue());
                break;
            case "SPECIES":
                sequence.getSpecies().add(xgmmlAttribute.getValue());
                break;
            case "CAZY":
                sequence.getCazy().add(xgmmlAttribute.getValue());
                break;

        }
    }

    private void addAttributeToSequence(Sequence sequence, XgmmlAttribute xgmmlAttribute) {
        switch (xgmmlAttribute.getName()) {
            case "Uniprot_ID":
                sequence.setUniprotId(xgmmlAttribute.getValue());
                break;
            case "STATUS":
                sequence.setStatus(xgmmlAttribute.getValue());
                break;
            case "Sequence_Length":
                sequence.setSequenceLength(xgmmlAttribute.getValue());
                break;
            case "Taxonomy_ID":
                sequence.setTaxonomyId(xgmmlAttribute.getValue());
                break;
            case "GDNA":
                sequence.setGdna(xgmmlAttribute.getValue());
                break;
            case "Description":
                sequence.setDescription(xgmmlAttribute.getValue());
                break;
            case "Swissprot_Description":
                sequence.setSwissprotDescription(xgmmlAttribute.getValue());
                break;
            case "Organism":
                sequence.setOrganism(xgmmlAttribute.getValue());
                break;
            case "Domain":
                sequence.setDomain(xgmmlAttribute.getValue());
                break;
            case "GN":
                sequence.setGn(xgmmlAttribute.getValue());
                break;
            case "PFAM":
                sequence.getPfam().add(xgmmlAttribute.getValue());
                break;
            case "PDB":
                sequence.getPdb().add(xgmmlAttribute.getValue());
                break;
            case "IPRO":
                sequence.getIpro().add(xgmmlAttribute.getValue());
                break;
            case "GO":
                sequence.getGo().add(xgmmlAttribute.getValue());
                break;
            case "GI":
                sequence.getGi().add(xgmmlAttribute.getValue());
                break;
            case "HMP_Body_Site":
                sequence.getHmpBodySite().add(xgmmlAttribute.getValue());
                break;
            case "HMP_Oxygen":
                sequence.setHmpOxygen(xgmmlAttribute.getValue());
                break;
            case "EFI_ID":
                sequence.setEfiId(xgmmlAttribute.getValue());
                break;
            case "EC":
                sequence.setEc(xgmmlAttribute.getValue());
                break;
            case "PHYLUM":
                sequence.setPhylum(xgmmlAttribute.getValue());
                break;
            case "CLASS":
                sequence.setClazz(xgmmlAttribute.getValue());
                break;
            case "ORDER":
                sequence.setOrder(xgmmlAttribute.getValue());
                break;
            case "FAMILY":
                sequence.setFamily(xgmmlAttribute.getValue());
                break;
            case "GENUS":
                sequence.setGenus(xgmmlAttribute.getValue());
                break;
            case "SPECIES":
                sequence.setSpecies(xgmmlAttribute.getValue());
                break;
            case "CAZY":
                sequence.getCazy().add(xgmmlAttribute.getValue());
                break;

        }
    }

    public boolean hasNext() {
        return nextEntity != null;
    }

    public SsnEntity next() throws XMLStreamException {
        if (hasNext()) {
            SsnEntity returnValue = nextEntity;
            nextEntity = findNextEntity();
            return returnValue;
        } else {
            return null;
        }
    }

    public void close() throws XMLStreamException {
        xgmmlEventReader.close();
    }
}
