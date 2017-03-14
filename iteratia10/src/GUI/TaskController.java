package GUI;

import Domain.Position;
import Domain.Task;
import Service.PositionService;
import Service.TaskService;
import Utils.IObserver;
import Validator.ValidatorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import Exception.CustomException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TaskController implements IObserver<Task> {
    @FXML private TableView<Task> tasksTables;
    @FXML private TableColumn<Task, Integer> tasksDescriptionCol;
    @FXML private TableColumn<Task, Integer> tasksDurationCol;
    @FXML private TextField txtID;
    @FXML private TextField txtDescription;
    @FXML private TextField txtDuration;

    ObservableList<Task> model;
    TaskService service;

    public TaskController() { }

    public void setService(TaskService service) {
        this.service = service;
        this.model = FXCollections.observableArrayList(service.getAll());
        tasksTables.setItems(model);
    }

    @FXML
    private void initialize() {
        tasksDescriptionCol.setCellValueFactory(new PropertyValueFactory<Task, Integer>("description"));
        tasksDurationCol.setCellValueFactory(new PropertyValueFactory<Task, Integer>("duration"));
    }

    private Task extractTask() {
        Integer id = Integer.parseInt(txtID.getText());
        String description = txtDescription.getText();
        Integer duration = Integer.parseInt(txtDuration.getText());
        return new Task(id, description, duration);
    }

    @FXML
    public void handleAdd() {
        try {
            Task t = extractTask();
            service.add(t);
        } catch(ValidatorException exp) {
            showErrorMessage(exp.getMessage());
        }catch (CustomException exp) {
            showErrorMessage(exp.getMessage());
        }catch (Exception exp) {
            showErrorMessage(exp.getMessage());
        }
    }

    @FXML
    public  void handleDelete() {
        try {
            Task t = extractTask();
            service.delete(t.getId());
        } catch(ValidatorException exp) {
            showErrorMessage(exp.getMessage());
        }catch (CustomException exp) {
            showErrorMessage(exp.getMessage());
        }catch (Exception exp) {
            showErrorMessage(exp.getMessage());
        }
    }

    @FXML
    public void handleUpdate() {
        try {
            Task t = extractTask();
            service.update(t);
        } catch(ValidatorException exp) {
            showErrorMessage(exp.getMessage());
        }catch (CustomException exp) {
            showErrorMessage(exp.getMessage());
        }catch (Exception exp) {
            showErrorMessage(exp.getMessage());
        }
    }

    public void showTaskDetails(Task value) {
        if(value == null) {
            txtID.setText("");
            txtDescription.setText("");
            txtDuration.setText("");
        } else {
            txtID.setText(value.getId().toString());
            txtDescription.setText(value.getDescription());
            txtDuration.setText(value.getDuration().toString());
        }
    }

    @FXML
    public void populateFields() {
        showTaskDetails(tasksTables.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void handleClearAll() {
        txtID.setEditable(true);
        showTaskDetails(null);
    }

    // Filtarea listei dupa conditia data de predicat
    public <E> List<E> filterGeneric(List<E> list, Predicate<E> cond)
    {
        List<E> filterList = new ArrayList<E>();
        for(E e : list)
            if(cond.test(e))
                filterList.add(e);

        return  filterList;
    }

    // Filtarea task-urilor dupa descrierea care incepe cu un anumit string
    public List<Task> filterTaskByDescriptionStartsWith(String description)
    {
        Predicate<Task> cond = x -> x.getDescription().startsWith(description);
        return filterGeneric(service.getAll(), cond);
    }

    // Filtarea task-urilor dupa descrierea care este egala cu un anumit string
    public List<Task> filterPositionByDuration(Integer duration)
    {
        Predicate<Task> cond = x -> x.getDuration() > duration;
        return filterGeneric(service.getAll(), cond);
    }

    @FXML
    public void filterByDescription() {
        model.setAll(filterTaskByDescriptionStartsWith(txtDescription.getText()));
    }

    @FXML
    public void filterByDuration() {
        model.setAll(filterPositionByDuration(Integer.parseInt(txtDuration.getText())));
    }

    @FXML
    public void resetList() {
        model.setAll(service.getAll());
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
