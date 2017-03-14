package GUITasks;

import Domain.Task;
import Repository.FileSerializableTaskRepository;
import Service.TaskService;
import Validator.IValidator;
import Validator.TaskValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    BorderPane rootLayout;
    Stage primaryStage;
    TaskService service;

    public void start(Stage primaryStage) {
        IValidator<Task> val = new TaskValidator();
        FileSerializableTaskRepository repo = new FileSerializableTaskRepository(val, "D:/faculta/semIII/2_map/tema/iteratia9/tasks.ser");
        service = new TaskService(repo);

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TasksApp");

        initRootLayout();
        initTaskView();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUITasks/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTaskView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUITasks/TaskView.fxml"));
            AnchorPane taskView = (AnchorPane) loader.load();
            rootLayout.setCenter(taskView);

            TaskViewControllerTabel viewCtrl = loader.getController();
            viewCtrl.setService(service);
            service.addObserver(viewCtrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        launch(args);
    }

}
