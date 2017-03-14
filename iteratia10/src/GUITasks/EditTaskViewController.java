package GUITasks;

import Domain.Task;
import Service.TaskService;
import Validator.ValidatorException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Exception.CustomException;

public class EditTaskViewController {
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtDuration;

    private TaskService service;
    private static Stage dialogStage;
    Task task;

    public void setService(TaskService service, Stage stage, Task task) {
        this.service = service;
        this.dialogStage = stage;
        this.task = task;
        if(task != null) {
            txtID.setText(task.getId().toString());
            txtID.setEditable(false);
            txtDescription.setText(task.getDescription());
            txtDuration.setText(task.getDuration().toString());
        }
    }

    @FXML
    public void handleAdd() {
        try {
            Integer id = Integer.parseInt(txtID.getText());
            String description = txtDescription.getText();
            Integer duration = Integer.parseInt(txtDuration.getText());
            if(task == null) {
                Task task = new Task(id, description, duration);
                service.add(task);
            } else {
                Task t = new Task(task.getId(), description, duration);
                service.update(t);
            }
        } catch(ValidatorException exp) {
            showErrorMessage(exp.getMessage());
        }catch (CustomException exp) {
            showErrorMessage(exp.getMessage());
        } catch (NumberFormatException exp) {
            showErrorMessage("campul Id si durata trebuie sa contina un numar");
        } catch (Exception exp) {
            showErrorMessage(exp.getMessage());
        }

        dialogStage.close();
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }

    private void cleanFields() {
        txtID.setText("");
        txtDescription.setText("");
        txtDuration.setText("");
    }

    public void showErrorMessage(String text) {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error message");
        message.setContentText(text);
        message.showAndWait();
    }
}
