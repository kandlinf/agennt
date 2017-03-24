package de.ur.agennt.service;

import de.ur.agennt.data.DataFacade;
import de.ur.agennt.data.enzymefunction.GnnRequester;
import de.ur.agennt.data.gnn.GnnFullReader;
import de.ur.agennt.entity.gnn.FullGnn;
import de.ur.agennt.entity.gnn.GnnStatistic;
import de.ur.agennt.entity.go.Pfam2GoEntry;
import de.ur.agennt.entity.project.Project;
import de.ur.agennt.entity.colored.ColoredSsnAnalyzerResult;
import de.ur.agennt.entity.ssn.SsnAnalyzerResult;
import de.ur.agennt.service.gnn.ExtendedGnnGenerator;
import de.ur.agennt.service.gnn.NeighborhoodStatistic;
import de.ur.agennt.service.gnn.PfamFilter;
import de.ur.agennt.service.ssn.ColoredSsnAnalyzer;
import de.ur.agennt.service.ssn.SSNAnalyzer;
import de.ur.agennt.service.ssn.TaxonomyFilter;
import de.ur.agennt.service.ssn.ThresholdFilter;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class ServiceFacade {
    private static ServiceFacade instance;

    private DataFacade dataFacade;
    private GnnRequester gnnRequester;

    private ServiceFacade() {
        dataFacade = DataFacade.getInstance();
    }

    public static ServiceFacade getInstance() {
        if(ServiceFacade.instance == null) {
            ServiceFacade.instance = new ServiceFacade();
        }
        return ServiceFacade.instance;
    }

    private File getTempFilename() {
        return new File(UUID.randomUUID().toString().replace("-","") + ".tmp");
    }

    public void addSsnToProject(Project project, File inputFile) throws FileNotFoundException, XMLStreamException {
        SSNAnalyzer ssnAnalyzer = new SSNAnalyzer(inputFile);
        SsnAnalyzerResult result = ssnAnalyzer.analyze(false);
        if(!Thread.currentThread().isInterrupted()) {
            project.addSSN(inputFile, result);
        }
    }

    public void addFilteredSsnToProject(Project project, File inputFile, Integer threshold, Boolean filterTaxonomy) throws FileNotFoundException, XMLStreamException {
        File TEMP_FILTERED_FILE = getTempFilename();
        File TEMP_WORKING_FILE = getTempFilename();
        File TEMP_TAXONOMY_FILE = getTempFilename();
        ThresholdFilter thresholdFilter = new ThresholdFilter(inputFile, TEMP_FILTERED_FILE);
        thresholdFilter.filter(threshold);
        if(filterTaxonomy) {
            TaxonomyFilter taxonomyFilter = new TaxonomyFilter(TEMP_FILTERED_FILE, TEMP_WORKING_FILE, TEMP_TAXONOMY_FILE);
            taxonomyFilter.filter();
            SSNAnalyzer ssnAnalyzer = new SSNAnalyzer(TEMP_TAXONOMY_FILE);
            SsnAnalyzerResult result = ssnAnalyzer.analyze(filterTaxonomy);
            if(!Thread.currentThread().isInterrupted()) {
                project.addFilteredSSN(TEMP_TAXONOMY_FILE, inputFile, result);
            }
        } else {
            SSNAnalyzer ssnAnalyzer = new SSNAnalyzer(TEMP_FILTERED_FILE);
            SsnAnalyzerResult result = ssnAnalyzer.analyze(filterTaxonomy);
            if(!Thread.currentThread().isInterrupted()) {
                project.addFilteredSSN(TEMP_FILTERED_FILE, inputFile, result);
            }
        }
        try {
            if(TEMP_FILTERED_FILE.exists()) Files.delete(TEMP_FILTERED_FILE.toPath());
            if(TEMP_WORKING_FILE.exists()) Files.delete(TEMP_WORKING_FILE.toPath());
            if(TEMP_TAXONOMY_FILE.exists()) Files.delete(TEMP_TAXONOMY_FILE.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addGnnToProject(Project project, File ssnFile, String url) throws Exception{
        File TEMP_GNN_FILE = getTempFilename();
        File TEMP_COLORED_FILE = getTempFilename();
        File TEMP_XGNN_FILE = getTempFilename();

        gnnRequester = new GnnRequester(url);
        gnnRequester.request(TEMP_GNN_FILE, TEMP_COLORED_FILE);
        if(!Thread.currentThread().isInterrupted()) {
            ColoredSsnAnalyzer coloredSsnAnalyzer = new ColoredSsnAnalyzer(TEMP_COLORED_FILE);
            ColoredSsnAnalyzerResult coloredSsnAnalyzerResult = coloredSsnAnalyzer.analyze();
            GnnFullReader gnnFullReader = new GnnFullReader(TEMP_GNN_FILE, gnnRequester.getNeighborhoodSize(), gnnRequester.getCoocurrence());
            FullGnn fullGnn = gnnFullReader.parse();
            NeighborhoodStatistic neighborhoodStatistic = new NeighborhoodStatistic(fullGnn);
            GnnStatistic gnnStatistic = neighborhoodStatistic.calculate();
            ExtendedGnnGenerator extendedGnnGenerator = new ExtendedGnnGenerator(fullGnn, gnnStatistic, coloredSsnAnalyzerResult);
            extendedGnnGenerator.writeToFile(TEMP_XGNN_FILE);
            if (!Thread.currentThread().isInterrupted()) {
                project.addGNN(TEMP_GNN_FILE, TEMP_COLORED_FILE, fullGnn, gnnStatistic, coloredSsnAnalyzerResult, TEMP_XGNN_FILE, ssnFile);
            }
        }
        try {
            if(TEMP_GNN_FILE.exists()) Files.delete(TEMP_GNN_FILE.toPath());
            if(TEMP_COLORED_FILE.exists()) Files.delete(TEMP_COLORED_FILE.toPath());
            if(TEMP_XGNN_FILE.exists()) Files.delete(TEMP_XGNN_FILE.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addGnnToProject(Project project, String url) throws Exception{
        File TEMP_GNN_FILE = getTempFilename();
        File TEMP_COLORED_FILE = getTempFilename();
        File TEMP_XGNN_FILE = getTempFilename();

        gnnRequester = new GnnRequester(url);
        gnnRequester.request(TEMP_GNN_FILE, TEMP_COLORED_FILE);
        if(!Thread.currentThread().isInterrupted()) {
            ColoredSsnAnalyzer coloredSsnAnalyzer = new ColoredSsnAnalyzer(TEMP_COLORED_FILE);
            ColoredSsnAnalyzerResult coloredSsnAnalyzerResult = coloredSsnAnalyzer.analyze();
            GnnFullReader gnnFullReader = new GnnFullReader(TEMP_GNN_FILE, gnnRequester.getNeighborhoodSize(), gnnRequester.getCoocurrence());
            FullGnn fullGnn = gnnFullReader.parse();
            NeighborhoodStatistic neighborhoodStatistic = new NeighborhoodStatistic(fullGnn);
            GnnStatistic gnnStatistic = neighborhoodStatistic.calculate();
            ExtendedGnnGenerator extendedGnnGenerator = new ExtendedGnnGenerator(fullGnn, gnnStatistic, coloredSsnAnalyzerResult);
            extendedGnnGenerator.writeToFile(TEMP_XGNN_FILE);
            if (!Thread.currentThread().isInterrupted()) {
                project.addGNN(TEMP_GNN_FILE, TEMP_COLORED_FILE, fullGnn, gnnStatistic, coloredSsnAnalyzerResult, TEMP_XGNN_FILE);
            }
        }
        try {
            if(TEMP_GNN_FILE.exists()) Files.delete(TEMP_GNN_FILE.toPath());
            if(TEMP_COLORED_FILE.exists()) Files.delete(TEMP_COLORED_FILE.toPath());
            if(TEMP_XGNN_FILE.exists()) Files.delete(TEMP_XGNN_FILE.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addGnnToProject(Project project, File inputFile, Integer neighborhoodSize, Integer coocurrence, String email) throws Exception {
        File TEMP_GNN_FILE = getTempFilename();
        File TEMP_COLORED_FILE = getTempFilename();
        File TEMP_XGNN_FILE = getTempFilename();
        File TEMP_FILTERED_FILE = getTempFilename();
        File TEMP_WORKING_FILE = getTempFilename();
        File TEMP_TAXONOMY_FILE = getTempFilename();

        gnnRequester = new GnnRequester(inputFile);
        gnnRequester.request(neighborhoodSize, coocurrence, email, TEMP_GNN_FILE, TEMP_COLORED_FILE);
        if(!Thread.currentThread().isInterrupted()) {
            ColoredSsnAnalyzer coloredSsnAnalyzer = new ColoredSsnAnalyzer(TEMP_COLORED_FILE);
            ColoredSsnAnalyzerResult coloredSsnAnalyzerResult = coloredSsnAnalyzer.analyze();
            GnnFullReader gnnFullReader = new GnnFullReader(TEMP_GNN_FILE, neighborhoodSize, coocurrence);
            FullGnn fullGnn = gnnFullReader.parse();
            NeighborhoodStatistic neighborhoodStatistic = new NeighborhoodStatistic(fullGnn);
            GnnStatistic gnnStatistic = neighborhoodStatistic.calculate();
            ExtendedGnnGenerator extendedGnnGenerator = new ExtendedGnnGenerator(fullGnn, gnnStatistic, coloredSsnAnalyzerResult);
            extendedGnnGenerator.writeToFile(TEMP_XGNN_FILE);
            if (!Thread.currentThread().isInterrupted()) {
                project.addGNN(TEMP_GNN_FILE, TEMP_COLORED_FILE, fullGnn, gnnStatistic, coloredSsnAnalyzerResult, TEMP_XGNN_FILE, inputFile);
            }
        }
        try {
            if(TEMP_GNN_FILE.exists()) Files.delete(TEMP_GNN_FILE.toPath());
            if(TEMP_COLORED_FILE.exists()) Files.delete(TEMP_COLORED_FILE.toPath());
            if(TEMP_XGNN_FILE.exists()) Files.delete(TEMP_XGNN_FILE.toPath());
            if(TEMP_FILTERED_FILE.exists()) Files.delete(TEMP_FILTERED_FILE.toPath());
            if(TEMP_WORKING_FILE.exists()) Files.delete(TEMP_WORKING_FILE.toPath());
            if(TEMP_TAXONOMY_FILE.exists()) Files.delete(TEMP_TAXONOMY_FILE.toPath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFilteredGnnToProject(Project project, File gnnFile, File filterFile) throws IOException, XMLStreamException {
        File TEMP_FILTER_FILE = getTempFilename();
        File TEMP_XGNN_FILE = getTempFilename();
        File TEMP_GNN_FILE = getTempFilename();
        File TEMP_COLORED_FILE = getTempFilename();
        File TEMP_FILTERED_FILE = getTempFilename();
        File TEMP_WORKING_FILE = getTempFilename();
        File TEMP_TAXONOMY_FILE = getTempFilename();

        PfamFilter pfamFilter;
        if(filterFile != null) {
            pfamFilter = new PfamFilter(filterFile);
        } else {
            pfamFilter = new PfamFilter();
        }
        ColoredSsnAnalyzer coloredSsnAnalyzer = new ColoredSsnAnalyzer(project.getColoredSsn(gnnFile));
        ColoredSsnAnalyzerResult coloredSsnAnalyzerResult = coloredSsnAnalyzer.analyze();
        FullGnn fullGnn = project.getFullGnn(gnnFile);
        FullGnn filteredGnn = pfamFilter.filter(fullGnn);
        NeighborhoodStatistic neighborhoodStatistic = new NeighborhoodStatistic(filteredGnn);
        GnnStatistic gnnStatistic = neighborhoodStatistic.calculate();
        dataFacade.writeFullGnn(filteredGnn,TEMP_GNN_FILE);
        pfamFilter.writeFilter(TEMP_FILTER_FILE);
        ExtendedGnnGenerator extendedGnnGenerator = new ExtendedGnnGenerator(filteredGnn, gnnStatistic, coloredSsnAnalyzerResult);
        extendedGnnGenerator.writeToFile(TEMP_XGNN_FILE);
        if(!Thread.currentThread().isInterrupted()) {
            project.addFilteredGNN(TEMP_GNN_FILE, project.getColoredSsn(gnnFile), filteredGnn, gnnStatistic,
                    coloredSsnAnalyzerResult, TEMP_FILTER_FILE, TEMP_XGNN_FILE, gnnFile);
        }
        try {
            if(TEMP_FILTER_FILE.exists()) Files.delete(TEMP_FILTER_FILE.toPath());
            if(TEMP_XGNN_FILE.exists()) Files.delete(TEMP_XGNN_FILE.toPath());
            if(TEMP_GNN_FILE.exists()) Files.delete(TEMP_GNN_FILE.toPath());
            if(TEMP_COLORED_FILE.exists()) Files.delete(TEMP_COLORED_FILE.toPath());
            if(TEMP_FILTERED_FILE.exists()) Files.delete(TEMP_FILTERED_FILE.toPath());
            if(TEMP_WORKING_FILE.exists()) Files.delete(TEMP_WORKING_FILE.toPath());
            if(TEMP_TAXONOMY_FILE.exists()) Files.delete(TEMP_TAXONOMY_FILE.toPath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Project> listProjects() {
        return dataFacade.listProjects();
    }

    public Project createProject(String name) {
        return dataFacade.createProject(name);
    }

    public void deleteProject(Project project) {
        dataFacade.deleteProject(project);
    }

    public List<Pfam2GoEntry> getGo(String pfam) {
        return dataFacade.getGo(pfam);
    }

    public String getEMail() {
        return dataFacade.getEMail();
    }

    public void setEMail(String email) {
        dataFacade.setEMail(email);
    }

    public String getCytoscapeExecutable() {
        return dataFacade.getCytoscapeExecutable();
    }

    public void setCytoscapeExecutable(String executable) {
        dataFacade.setCytoscapeExecutable(executable);
    }

    public static  <K, V extends Comparable<? super V>> Map<K, V>
    sortByValue( Map<K, V> map )
    {
        List<Map.Entry<K, V>> list =
                new LinkedList<>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return ( o2.getValue() ).compareTo( o1.getValue() );
            }
        } );

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }

}
