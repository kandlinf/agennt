package de.ur.agennt.data;

import de.ur.agennt.data.colored.ColoredSsnEventReader;
import de.ur.agennt.data.enzymefunction.GnnRequester;
import de.ur.agennt.data.gnn.GnnFullReader;
import de.ur.agennt.data.gnn.GnnFullWriter;
import de.ur.agennt.data.pfam.Pfam2Go;
import de.ur.agennt.data.pfam.PfamWhitelistReader;
import de.ur.agennt.data.pfam.PfamWhitelistWriter;
import de.ur.agennt.data.project.ProjectManager;
import de.ur.agennt.data.ssn.SsnEventReader;
import de.ur.agennt.data.ssn.SsnEventWriter;
import de.ur.agennt.data.taxonomy.Taxonomy;
import de.ur.agennt.data.xgnn.ExtendedGNNWriter;
import de.ur.agennt.entity.colored.ColoredSsn;
import de.ur.agennt.entity.colored.ColoredSsnEntity;
import de.ur.agennt.entity.gnn.FullGnn;
import de.ur.agennt.entity.go.Pfam2GoEntry;
import de.ur.agennt.entity.project.Project;
import de.ur.agennt.entity.ssn.SsnEntity;
import de.ur.agennt.entity.xgnn.XGnnEntity;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Facade of the data layer
 */
public class DataFacade {
    private static DataFacade instance;

    private ProjectManager projectManager;
    private Pfam2Go pfam2Go;
    private Taxonomy taxonomy;
    private PfamWhitelistReader pfamWhitelist;
    private Map<File, SsnEventReader> ssnEventReaderMap;
    private Map<File, SsnEventWriter> ssnEventWriterMap;
    private Map<File, ExtendedGNNWriter> extendedGNNWriterMap;
    private Map<File, ColoredSsnEventReader> coloredSsnEventReaderMap;

    private DataFacade() {
        pfam2Go = new Pfam2Go();
        taxonomy = new Taxonomy();
        projectManager = new ProjectManager();
        pfamWhitelist = new PfamWhitelistReader();
        ssnEventReaderMap = new HashMap<>();
        ssnEventWriterMap = new HashMap<>();
        extendedGNNWriterMap = new HashMap<>();
        coloredSsnEventReaderMap = new HashMap<>();
    }

    public static DataFacade getInstance() {
        if(DataFacade.instance == null) {
            DataFacade.instance = new DataFacade();
        }
        return DataFacade.instance;
    }

    public SsnEntity readSsnEvent(File inputFile) throws FileNotFoundException, XMLStreamException {
        if(!ssnEventReaderMap.containsKey(inputFile)) {
            SsnEventReader ssnEventReader = new SsnEventReader(inputFile);
            ssnEventReaderMap.put(inputFile, ssnEventReader);
        }
        SsnEventReader ssnEventReader = ssnEventReaderMap.get(inputFile);
        if(ssnEventReader.hasNext()) {
            return ssnEventReader.next();
        } else {
            ssnEventReader.close();
            ssnEventReaderMap.remove(inputFile);
            return null;
        }
    }

    public ColoredSsnEntity readColoredSsnEvent(File inputFile) throws FileNotFoundException, XMLStreamException {
        if(!coloredSsnEventReaderMap.containsKey(inputFile)) {
            ColoredSsnEventReader coloredSsnEventReader = new ColoredSsnEventReader(inputFile);
            coloredSsnEventReaderMap.put(inputFile, coloredSsnEventReader);
        }
        ColoredSsnEventReader coloredSsnEventReader = coloredSsnEventReaderMap.get(inputFile);
        if(coloredSsnEventReader.hasNext()) {
            return coloredSsnEventReader.next();
        } else {
            coloredSsnEventReader.close();
            coloredSsnEventReaderMap.remove(inputFile);
            return null;
        }
    }

    public void writeSsnEvent(File outputFile, SsnEntity ssnEntity) throws FileNotFoundException, XMLStreamException {
        if(!ssnEventWriterMap.containsKey(outputFile)) {
            SsnEventWriter ssnEventWriter = new SsnEventWriter(outputFile);
            ssnEventWriterMap.put(outputFile, ssnEventWriter);
        }
        SsnEventWriter ssnEventWriter = ssnEventWriterMap.get(outputFile);
        if(ssnEntity != null) {
            ssnEventWriter.add(ssnEntity);
        } else {
            ssnEventWriter.close();
            ssnEventWriterMap.remove(outputFile);
        }
    }

    public void writeExtendedGnnEvent(File outputFile, XGnnEntity xGnnEntity) throws FileNotFoundException, XMLStreamException {
        if(!extendedGNNWriterMap.containsKey(outputFile)) {
            ExtendedGNNWriter extendedGNNWriter = new ExtendedGNNWriter(outputFile);
            extendedGNNWriterMap.put(outputFile, extendedGNNWriter);
        }
        ExtendedGNNWriter extendedGNNWriter = extendedGNNWriterMap.get(outputFile);
        if(xGnnEntity != null) {
            extendedGNNWriter.add(xGnnEntity);
        } else {
            extendedGNNWriter.close();
            extendedGNNWriterMap.remove(outputFile);
        }
    }

    public FullGnn readFullGnn(File inputFile, Integer neighborhoodSize, Integer coocurrence) throws FileNotFoundException, XMLStreamException {
        GnnFullReader reader = new GnnFullReader(inputFile,neighborhoodSize,coocurrence);
        return reader.parse();
    }

    public void writeFullGnn(FullGnn gnn, File outputFile) throws FileNotFoundException, XMLStreamException {
        GnnFullWriter writer = new GnnFullWriter(gnn);
        writer.write(outputFile);
    }

    public void requestGnn(File ssnFile, Integer neighborhoodSize, Integer coocurrence, String email, File gnnFile, File coloredFile) throws Exception {
        List<File> result = new ArrayList<>();
        GnnRequester gnnRequester = new GnnRequester(ssnFile);
        gnnRequester.request(neighborhoodSize,coocurrence,email, gnnFile, coloredFile);
    }

    public List<Pfam2GoEntry> getGo(String pfam) {
        return pfam2Go.getGo(pfam);
    }

    public String getEMail() {
        return projectManager.getEMail();
    }

    public void setEMail(String email) {
        projectManager.setEMail(email);
    }

    public String getCytoscapeExecutable() {
        return projectManager.getCytoscapeExecutable();
    }

    public void setCytoscapeExecutable(String executable) {
        projectManager.setCytoscapeExecutable(executable);
    }

    public List<Project> listProjects() {
        return projectManager.listProjects();
    }

    public Project createProject(String name) {
        return projectManager.createProject(name);
    }

    public void deleteProject(Project project) {
        projectManager.deleteProject(project);
    }

    public Set<String> getPfamWhitelist() {
        return pfamWhitelist.getWhiteList();
    }

    public Set<String> getPfamWhitelist(File file) throws IOException {
        PfamWhitelistReader customWhitelist = new PfamWhitelistReader(file);
        return customWhitelist.getWhiteList();
    }

    public void writePfamWhitelistToFile(Set<String> whitelist, File file) throws IOException {
        PfamWhitelistWriter writer = new PfamWhitelistWriter(whitelist);
        writer.writeToFile(file);
    }

    public boolean isSpecies(String taxonomyId) {
        return taxonomy.isSpecies(taxonomyId);
    }

}
