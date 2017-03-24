package de.ur.agennt.entity.project;

import de.ur.agennt.entity.gnn.FullGnn;
import de.ur.agennt.entity.gnn.GnnStatistic;
import de.ur.agennt.entity.colored.ColoredSsnAnalyzerResult;
import de.ur.agennt.entity.ssn.SsnAnalyzerResult;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private String name;
    private File projectRootFile;

    public Project(String name, File projectRootFile) {
        this.name = name;
        this.projectRootFile =  projectRootFile;
    }

    public String getName() {
        return name;
    }
//TE
    public List<File> getSsnFiles() {
        List<File> ssnFiles = new ArrayList<>();;
        for(File file : projectRootFile.listFiles()) {
            if(file.getName().endsWith(".xgmml") && !file.getName().endsWith(".gnn.xgmml")
                    && !file.getName().endsWith(".x.xgmml") && !file.getName().endsWith(".color.xgmml")) {
                ssnFiles.add(file);
            }
        }
        return ssnFiles;
    }

    public List<File> getFilteredFiles(File ssnFile) {
        List<File> ssnFiles = new ArrayList<>();
        File ssnFolder = new File(ssnFile.getAbsolutePath().replace(".xgmml", "") + "/");
        for(File file : ssnFolder.listFiles()) {
            if(file.getName().endsWith(".xgmml")) {
                ssnFiles.add(file);
            }
        }
        return ssnFiles;
    }

    public SsnAnalyzerResult getAnalyzerResult(File ssnFile)  {
        try {
            String fileName = ssnFile.getAbsolutePath().replace(".xgmml", ".res");
            File resFile = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(resFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            SsnAnalyzerResult ssnAnalyzerResult = (SsnAnalyzerResult)objectInputStream.readObject();
            return ssnAnalyzerResult;
        } catch (Exception ex) {

        }
        return null;
    }

    public File getColoredSsn(File gnnFile)  {
        try {
            String fileName = gnnFile.getAbsolutePath().replace(".gnn.xgmml", ".color.xgmml");
            File coloredFile = new File(fileName);
            return coloredFile;
        } catch (Exception ex) {

        }
        return null;
    }

    public ColoredSsnAnalyzerResult getColoredSsnAnalyzerResult(File gnnFile)  {
        try {
            String fileName = gnnFile.getAbsolutePath().replace(".gnn.xgmml", ".color.stat");
            File coloredFile = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(coloredFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ColoredSsnAnalyzerResult coloredSsnAnalyzerResult = (ColoredSsnAnalyzerResult)objectInputStream.readObject();
            return coloredSsnAnalyzerResult;
        } catch (Exception ex) {

        }
        return null;
    }

    public GnnStatistic getStatGnn(File gnnFile)  {
        try {
            String fileName = gnnFile.getAbsolutePath().replace(".gnn.xgmml", ".stat.res");
            File resFile = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(resFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            GnnStatistic gnnStatistic = (GnnStatistic) objectInputStream.readObject();
            return gnnStatistic;
        } catch (Exception ex) {

        }
        return null;
    }

    public FullGnn getFullGnn(File gnnFile)  {
        try {
            String fileName = gnnFile.getAbsolutePath().replace(".gnn.xgmml", ".fullgnn.res");
            File resFile = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(resFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            FullGnn fullGnn = (FullGnn) objectInputStream.readObject();
            return fullGnn;
        } catch (Exception ex) {

        }
        return null;
    }

    public List<File> getGNNFiles() {
        List<File> gnnFiles = new ArrayList<>();;
        for(File file : projectRootFile.listFiles()) {
            if(file.getName().endsWith(".gnn.xgmml")) {
                gnnFiles.add(file);
            }
        }
        return gnnFiles;
    }

    public List<File> getGNNFiles(File ssnFile) {
        List<File> ssnFiles = new ArrayList<>();
        File ssnFolder = new File(ssnFile.getAbsolutePath().replace(".xgmml", "") + "/");
        for(File file : ssnFolder.listFiles()) {
            if(file.getName().endsWith(".gnn.xgmml")) {
                ssnFiles.add(file);
            }
        }
        return ssnFiles;
    }

    public File getFilterFile(File filteredGnnFile) {
        try {
            String fileName = filteredGnnFile.getAbsolutePath().replace(".gnn.xgmml", ".txt");
            File filterFile = new File(fileName);
            return filterFile;
        } catch (Exception ex) {

        }
        return null;
    }

    public File getExtendedGnnFile(File gnnFile) {
        try {
            String fileName = gnnFile.getAbsolutePath().replace(".gnn.xgmml", ".x.xgmml");
            File filterFile = new File(fileName);
            return filterFile;
        } catch (Exception ex) {

        }
        return null;
    }

    public List<File> getFilteredGNNFiles(File gnnFile) {
        List<File> gnnFiles = new ArrayList<>();
        File gnnFolder = new File(gnnFile.getAbsolutePath().replace(".gnn.xgmml", "") + "/");
        for(File file : gnnFolder.listFiles()) {
            if(file.getName().endsWith(".gnn.xgmml")) {
                gnnFiles.add(file);
            }
        }

        return gnnFiles;
    }

    public void addSSN(File ssnFile, SsnAnalyzerResult ssnAnalyzerResult) {
        try {
            LocalDateTime date = LocalDateTime.now();
            String baseFileName = date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace(":", "-");
            File localSsnFile = new File(projectRootFile, baseFileName + ".xgmml");
            File localResultFile = new File(projectRootFile, baseFileName + ".res");
            File localFolder = new File(projectRootFile, baseFileName + "/");
            localFolder.mkdir();
            Files.copy(ssnFile.toPath(), localSsnFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            FileOutputStream fileOutputStream = new FileOutputStream(localResultFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(ssnAnalyzerResult);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addFilteredGNN(File filteredGnnFile, File coloredFile, FullGnn fullGnn, GnnStatistic gnnStatistic, ColoredSsnAnalyzerResult coloredSsnAnalyzerResult, File filterFile, File extendedGnn, File gnnFile) {
        try {
            String folder = gnnFile.getAbsolutePath().replace(".gnn.xgmml", "") + "/";
            LocalDateTime date = LocalDateTime.now();
            String baseFileName = date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace(":", "-");

            File localGnnFile = new File(folder + baseFileName + ".gnn.xgmml");
            File localGnnStatisticFile = new File(folder + baseFileName + ".stat.res");
            File localColoredFile = new File(folder + baseFileName + ".color.xgmml");
            File localResultFile = new File(folder + baseFileName + ".fullgnn.res");
            File localFilterFile = new File(folder + baseFileName + ".txt");
            File localColoredSsnAnalyzerResultFile = new File(folder + baseFileName + ".color.stat");
            File localExtendedFile = new File(folder + baseFileName + ".x.xgmml");
            Files.copy(filteredGnnFile.toPath(), localGnnFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(coloredFile.toPath(), localColoredFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(filterFile.toPath(), localFilterFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(extendedGnn.toPath(), localExtendedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            FileOutputStream fullOutputStream = new FileOutputStream(localResultFile);
            ObjectOutputStream fullObjectStream = new ObjectOutputStream(fullOutputStream);
            fullObjectStream.writeObject(fullGnn);

            FileOutputStream coloredStatOutputStream = new FileOutputStream(localColoredSsnAnalyzerResultFile);
            ObjectOutputStream coloredStatObjectStream = new ObjectOutputStream(coloredStatOutputStream);
            coloredStatObjectStream.writeObject(coloredSsnAnalyzerResult);

            FileOutputStream statOutputStream = new FileOutputStream(localGnnStatisticFile);
            ObjectOutputStream statObjectStream = new ObjectOutputStream(statOutputStream);
            statObjectStream.writeObject(gnnStatistic);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addGNN(File gnnFile, File coloredFile, FullGnn fullGnn, GnnStatistic gnnStatistic, ColoredSsnAnalyzerResult coloredSsnAnalyzerResult, File extendedGnn) {
        try {
            LocalDateTime date = LocalDateTime.now();
            String baseFileName = date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace(":", "-");

            File localFolder = new File(projectRootFile, baseFileName + "/");
            localFolder.mkdir();

            File localGnnFile = new File(projectRootFile, baseFileName + ".gnn.xgmml");
            File localGnnStatisticFile = new File(projectRootFile,  baseFileName + ".stat.res");
            File localColoredFile = new File(projectRootFile, baseFileName + ".color.xgmml");
            File localColoredSsnAnalyzerResultFile = new File(projectRootFile, baseFileName + ".color.stat");
            File localResultFile = new File(projectRootFile, baseFileName + ".fullgnn.res");
            File localExtendedFile = new File(projectRootFile, baseFileName + ".x.xgmml");

            Files.copy(gnnFile.toPath(), localGnnFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(coloredFile.toPath(), localColoredFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(extendedGnn.toPath(), localExtendedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            FileOutputStream fullOutputStream = new FileOutputStream(localResultFile);
            ObjectOutputStream fullObjectStream = new ObjectOutputStream(fullOutputStream);
            fullObjectStream.writeObject(fullGnn);

            FileOutputStream coloredStatOutputStream = new FileOutputStream(localColoredSsnAnalyzerResultFile);
            ObjectOutputStream coloredStatObjectStream = new ObjectOutputStream(coloredStatOutputStream);
            coloredStatObjectStream.writeObject(coloredSsnAnalyzerResult);

            FileOutputStream statOutputStream = new FileOutputStream(localGnnStatisticFile);
            ObjectOutputStream statObjectStream = new ObjectOutputStream(statOutputStream);
            statObjectStream.writeObject(gnnStatistic);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public void addGNN(File gnnFile, File coloredFile, FullGnn fullGnn, GnnStatistic gnnStatistic, ColoredSsnAnalyzerResult coloredSsnAnalyzerResult, File extendedGnn, File ssnFile) {
        try {
            String folder = ssnFile.getAbsolutePath().replace(".xgmml", "") + "/";
            LocalDateTime date = LocalDateTime.now();
            String baseFileName = date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace(":", "-");

            File localFolder = new File(folder, baseFileName + "/");
            localFolder.mkdir();

            File localGnnFile = new File(folder + baseFileName + ".gnn.xgmml");
            File localGnnStatisticFile = new File(folder + baseFileName + ".stat.res");
            File localColoredFile = new File(folder + baseFileName + ".color.xgmml");
            File localColoredSsnAnalyzerResultFile = new File(folder + baseFileName + ".color.stat");
            File localResultFile = new File(folder + baseFileName + ".fullgnn.res");
            File localExtendedFile = new File(folder + baseFileName + ".x.xgmml");

            Files.copy(gnnFile.toPath(), localGnnFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(coloredFile.toPath(), localColoredFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(extendedGnn.toPath(), localExtendedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            FileOutputStream fullOutputStream = new FileOutputStream(localResultFile);
            ObjectOutputStream fullObjectStream = new ObjectOutputStream(fullOutputStream);
            fullObjectStream.writeObject(fullGnn);

            FileOutputStream coloredStatOutputStream = new FileOutputStream(localColoredSsnAnalyzerResultFile);
            ObjectOutputStream coloredStatObjectStream = new ObjectOutputStream(coloredStatOutputStream);
            coloredStatObjectStream.writeObject(coloredSsnAnalyzerResult);

            FileOutputStream statOutputStream = new FileOutputStream(localGnnStatisticFile);
            ObjectOutputStream statObjectStream = new ObjectOutputStream(statOutputStream);
            statObjectStream.writeObject(gnnStatistic);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addFilteredSSN(File filteredSsnFile, File ssnFile, SsnAnalyzerResult ssnAnalyzerResult) {
        try {
            LocalDateTime date = LocalDateTime.now();
            String baseFileName = date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace(":", "-");
            String ssnFolder = ssnFile.getAbsolutePath().replace(".xgmml", "") + "/";
            File localSsnFile = new File(ssnFolder + baseFileName + ".xgmml");
            File localResultFile = new File(ssnFolder + baseFileName + ".res");
            File localFolder = new File(ssnFolder + baseFileName + "/");
            localFolder.mkdir();
            Files.copy(filteredSsnFile.toPath(), localSsnFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            FileOutputStream fileOutputStream = new FileOutputStream(localResultFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(ssnAnalyzerResult);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return name;
    }

}
