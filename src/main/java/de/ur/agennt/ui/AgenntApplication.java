package de.ur.agennt.ui;

import de.ur.agennt.entity.project.Project;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.management.ManagementFactory;

public class AgenntApplication extends Application implements ProjectViewer{

    public static void main(String[] args) {
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
