package GUITasks;

import Domain.Task;
import Service.TaskService;
import Utils.IObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Exception.CustomException;
import java.io.IOException;
import java.util.List;

public class TaskViewControllerTabel implements IObserver<Task> {
    @FXML
    private TableView<Task> tasksTable;
    @FXML
    private TableColumn<Task, Integer> firstCol;
    @FXML
    private TableColumn<Task, Integer> secondCol;

    TaskService service;
    ObservableList<Task> model;

    public void setService(TaskService service) {
        this.service = service;
        this.model = FXCollections.observableArrayList(service.getAll());
        tasksTable.setItems(model);
    }

    @FXML
    private void initialize() {
        firstCol.setCellValueFactory(new PropertyValueFactory<Task, Integer>("description"));
        secondCol.setCellValueFactory(new PropertyValueFactory<Task, Integer>("duration"));
    }

    @FXML
    public void handleAdd() {
        showTaskEditDialog(null);
    }

    @FXML
    public  void handleDelete() {
        try {
            service.delete(tasksTable.getSelectionModel().getSelectedItem().getId());
        }catch (CustomException exp) {
            showErrorMessage(exp.getMessage());
        }catch (Exception exp) {
            showErrorMessage(exp.getMessage());
        }
    }

    @FXML
    public void handleUpdate() {
        showTaskEditDialog(tasksTable.getSelectionModel().getSelectedItem());
    }

    public void showTaskEditDialog(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaskViewControllerTabel.class.getResource("EditTaskView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Task");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            EditTaskViewController ctrl = loader.getController();
            ctrl.setService(service, dialogStage, task);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(List<Task> list) {
        model.setAll(list);
    }

    public void showErrorMessage(String text) {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error message");
        message.setContentText(text);
        message.showAndWait();
    }
}
