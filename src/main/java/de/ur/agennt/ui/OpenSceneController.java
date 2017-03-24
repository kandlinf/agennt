package de.ur.agennt.ui;

import de.ur.agennt.entity.project.Project;
import de.ur.agennt.data.project.ProjectManager;
import de.ur.agennt.service.ServiceFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OpenSceneController implements Initializable {
    @FXML
    private Button newButton;
    @FXML
    private Button loadButton;
    @FXML
    private Button deleteButton;
    @FXML
    private ListView<Project> projectListView;

    private ServiceFacade serviceFacade;
    private ProjectViewer projectViewer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serviceFacade = ServiceFacade.getInstance();
        updateProjectList();
    }

    @FXML
    private void newAction() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Create new project");
            dialog.setHeaderText("Please enter a name for your new project");
            dialog.setContentText("New project name:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                Project project = serviceFacade.createProject(result.get());
                if (project != null) {
                    projectViewer.viewProject(project);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Create new project");
                    alert.setHeaderText("Project can not be created");
                    alert.setContentText("A project with this name already exists!");
                    alert.showAndWait();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void loadAction() {
        try {
            Project project = projectListView.getSelectionModel().getSelectedItem();
            if (project != null) {
                projectViewer.viewProject(project);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void deleteAction() {
        Project selectedProject = projectListView.getSelectionModel().getSelectedItem();
        if(selectedProject != null) {
            serviceFacade.deleteProject(selectedProject);
            updateProjectList();
        }
    }

    private void updateProjectList() {
        List<Project> projectList = serviceFacade.listProjects();
        ObservableList<Project> projectObservable = FXCollections.observableArrayList(projectList);
        projectListView.setItems(projectObservable);
    }

    public void setProjectViewer(ProjectViewer projectViewer) {
        this.projectViewer = projectViewer;
    }
}
