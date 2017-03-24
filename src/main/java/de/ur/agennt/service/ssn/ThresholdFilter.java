package de.ur.agennt.service.ssn;

import de.ur.agennt.data.DataFacade;
import de.ur.agennt.entity.ssn.Similarity;
import de.ur.agennt.entity.ssn.SsnEntity;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;

public class ThresholdFilter {
    private DataFacade dataFacade;
    private File inputSsn;
    private File outputSsn;

    public ThresholdFilter(File inputFile, File outputFile) {
        dataFacade = DataFacade.getInstance();
        inputSsn = inputFile;
        outputSsn = outputFile;
    }

    public void filter(Integer threshold) throws XMLStreamException, FileNotFoundException {
        SsnEntity entity = dataFacade.readSsnEvent(inputSsn);
        while(entity != null) {
            if(entity instanceof Similarity) {
                Similarity similarity = (Similarity)entity;
                Double score = Double.parseDouble(similarity.getAlignmentScore());
                if(score >= threshold) {
                    dataFacade.writeSsnEvent(outputSsn, entity);
                }
            } else {
                dataFacade.writeSsnEvent(outputSsn, entity);
            }
            entity = dataFacade.readSsnEvent(inputSsn);
        }
        dataFacade.writeSsnEvent(outputSsn, null);
    }


}
