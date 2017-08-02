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

import java.io.IOException;
import java.lang.management.ManagementFactory;

public class AgenntApplication extends Application implements ProjectViewer {

    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption(Option.builder().longOpt("help").desc("Show information about usage").build());
        options.addOption(Option.builder().longOpt("create-project").hasArg().argName("NAME").desc("Create project with NAME").build());
        options.addOption(Option.builder().longOpt("delete-project").hasArg().argName("NAME").desc("Create project with NAME").build());
        //-PappArgs="['--create-project=Hans']"
        ServiceFacade serviceFacade = ServiceFacade.getInstance();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse( options, args );
            if(line.hasOption("help")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp( "agennt", options );
            } else if(line.hasOption("create-project")) {
                    String projectName = line.getOptionValue("create-project");
                    serviceFacade.createProject(projectName);
            } else if(line.hasOption("delete-project")) {
                //String projectName = line.getOptionValue("delete-project");
                //serviceFacade.deleteProject(serviceFacade.listProje);
            }  else {
                boolean launched = false;
                try {
                    Object fxObject = System.getProperties().get("javafx.runtime.version");
                    if (fxObject != null) {
                        String fxString = fxObject.toString();
                        System.out.println("JavaFX: " + fxString);
                        String[] fxArray = fxString.split("\\."); //d
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
        catch( ParseException exp ) {
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
