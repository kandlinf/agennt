package de.ur.agennt.service.ssn;

import de.ur.agennt.data.DataFacade;
import de.ur.agennt.entity.ssn.*;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TaxonomyFilter {
    private DataFacade dataFacade;
    private File inputSsn;
    private File outputSsn;
    private File workingSsn;
    private Set<String> removedNodesSet;

    public TaxonomyFilter(File inputFile, File workingFile, File outputFile) {
        dataFacade = DataFacade.getInstance();
        inputSsn = inputFile;
        outputSsn = outputFile;
        workingSsn = workingFile;
        removedNodesSet = new HashSet<>();
    }

    public void filter() throws FileNotFoundException, XMLStreamException {
        filterSpecies();
        removeEdges();
    }

    private void removeEdges() throws XMLStreamException, FileNotFoundException {
        SsnEntity entity = dataFacade.readSsnEvent(workingSsn);
        while (entity != null) {
            if(entity instanceof Similarity) {
                Similarity similarity = (Similarity)entity;
                if(!removedNodesSet.contains(similarity.getSource()) && !removedNodesSet.contains(similarity.getTarget())) {
                    dataFacade.writeSsnEvent(outputSsn, entity);
                }
            } else {
                dataFacade.writeSsnEvent(outputSsn, entity);
            }
            entity = dataFacade.readSsnEvent(workingSsn);
        }
        dataFacade.writeSsnEvent(outputSsn, null);

    }

    private void filterSpecies() throws XMLStreamException, FileNotFoundException {
        SsnEntity entity = dataFacade.readSsnEvent(inputSsn);
        while(entity != null) {
            if(entity instanceof Sequence) {
                Sequence sequence = (Sequence)entity;
                if(dataFacade.isSpecies(sequence.getTaxonomyId())) {
                    dataFacade.writeSsnEvent(workingSsn, entity);
                } else {
                    removedNodesSet.add(sequence.getId());
                }
            } else if(entity instanceof RepresentativeSequence) {
                RepresentativeSequence representativeSequence = (RepresentativeSequence)entity;
                List<Integer> toBeRemoved = new ArrayList<>();
                for(int i = 0; i < representativeSequence.getTaxonomyId().size(); i++) {
                    if(!dataFacade.isSpecies(representativeSequence.getTaxonomyId().get(i))) {
                        toBeRemoved.add(i);
                    }
                }
                for(Integer i : toBeRemoved) {
                    representativeSequence.removeEntry(i);
                }
                if(representativeSequence.getTaxonomyId().size() > 0) {
                    dataFacade.writeSsnEvent(workingSsn, entity);
                } else {
                    removedNodesSet.add(representativeSequence.getId());
                }
            } else if(entity instanceof Similarity) {
                Similarity similarity = (Similarity)entity;
                dataFacade.writeSsnEvent(workingSsn, entity);
            } else if(entity instanceof Ssn ){
                Ssn ssn = (Ssn)entity;
                dataFacade.writeSsnEvent(workingSsn, entity);
            }
            entity = dataFacade.readSsnEvent(inputSsn);
        }
        dataFacade.writeSsnEvent(workingSsn, null);
    }
}
