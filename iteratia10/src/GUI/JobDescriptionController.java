package GUI;

import Domain.JobDescription;
import Domain.Position;
import Domain.Task;
import Repository.JobDescriptionRepository;
import Service.PositionService;
import Service.TaskService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class JobDescriptionController  {
    @FXML private TableView<Position> positionsTables;
    @FXML private TableColumn<Position, Integer> positionNameCol;
    @FXML private TableColumn<Position, Integer> positionTypeCol;
    @FXML private TableView<Task> tasksTables;
    @FXML private TableColumn<Task, Integer> tasksDescriptionCol;
    @FXML private TableColumn<Task, Integer> tasksDurationCol;

    @FXML private TableView<JobDescription> jobDescriptionTables;
    @FXML private TableColumn<Position, String> jobDescriptionPosition;
    @FXML private TableColumn<List<Task>, String> jobDescriptionTasks;

    ObservableList<Position> modelPosition;
    PositionService servicePosition;

    ObservableList<Task> modelTask;
    TaskService serviceTask;

    ObservableList<JobDescription> modelJobDescription;
    JobDescriptionRepository jobDescriptionRepo;

    public JobDescriptionController() {}

    public void setService(PositionService servicePosition, TaskService serviceTask, JobDescriptionRepository jobDescriptionRepo) {
        this.servicePosition = servicePosition;
        this.serviceTask = serviceTask;
        this.jobDescriptionRepo = jobDescriptionRepo;

        this.modelPosition = FXCollections.observableArrayList(servicePosition.getAll());
        this.modelTask = FXCollections.observableArrayList(serviceTask.getAll());
        this.modelJobDescription = FXCollections.observableArrayList(jobDescriptionRepo.getAll());

        positionsTables.setItems(modelPosition);
        tasksTables.setItems(modelTask);
        tasksTables.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        jobDescriptionTables.setItems(modelJobDescription);
    }

    @FXML
    private void initialize() {
        positionNameCol.setCellValueFactory(new PropertyValueFactory<Position, Integer>("name"));
        positionTypeCol.setCellValueFactory(new PropertyValueFactory<Position, Integer>("type"));
        tasksDescriptionCol.setCellValueFactory(new PropertyValueFactory<Task, Integer>("description"));
        tasksDurationCol.setCellValueFactory(new PropertyValueFactory<Task, Integer>("duration"));
        jobDescriptionPosition.setCellValueFactory(new PropertyValueFactory<Position, String>("position"));
        jobDescriptionTasks.setCellValueFactory(new PropertyValueFactory<List<Task>, String>("tasks"));
    }

    @FXML
    public void handleAdd() {
        Position p = positionsTables.getSelectionModel().getSelectedItem();
        List<Task> tasks = new ArrayList<>();

        for(Task t: tasksTables.getSelectionModel().getSelectedItems()) {
            tasks.add(t);
        }

        JobDescription j = new JobDescription(p, tasks);
        jobDescriptionRepo.add(j);
        modelJobDescription.add(j);
    }

    @FXML
    public  void handleDelete() {
        JobDescription j = jobDescriptionTables.getSelectionModel().getSelectedItem();
        jobDescriptionRepo.delete(j);
        modelJobDescription.remove(j);
    }
}
