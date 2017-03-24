package de.ur.agennt.data.project;

import de.ur.agennt.entity.project.Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProjectManager {
    File projectRootFile;
    File propertiesFile;
    Properties properties;

    public ProjectManager() {
        this.projectRootFile = new File(System.getProperty("user.home") + "/agennt");
        if(!projectRootFile.exists()) {
            projectRootFile.mkdir();
        }
        try {
            this.properties = new Properties();
            this.propertiesFile = new File(projectRootFile, "settings.ini");
            if (propertiesFile.exists()) {
                this.properties.load(new FileInputStream(propertiesFile));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getEMail() {
        return properties.getProperty("email");
    }

    public void setEMail(String value) {
        try {
            this.properties.setProperty("email", value);
            properties.store(new FileOutputStream(propertiesFile), "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getCytoscapeExecutable() {
        return properties.getProperty("cytoscape");
    }

    public void setCytoscapeExecutable(String value) {
        try {
            this.properties.setProperty("cytoscape", value);
            properties.store(new FileOutputStream(propertiesFile), "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Project> listProjects() {
        File[] projectFiles = projectRootFile.listFiles();
        List<Project> projectList = new ArrayList<>();

        for(File projectFile : projectFiles) {
            if(projectFile.isDirectory()) {
                projectList.add(new Project(projectFile.getName(), projectFile));
            }
        }
        return projectList;
    }

    public Project createProject(String name) {
        File projectFile = new File(projectRootFile, name);
        if(!projectFile.exists()) {
            projectFile.mkdir();
            return new Project(name, projectFile);
        } else {
            return null;
        }
    }

    public void deleteProject(Project project) {
        File projectFolder = new File(projectRootFile, project.getName());
        if(projectFolder.exists() && projectFolder.isDirectory()) {
            deleteDir(projectFolder);
        }
    }

    private void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();
    }

}
