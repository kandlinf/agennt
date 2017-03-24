package de.ur.agennt.ui;

import de.ur.agennt.data.gnn.GnnFullWriter;
import de.ur.agennt.data.project.ProjectManager;
import de.ur.agennt.entity.colored.ColoredSsnAnalyzerResult;
import de.ur.agennt.entity.gnn.*;
import de.ur.agennt.entity.go.Pfam2GoEntry;
import de.ur.agennt.entity.ssn.SsnAnalyzerResult;
import de.ur.agennt.data.pfam.Pfam2Go;
import de.ur.agennt.service.ServiceFacade;
import de.ur.agennt.service.gnn.PfamFilter;
import de.ur.agennt.service.ssn.SSNAnalyzer;
import de.ur.agennt.data.enzymefunction.GnnRequester;
import de.ur.agennt.service.ssn.ThresholdFilter;
import de.ur.agennt.data.gnn.GnnFullReader;
import de.ur.agennt.entity.project.Project;
import de.ur.agennt.service.gnn.NeighborhoodStatistic;
import de.ur.agennt.service.ssn.ApeltsinThreshold;
import de.ur.agennt.service.ssn.SmoothThreshold;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

import java.awt.geom.Arc2D;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

public class ProjectSceneController implements Initializable, ChangeListener<TreeItem<ProjectTreeItem>> {

    @FXML
    private TreeView<ProjectTreeItem> projectTreeView;
    @FXML
    private Pane contentPane;
    @FXML
    private TitledPane projectTitledPane;
    @FXML
    private TitledPane ssnTitledPane;
    @FXML
    private TitledPane gnnTitledPane;
    @FXML
    private Label sequenceCountLabel;
    @FXML
    private Label nodeCountLabel;
    @FXML
    private Label edgeCountLabel;
    @FXML
    private Label minThLabel;
    @FXML
    private Label maxThLabel;
    @FXML
    private Label apeltsinThLabel;
    @FXML
    private Label smoothThLabel;
    @FXML
    private BarChart<String, Number> histogramBarChart;
    @FXML
    private LineChart<String, Number> nsvLineChart;
    @FXML
    private Button createFilteredButton;
    @FXML
    private Button requestGNNButton;
    @FXML
    private Button addGNNButton;
    @FXML
    private Button filterGNNButton;
    @FXML
    private Label coocurrenceLabel;
    @FXML
    private Label neighborhoodLabel;
    @FXML
    private Label clusterCountLabel;
    @FXML
    private Label neighborhoodCountLabel;
    @FXML
    private Label gnnSequenceCountLabel;
    @FXML
    private TableView<NeighborhoodEntry> hyperNeighborhoodTable;
    @FXML
    private TableView<ClusterEntry> clusterTable;
    @FXML
    private TableView<PfamEntry> pfamTable;
    @FXML
    private ImageView logoImageView;


    private ServiceFacade serviceFacade;
    private Project project;
    private Stage stage;
    private DecimalFormat uniquenessDF = new DecimalFormat("#.00");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.serviceFacade = ServiceFacade.getInstance();
        this.logoImageView.setImage(new Image(getClass().getResourceAsStream("dna.png")));
        projectTreeView.getSelectionModel().selectedItemProperty().addListener(this);
        for(TableColumn tableColumn : hyperNeighborhoodTable.getColumns()) {
            switch (tableColumn.getText()) {
                case "Cluster":
                    tableColumn.setCellValueFactory(new PropertyValueFactory<NeighborhoodEntry,Integer>("cluster"));
                    break;
                case "Pfam":
                    tableColumn.setCellValueFactory(new PropertyValueFactory<NeighborhoodEntry, String>("pfam"));
                    break;
                case "Coverage":
                    tableColumn.setCellValueFactory(new PropertyValueFactory<NeighborhoodEntry, Double>("coverage"));
                    tableColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
                        @Override
                        public TableCell call(TableColumn param) {
                            return new TableCell<NeighborhoodEntry, Double>() {
                                @Override
                                protected void updateItem(Double item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (item == null || empty) {
                                        setText(null);
                                    } else {
                                        DecimalFormat uniquenessDF = new DecimalFormat("0.00");
                                        setText(uniquenessDF.format(item));
                                    }
                                }
                            };                        }
                    });
                    break;
                case "Uniqueness":
                    tableColumn.setCellValueFactory(new PropertyValueFactory<NeighborhoodEntry, Double>("uniqueness"));
                    tableColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
                        @Override
                        public TableCell call(TableColumn param) {
                            return new TableCell<NeighborhoodEntry, Double>() {
                                @Override
                                protected void updateItem(Double item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (item == null || empty) {
                                        setText(null);
                                    } else {
                                        DecimalFormat uniquenessDF = new DecimalFormat("0.00");
                                        setText(uniquenessDF.format(item));
                                    }
                                }
                            };                        }
                    });
                    break;
                case "#Cluster":
                    tableColumn.setCellValueFactory(new PropertyValueFactory<NeighborhoodEntry, Integer>("clusterSequences"));
                    break;
                case "#Pfam":
                    tableColumn.setCellValueFactory(new PropertyValueFactory<NeighborhoodEntry, Integer>("pfamSequences"));
                    break;
                case "#Neighborhood":
                    tableColumn.setCellValueFactory(new PropertyValueFactory<NeighborhoodEntry, Integer>("neighborhoodSequences"));
                    break;
                case "GO":
                    tableColumn.setCellValueFactory(new PropertyValueFactory<NeighborhoodEntry, String>("pfamGo"));
                    break;
                case "Description":
                    tableColumn.setCellValueFactory(new PropertyValueFactory<NeighborhoodEntry, String>("pfamDescription"));
                    break;
            }
        }

        for(TableColumn tableColumn : clusterTable.getColumns()) {
            switch (tableColumn.getText()) {
                case "Cluster":
                    tableColumn.setCellValueFactory(new PropertyValueFactory<NeighborhoodEntry,Integer>("cluster"));
                    break;
                case "Uniqueness":
                    tableColumn.setCellValueFactory(new PropertyValueFactory<NeighborhoodEntry, Double>("uniqueness"));
                    tableColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
                        @Override
                        public TableCell call(TableColumn param) {
                            return new TableCell<NeighborhoodEntry, Double>() {
                                @Override
                                protected void updateItem(Double item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (item == null || empty) {
                                        setText(null);
                                    } else {
                                        DecimalFormat uniquenessDF = new DecimalFormat("0.00");
                                        setText(uniquenessDF.format(item));
                                    }
                                }
                            };                        }
                    });
                    break;
                case "Size":
                    tableColumn.setCellValueFactory(new PropertyValueFactory<NeighborhoodEntry, Integer>("clusterSequences"));
                    break;
                case "Nodes":
                    tableColumn.setCellValueFactory(new PropertyValueFactory<NeighborhoodEntry, Integer>("clusterNodes"));
                    break;
                case "Phylum":
                    tableColumn.setCellValueFactory(new PropertyValueFactory<NeighborhoodEntry, Integer>("phylumStat"));
                    break;
            }
        }

        for(TableColumn tableColumn : pfamTable.getColumns()) {
            switch (tableColumn.getText()) {
                case "Pfam":
                    tableColumn.setCellValueFactory(new PropertyValueFactory<NeighborhoodEntry,String>("pfam"));
                    break;
                case "Uniqueness":
                    tableColumn.setCellValueFactory(new PropertyValueFactory<NeighborhoodEntry, Double>("uniqueness"));
                    tableColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
                        @Override
                        public TableCell call(TableColumn param) {
                            return new TableCell<NeighborhoodEntry, Double>() {
                                @Override
                                protected void updateItem(Double item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (item == null || empty) {
                                        setText(null);
                                    } else {
                                        DecimalFormat uniquenessDF = new DecimalFormat("0.00");
                                        setText(uniquenessDF.format(item));
                                    }
                                }
                            };                        }
                    });
                    break;
                case "Size":
                    tableColumn.setCellValueFactory(new PropertyValueFactory<NeighborhoodEntry, Integer>("pfamSequences"));
                    break;
                case "GO":
                    tableColumn.setCellValueFactory(new PropertyValueFactory<NeighborhoodEntry, String>("pfamGo"));
                    break;
                case "Description":
                    tableColumn.setCellValueFactory(new PropertyValueFactory<NeighborhoodEntry, String>("pfamDescription"));
                    break;
            }
        }
    }

    public void setProject(Project project) {
        this.project = project;
        updateProjectTree();
        projectTreeView.getSelectionModel().selectFirst();
    }

    @FXML
    private void filterGNNAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Create filtered GNN");
        alert.setHeaderText("Select a whitelist for filtering the GNN");

        ButtonType buttonTypeOne = new ButtonType("Use built in whitelist");
        ButtonType buttonTypeTwo = new ButtonType("Choose custom whitelist");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        File gnnFile = projectTreeView.getSelectionModel().getSelectedItem().getValue().getFile();
        Optional<ButtonType> result = alert.showAndWait();
        final File filterFile;
        if (result.get() == buttonTypeTwo) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose filter file");
            filterFile = fileChooser.showOpenDialog(null);
            if(filterFile == null) return;
        } else if(result.get() == buttonTypeCancel) {
            return;
        } else {
            filterFile = null;
        }
        Task<Void> filterGnnTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("Filtering GNN...");
                updateProgress(-1, -1);
                serviceFacade.addFilteredGnnToProject(project, gnnFile, filterFile);
                return null;
            }
        };
        ProgressDialog progressDialog = new ProgressDialog(filterGnnTask, stage);
        progressDialog.showAndStart(this::updateProjectTree);
    }

    @FXML
    private void requestGNNAction() {
        File ssnFile = projectTreeView.getSelectionModel().getSelectedItem().getValue().getFile();
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Request GNN");
        dialog.setHeaderText("Please provide GNN generation parameters");
        ButtonType loginButtonType = new ButtonType("Request", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ChoiceBox<String> neighborhoodSize = new ChoiceBox<>(FXCollections.observableArrayList(
                "3", "4", "5","6","7","8","9", "10")
        );
        neighborhoodSize.getSelectionModel().selectFirst();

        ArrayList<String> coList = new ArrayList<>();
        for(int i = 1; i <= 100; i++) {
            coList.add("" + i);
        }
        ChoiceBox<String> coOccurrence = new ChoiceBox<>(FXCollections.observableArrayList(coList));
        coOccurrence.getSelectionModel().select(19);
        //TextField coOccurrence = new TextField();
        //coOccurrence.setText("20");

        TextField email = new TextField();
            String savedEMail = serviceFacade.getEMail();
            if(savedEMail == null) {
                email.setText("");
            } else {
                email.setText(savedEMail);
            }
        grid.add(new Label("Neighborhood Size:"), 0, 0);
        grid.add(neighborhoodSize, 1, 0);
        grid.add(new Label("Co-Occurrence:"), 0, 1);
        grid.add(coOccurrence, 1, 1);
        grid.add(new Label("EMail:"), 0,2);
        grid.add(email,1,2);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                ArrayList<String> result = new ArrayList<String>();
                result.add(neighborhoodSize.getValue());
                result.add(coOccurrence.getValue());
                result.add(email.getText());
                serviceFacade.setEMail(email.getText());
                return result;
            }
            return null;
        });

        Optional<List<String>> result = dialog.showAndWait();
        if(result.isPresent()) {
            String neighborhoodSizeString = result.get().get(0);
            String coOccurrenceString = result.get().get(1);
            String emailString = result.get().get(2);
            File filteredSsn = projectTreeView.getSelectionModel().getSelectedItem().getValue().getFile();
            Task<Void> requestGnnTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateMessage("Requesting GNN...");
                    updateProgress(-1, -1);
                    serviceFacade.addGnnToProject(project, filteredSsn, Integer.parseInt(neighborhoodSizeString), Integer.parseInt(coOccurrenceString), emailString);
                    return null;
                }
            };
            ProgressDialog progressDialog = new ProgressDialog(requestGnnTask, stage);
            progressDialog.showAndStart(this::updateProjectTree);
        }

    }

    @FXML
    private void addGNNAction() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Raw GNN");
        dialog.setHeaderText("Please enter the EFI download URL");
        dialog.setContentText("URL:");
        Optional<String> result = dialog.showAndWait();
        File ssnFile = projectTreeView.getSelectionModel().getSelectedItem().getValue().getFile();
        if(result.isPresent()) {
            Task<Void> addSsnTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateMessage("Requesting GNN...");
                    updateProgress(-1, -1);
                    serviceFacade.addGnnToProject(project, ssnFile,result.get());
                    return null;
                }
            };
            ProgressDialog progressDialog = new ProgressDialog(addSsnTask, stage);
            progressDialog.showAndStart(this::updateProjectTree);
        }
    }

    @FXML
    private void addRawGNNAction() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Raw GNN");
        dialog.setHeaderText("Please enter the EFI download URL");
        dialog.setContentText("URL:");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()) {
            Task<Void> addSsnTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateMessage("Requesting GNN...");
                    updateProgress(-1, -1);
                    serviceFacade.addGnnToProject(project, result.get());
                    return null;
                }
            };
            ProgressDialog progressDialog = new ProgressDialog(addSsnTask, stage);
            progressDialog.showAndStart(this::updateProjectTree);
        }
    }

    @FXML
    private void createFilteredSSNAction() {
        Dialog<Pair<String, Boolean>> dialog = new Dialog<>();
        dialog.setTitle("Create filtered SSN");
        dialog.setHeaderText("Provide information for filtering the SSN");
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField thresholdField = new TextField();
        CheckBox taxonomyBox = new CheckBox();

        grid.add(new Label("Threshold:"), 0, 0);
        grid.add(thresholdField, 1, 0);
        grid.add(new Label("Taxonomy filter:"), 0, 1);
        grid.add(taxonomyBox, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(thresholdField.getText(), taxonomyBox.isSelected());
            }
            return null;
        });

        Optional<Pair<String, Boolean>> result = dialog.showAndWait();
        File ssnFile = projectTreeView.getSelectionModel().getSelectedItem().getValue().getFile();

        if (result.isPresent()) {
            Integer threshold = Integer.parseInt(result.get().getKey());
            Boolean filter = result.get().getValue();
            Task<Void> addSsnTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateMessage("Filtering SSN...");
                    updateProgress(-1, -1);
                    serviceFacade.addFilteredSsnToProject(project, ssnFile, threshold, filter);
                    return null;
                }
            };
            ProgressDialog progressDialog = new ProgressDialog(addSsnTask, stage);
            progressDialog.showAndStart(this::updateProjectTree);
        }
    }

    private void viewCytoscape(File file) {
        try {
            if(serviceFacade.getCytoscapeExecutable() == null) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Cytoscape executable");
                File cytoFile = fileChooser.showOpenDialog(null);
                if(cytoFile != null) {
                    serviceFacade.setCytoscapeExecutable(cytoFile.getAbsolutePath().toString());
                } else {
                    return;
                }
            }
            Runtime.getRuntime().exec(serviceFacade.getCytoscapeExecutable() + " -N " + file.getAbsolutePath());
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("View in Cytoscape");
            alert.setHeaderText("Cytoscape could not be found!");
            alert.setContentText("Please configure the path to the Cytoscape executable in the settings.");
            alert.showAndWait();
            serviceFacade.setCytoscapeExecutable(null);
        }
    }

    @FXML
    private void viewColoredCytoscapeAction() {
        File gnnFile = projectTreeView.getSelectionModel().getSelectedItem().getValue().getFile();
        File coloredFile = project.getColoredSsn(gnnFile);
        viewCytoscape(coloredFile);
    }

    @FXML
    private void viewExtendedCytoscapeAction() {
        File gnnFile = projectTreeView.getSelectionModel().getSelectedItem().getValue().getFile();
        File extendedFile = project.getExtendedGnnFile(gnnFile);
        viewCytoscape(extendedFile);
    }

    @FXML
    private void viewCytoscapeAction() {
        File file = projectTreeView.getSelectionModel().getSelectedItem().getValue().getFile();
        viewCytoscape(file);
    }

    @FXML
    private void addSSNAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open SSN File");
        File ssnFile = fileChooser.showOpenDialog(null);
        if(ssnFile != null) {
            Task<Void> addSsnTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateMessage("Parsing SSN File...");
                    updateProgress(-1,-1);
                    serviceFacade.addSsnToProject(project, ssnFile);
                    return null;
                }
            };
            ProgressDialog progressDialog = new ProgressDialog(addSsnTask, stage);
            progressDialog.showAndStart(this::updateProjectTree);
        }
    }

    private void updateHistogram(SsnAnalyzerResult ssnAnalyzerResult) {
        histogramBarChart.getData().clear();
        histogramBarChart.getXAxis().setLabel("BLAST Score");
        histogramBarChart.getYAxis().setLabel("Count");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("#Edges");

        for(Integer threshold = ssnAnalyzerResult.getMinThreshold(); threshold <= ssnAnalyzerResult.getMaxThreshold(); threshold++) {
            series.getData().add(new XYChart.Data<>(threshold.toString(), ssnAnalyzerResult.getThresholdEdgeCountMap().get(threshold)));
        }

        histogramBarChart.getData().add(series);
    }

    private void updateNsv(SsnAnalyzerResult ssnAnalyzerResult, ApeltsinThreshold apeltsinThreshold) {
        nsvLineChart.getData().clear();
        nsvLineChart.setCreateSymbols(false);
        nsvLineChart.getXAxis().setLabel("BLAST Score");
        nsvLineChart.getYAxis().setLabel("Count");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Nsv(Th)");

        for(Integer threshold = ssnAnalyzerResult.getMinThreshold(); threshold <= ssnAnalyzerResult.getMaxThreshold(); threshold++) {
            series.getData().add(new XYChart.Data<>(threshold.toString(), apeltsinThreshold.nsv(threshold)));
        }

        nsvLineChart.getData().add(series);
    }

    private void updateProjectTree() {
        ProjectTreeItem rootItem = new ProjectTreeItem(project.getName());
        TreeItem<ProjectTreeItem> rootTreeItem = new TreeItem<>(rootItem);
        projectTreeView.setRoot(rootTreeItem);

        List<File> ssnFiles = project.getSsnFiles();
        for (File ssnFile : ssnFiles) {
            SsnAnalyzerResult ssnResult = project.getAnalyzerResult(ssnFile);
            String prefix;
            if (ssnResult.isRepresentative()) {
                prefix = "[REP] ";
            } else {
                prefix = "[FULL] ";
            }
            ProjectTreeItem ssnItem = new ProjectTreeItem(prefix + ssnResult.getName(), ProjectTreeItem.ItemType.SSN, ssnFile);
            TreeItem<ProjectTreeItem> ssnTreeItem = new TreeItem<>(ssnItem);
            rootTreeItem.getChildren().add(ssnTreeItem);

            List<File> filterFiles = project.getFilteredFiles(ssnFile);
            for (File filterFile : filterFiles) {
                SsnAnalyzerResult filterResult = project.getAnalyzerResult(filterFile);
                String taxonomyFiltered = filterResult.isTaxonomyFiltered() ? " [TAX]" : "";
                String name = "[FIL] >" + filterResult.getMinThreshold() + taxonomyFiltered;
                ProjectTreeItem filterItem = new ProjectTreeItem(name, ProjectTreeItem.ItemType.FILTERED_SSN, filterFile);
                TreeItem<ProjectTreeItem> filterTreeItem = new TreeItem<>(filterItem);
                ssnTreeItem.getChildren().add(filterTreeItem);

                List<File> gnnFiles = project.getGNNFiles(filterFile);
                for (File gnnFile : gnnFiles) {
                    FullGnn fullGnn = project.getFullGnn(gnnFile);
                    name = "[GNN] nh=" + fullGnn.getNeighborhoodSize() + " co=" + fullGnn.getCoocurrence();
                    ProjectTreeItem gnnItem = new ProjectTreeItem(name, ProjectTreeItem.ItemType.GNN, gnnFile);
                    TreeItem<ProjectTreeItem> gnnTreeItem = new TreeItem<>(gnnItem);
                    filterTreeItem.getChildren().add(gnnTreeItem);

                    List<File> filteredGnnFiles = project.getFilteredGNNFiles(gnnFile);
                    for (File filteredGnnFile : filteredGnnFiles) {
                        try {
                            File whiteListFile = project.getFilterFile(filteredGnnFile);
                            PfamFilter pfamFilter = new PfamFilter(whiteListFile);
                            name = "[FIL] #" + pfamFilter.getWhiteListSize();
                            ProjectTreeItem filteredGnnItem = new ProjectTreeItem(name, ProjectTreeItem.ItemType.FILTERED_GNN, filteredGnnFile);
                            TreeItem<ProjectTreeItem> filteredGnnTreeItem = new TreeItem<>(filteredGnnItem);
                            gnnTreeItem.getChildren().add(filteredGnnTreeItem);
                        } catch (IOException ex) {

                        }
                    }
                }
            }
        }
        for (File gnnFile : project.getGNNFiles()) {
            FullGnn fullGnn = project.getFullGnn(gnnFile);
            String name = "[GNN] nh=" + fullGnn.getNeighborhoodSize() + " co=" + fullGnn.getCoocurrence();
            ProjectTreeItem gnnItem = new ProjectTreeItem(name, ProjectTreeItem.ItemType.GNN, gnnFile);
            TreeItem<ProjectTreeItem> gnnTreeItem = new TreeItem<>(gnnItem);
            rootTreeItem.getChildren().add(gnnTreeItem);

            List<File> filteredGnnFiles = project.getFilteredGNNFiles(gnnFile);
            for (File filteredGnnFile : filteredGnnFiles) {
                try {
                    File whiteListFile = project.getFilterFile(filteredGnnFile);
                    PfamFilter pfamFilter = new PfamFilter(whiteListFile);
                    name = "[FIL] #" + pfamFilter.getWhiteListSize();
                    ProjectTreeItem filteredGnnItem = new ProjectTreeItem(name, ProjectTreeItem.ItemType.FILTERED_GNN, filteredGnnFile);
                    TreeItem<ProjectTreeItem> filteredGnnTreeItem = new TreeItem<>(filteredGnnItem);
                    gnnTreeItem.getChildren().add(filteredGnnTreeItem);
                } catch (IOException ex) {

                }

            }
            rootTreeItem.setExpanded(true);
        }
    }

    @Override
    public void changed(ObservableValue<? extends TreeItem<ProjectTreeItem>> observable, TreeItem<ProjectTreeItem> oldValue, TreeItem<ProjectTreeItem> newValue) {
        if(newValue == null) return;
        projectTitledPane.setVisible(false);
        ssnTitledPane.setVisible(false);
        gnnTitledPane.setVisible(false);

        ProjectTreeItem treeItem = newValue.getValue();
        boolean isSSN = false;
        boolean isGNN = false;
        switch (treeItem.getItemType()) {
            case PROJECT:
                projectTitledPane.setText("Project: " + project.getName());
                projectTitledPane.setVisible(true);
                break;
            case SSN:
                isSSN = true;
                createFilteredButton.setVisible(true);
                createFilteredButton.setManaged(true);
                requestGNNButton.setVisible(false);
                requestGNNButton.setManaged(false);
                addGNNButton.setVisible(false);
                addGNNButton.setManaged(false);
                apeltsinThLabel.setManaged(true);
                apeltsinThLabel.setVisible(true);
                smoothThLabel.setManaged(true);
                smoothThLabel.setVisible(true);
            case FILTERED_SSN:
                if(!isSSN) {
                    createFilteredButton.setVisible(false);
                    createFilteredButton.setManaged(false);
                    requestGNNButton.setVisible(true);
                    requestGNNButton.setManaged(true);
                    addGNNButton.setVisible(true);
                    addGNNButton.setManaged(true);
                    apeltsinThLabel.setManaged(false);
                    apeltsinThLabel.setVisible(false);
                    smoothThLabel.setManaged(false);
                    smoothThLabel.setVisible(false);
                }
                SsnAnalyzerResult ssnResult = project.getAnalyzerResult(treeItem.getFile());
                String type = ssnResult.isRepresentative() ? "Representative" : "Full";
                ApeltsinThreshold apeltsinThreshold = new ApeltsinThreshold(ssnResult);
                SmoothThreshold smoothThreshold = new SmoothThreshold(ssnResult);
                ssnTitledPane.setText(type + " SSN: " + ssnResult.getName());
                sequenceCountLabel.setText("#Sequences: " + ssnResult.getTotalSequenceCount());
                nodeCountLabel.setText("#Nodes: " + ssnResult.getSequenceCount());
                edgeCountLabel.setText("#Edges: " + ssnResult.getSimilarityCount());
                minThLabel.setText("Min Score: " + ssnResult.getMinThreshold());
                maxThLabel.setText("Max Score: " + ssnResult.getMaxThreshold());
                apeltsinThLabel.setText("A-Th: " + apeltsinThreshold.getThreshold());
                smoothThLabel.setText("S-Th: " + smoothThreshold.getThreshold());
                updateHistogram(ssnResult);
                updateNsv(ssnResult, apeltsinThreshold);
                ssnTitledPane.setVisible(true);
                break;
            case GNN:
                isGNN = true;
            case FILTERED_GNN:
                FullGnn fullGnn = project.getFullGnn(treeItem.getFile());
                gnnTitledPane.setText("GNN: " + fullGnn.getGnn().getLabel());
                neighborhoodLabel.setText("Neighborhood Size: " + fullGnn.getNeighborhoodSize());
                coocurrenceLabel.setText("Coocurrence: " + fullGnn.getCoocurrence());
                gnnSequenceCountLabel.setText("#Sequences: " + fullGnn.getSequenceCount());
                clusterCountLabel.setText("#Clusters: " + fullGnn.clusterCount());
                neighborhoodCountLabel.setText("#Pfams: " + fullGnn.getPfamMap().keySet().size());
                hyperNeighborhoodTable.setItems(getNeighborhoodEntryList(treeItem.getFile()));
                pfamTable.setItems(getPfamEntryList(treeItem.getFile()));
                clusterTable.setItems(getClusterEntryList(treeItem.getFile()));
                gnnTitledPane.setVisible(true);
                filterGNNButton.setManaged(true);
                filterGNNButton.setVisible(true);

                if (!isGNN) {
                    filterGNNButton.setVisible(false);
                    filterGNNButton.setManaged(false);
                }
                break;
        }
    }

    private ObservableList<PfamEntry> getPfamEntryList(File gnnFile) {
        GnnStatistic gnnStatistic = project.getStatGnn(gnnFile);
        FullGnn fullGnn = project.getFullGnn(gnnFile);

        List<PfamEntry> list = new ArrayList<>();

        for(String pfamId : fullGnn.getPfamMap().keySet()) {
            Pfam pfam = fullGnn.getPfamMap().get(pfamId);
            Integer pfamSequences = fullGnn.getPfamSequenceCount(pfam.getId());
            Double uniqueness = gnnStatistic.getPfamUniqueness().get(pfam.getPfam());

            StringBuffer go = new StringBuffer();
            for(Pfam2GoEntry entry : serviceFacade.getGo(pfam.getPfam())) {
                go.append(entry.getGo() + " (" + entry.getDescription() + "); ");
            }

            PfamEntry pfamEntry = new PfamEntry(pfam.getPfam(), pfamSequences, uniqueness, go.toString(), pfam.getPfamDescription());
            list.add(pfamEntry);
        }

        return FXCollections.observableList(list);
    }

    private ObservableList<ClusterEntry> getClusterEntryList(File gnnFile) {
        GnnStatistic gnnStatistic = project.getStatGnn(gnnFile);
        FullGnn fullGnn = project.getFullGnn(gnnFile);
        ColoredSsnAnalyzerResult coloredSsnAnalyzerResult = project.getColoredSsnAnalyzerResult(gnnFile);

        List<ClusterEntry> list = new ArrayList<>();

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);

        Set<String> clusterSet = new HashSet<>();
        for(String clusterId : fullGnn.getClusterMap().keySet()) {
            Cluster cluster = fullGnn.getClusterMap().get(clusterId);
            if(!clusterSet.contains(cluster.getClusterNumber())) {
                clusterSet.add(cluster.getClusterNumber());

                StringBuffer phylumStat = new StringBuffer();
                Map<String, Double> unsortedPhylumStats = coloredSsnAnalyzerResult.getClusterPhylumPercentageMap().get(Integer.parseInt(cluster.getClusterNumber()));
                if(unsortedPhylumStats != null) {
                    Map<String, Double> phylumStats = ServiceFacade.sortByValue(unsortedPhylumStats);
                    for (String phylum : phylumStats.keySet()) {
                        Double value = phylumStats.get(phylum);
                        if(value != null) phylumStat.append(phylum + ": " + numberFormat.format(value) + " ");
                    }
                }

                Double uniqueness = gnnStatistic.getClusterUniqueness().get(cluster.getClusterNumber());
                ClusterEntry clusterEntry = new ClusterEntry(Integer.parseInt(cluster.getClusterNumber()),
                        Integer.parseInt(cluster.getTotalSsnSequences()), uniqueness,
                        coloredSsnAnalyzerResult.getClusterNodeCountMap().get(Integer.parseInt(cluster.getClusterNumber())),
                        phylumStat.toString());
                list.add(clusterEntry);
            }
        }

        return FXCollections.observableList(list);
    }

    private ObservableList<NeighborhoodEntry> getNeighborhoodEntryList(File gnnFile) {
        FullGnn fullGnn = project.getFullGnn(gnnFile);
        GnnStatistic gnnStatistic = project.getStatGnn(gnnFile);

        List<NeighborhoodEntry> list = new ArrayList<>();

        for(String clusterId : fullGnn.getClusterMap().keySet()) {
            Cluster cluster = fullGnn.getClusterMap().get(clusterId);
            Neighborhood neighborhood = fullGnn.getClusterNeighborhoodMap().get(clusterId);
            Pfam pfam = fullGnn.getPfamMap().get(neighborhood.getSource());

            Integer clusterNumber = Integer.parseInt(cluster.getClusterNumber());
            Integer clusterSequences = Integer.parseInt(cluster.getTotalSsnSequences());
            Integer pfamSequences = fullGnn.getPfamSequenceCount(pfam.getId());
            Integer neighborhoodSequences = fullGnn.getNeighborhoodSequenceCount(neighborhood);
            Double uniqueness = gnnStatistic.getNeighborhoodUniqueness().get(neighborhood.getLabel());
            Double coverage = ((double)fullGnn.getNeighborhoodSequenceCount(neighborhood)) / Double.parseDouble(cluster.getTotalSsnSequences());

            StringBuffer go = new StringBuffer();
            for(Pfam2GoEntry entry : serviceFacade.getGo(pfam.getPfam())) {
                go.append(entry.getGo() + " (" + entry.getDescription() + "); ");
            }
            NeighborhoodEntry neighborhoodEntry = new NeighborhoodEntry(clusterNumber,clusterSequences, pfam.getPfam(), pfamSequences, neighborhoodSequences, uniqueness, go.toString(), pfam.getPfamDescription(), coverage );
            list.add(neighborhoodEntry);

        }
        return FXCollections.observableList(list);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
