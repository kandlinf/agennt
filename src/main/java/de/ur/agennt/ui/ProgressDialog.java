package de.ur.agennt.ui;


import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class ProgressDialog implements EventHandler<WorkerStateEvent> {
    private Task task;
    private Stage stage;
    private Label statusLabel;
    private ProgressBar progressBar;
    private Button cancelButton;
    private Button forceButton;
    private Runnable doneFunction;
    private Runnable forceDownload;

    public ProgressDialog(Task executeTask, Window parent, Runnable forceDownload) {
        this.forceDownload = forceDownload;
        task = executeTask;
        task.setOnRunning(this);
        task.setOnSucceeded(this);
        task.setOnFailed(this);
        stage = new Stage();
        stage.initOwner(parent);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Operation in progress...");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setWidth(520);
        stage.setHeight(170);
        stage.setResizable(false);
        buildUI();
    }

    public ProgressDialog(Task executeTask, Window parent) {
        this(executeTask, parent, null);
    }

    private void buildUI() {
        GridPane grid = new GridPane();
        grid.setMinSize(500, 150);
        grid.setVgap(20);
        grid.setPadding(new Insets(10, 10, 10, 10));

        statusLabel = new Label(task.getMessage());
        statusLabel.setTextAlignment(TextAlignment.LEFT);
        statusLabel.setMinWidth(490);
        grid.add(statusLabel, 0, 0);

        progressBar = new ProgressBar();
        progressBar.setProgress(task.getProgress());
        progressBar.setMinWidth(490);
        grid.add(progressBar, 0, 1);

        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        forceButton = new Button("Force Download");
        forceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                task.cancel();
                stage.close();
                forceDownload.run();
            }
        });

        if (forceDownload != null) hBox.getChildren().add(forceButton);

        cancelButton = new Button("Cancel");
        cancelButton.setDefaultButton(true);
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                task.cancel();
                stage.close();
            }
        });
        hBox.getChildren().add(cancelButton);
        grid.add(hBox, 0, 2);

        grid.setHalignment(hBox, HPos.RIGHT);

        Scene scene = new Scene(grid, 520, 170);
        stage.setScene(scene);
    }


    @Override
    public void handle(WorkerStateEvent event) {
        if (event.getEventType() == WorkerStateEvent.WORKER_STATE_RUNNING) {
            statusLabel.setText(task.getMessage());
            progressBar.setProgress(task.getProgress());
        } else if (event.getEventType() == WorkerStateEvent.WORKER_STATE_FAILED) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(task.getMessage());
            alert.setHeaderText("Operation failed");
            task.getException().printStackTrace();
            alert.setContentText(task.getException().getMessage());
            alert.showAndWait();
            stage.close();
        } else if (event.getEventType() == WorkerStateEvent.WORKER_STATE_SUCCEEDED) {
            stage.close();
            if (doneFunction != null) doneFunction.run();
        }
    }

    public void showAndStart(Runnable doneFunction) {
        this.doneFunction = doneFunction;
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        stage.showAndWait();
    }


}
