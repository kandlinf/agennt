package de.ur.agennt.ui;

import de.ur.agennt.entity.project.Project;
import de.ur.agennt.service.ServiceFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class AgenntApplication extends Application implements ProjectViewer {
    private static Project findProjectByName(String name) {
        ServiceFacade serviceFacade = ServiceFacade.getInstance();
        for(Project project : serviceFacade.listProjects()) {
            if(project.getName().equals(name))
                return project;
        }
        return null;
    }

    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption(Option.builder().longOpt("help").desc("Show information about usage").build());
        options.addOption(Option.builder().longOpt("create").desc("Create project").build());
        options.addOption(Option.builder().longOpt("delete").desc("Delete project").build());
        options.addOption(Option.builder().longOpt("add-ssn").hasArgs().argName("FILE").desc("Add SSN file FILE").build());
        options.addOption(Option.builder().longOpt("filter-ssn").desc("Filter first SSN in project").build());
        options.addOption(Option.builder().longOpt("th").hasArgs().argName("THRESHOLD").desc("Apply specified threshold").build());
        options.addOption(Option.builder().longOpt("tax").hasArgs().argName("TAXONOMY").desc("Apply taxonomy filter (true,false)").build());
        options.addOption(Option.builder().longOpt("project").hasArgs().argName("NAME").desc("Specifies project with NAME").build());
        options.addOption(Option.builder().longOpt("add-gnn").desc("Request GNN for first filtered SSN in project").build());
        options.addOption(Option.builder().longOpt("co").hasArgs().argName("CO").desc("Co-occurrence").build());
        options.addOption(Option.builder().longOpt("nh").hasArgs().argName("NH").desc("Neighborhood Size").build());
        options.addOption(Option.builder().longOpt("email").hasArgs().argName("EMAIL").desc("EMail").build());

        //-PappArgs="['--create-project=Hans']"
        ServiceFacade serviceFacade = ServiceFacade.getInstance();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse( options, args );
            if(line.hasOption("help")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp( "agennt", options );
            } else if(line.hasOption("create")) {
                    String projectName = line.getOptionValue("project");
                    serviceFacade.createProject(projectName);
            } else if(line.hasOption("delete")) {
                String projectName = line.getOptionValue("project");
                Project project = findProjectByName(projectName);
                if(project != null) {
                    serviceFacade.deleteProject(project);
                } else {
                    System.err.println("Project not found!");
                }
            } else if(line.hasOption("add-ssn")) {
                    String projectName = line.getOptionValue("project");
                    String fileName = line.getOptionValue("add-ssn");
                    Project project = findProjectByName(projectName);
                    if (project != null) {
                        serviceFacade.addSsnToProject(project, new File(fileName));
                    } else {
                        System.err.println("Project not found!");
                    }
            } else if(line.hasOption("filter-ssn")) {
                String projectName = line.getOptionValue("project");
                String th = line.getOptionValue("th");
                String tax = line.getOptionValue("tax");
                Project project = findProjectByName(projectName);
                serviceFacade.addFilteredSsnToProject(project,project.getSsnFiles().get(0),new Integer(th), new Boolean(tax));

            } else if(line.hasOption("add-gnn")) {
                String projectName = line.getOptionValue("project");
                String co = line.getOptionValue("co");
                String nh = line.getOptionValue("nh");
                String email = line.getOptionValue("email");
                Project project = findProjectByName(projectName);
                File ssn = project.getSsnFiles().get(0);
                File filteredSSN = project.getFilteredFiles(ssn).get(0);
                serviceFacade.addGnnToProject(project,filteredSSN,new Integer(nh), new Integer(co), email);
            } else {
                boolean launched = false;
                try {
                    Object fxObject = System.getProperties().get("javafx.runtime.version");
                    if (fxObject != null) {
                        String fxString = fxObject.toString();
                        System.out.println("JavaFX: " + fxString);
                        String[] fxArray = fxString.split("\\.");
                        int major = Integer.parseInt(fxArray[0]);
                        int minor = Integer.parseInt(fxArray[1]);
                        String[] patchArray = fxArray[2].split("-");
                        int patch = Integer.parseInt(patchArray[0]);
                        if (major >= 8 && minor >= 0 && patch >= 60) {
                            launched = true;
                            launch(args);
                        }

                    }
                } catch (Exception ex) {
                    if(launched) {
                        System.err.println("An error occurred: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
                if(!launched) {
                    System.err.println("No suitable JavaFX found!");
                    System.err.println("AGeNNT needs at least Oracle Java SE Runtime Environment");
                    System.err.println("Version 8 Update 101. It can be downloaded for most operating");
                    System.err.println("systems from https://www.java.com/download/");
                }
            }

        }
        catch( Exception exp ) {
            System.out.println(exp.getMessage() );
        }
        System.exit(0);
    }

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException{
        this.primaryStage = primaryStage;

        FXMLLoader openLoader = new FXMLLoader(getClass().getResource("openScene.fxml"));
        Pane openPane = openLoader.load();
        OpenSceneController openController = openLoader.getController();
        openController.setProjectViewer(this);
        Scene openScene = new Scene(openPane);
        primaryStage.setScene(openScene);
        primaryStage.setTitle("AGeNNT v1.1.0");
        primaryStage.show();
    }

    @Override
    public void viewProject(Project project) throws IOException {
        FXMLLoader projectLoader = new FXMLLoader(getClass().getResource("projectScene.fxml"));
        SplitPane projectPane = projectLoader.load();
        ProjectSceneController projectController = projectLoader.getController();
        projectController.setProject(project);
        projectController.setStage(primaryStage);
        Scene projectScene = new Scene(projectPane);
        primaryStage.setScene(projectScene);
    }
}
