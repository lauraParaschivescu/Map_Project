package Main;

import GUI.JobDescriptionController;
import GUI.PositionContoller;
import GUI.RootLayoutContoller;
import GUI.TaskController;
import Repository.*;
import Service.PositionService;
import Service.TaskService;
import Validator.PositionValidator;
import Validator.TaskValidator;
import XML.SaxSchemaXMLPositionRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    BorderPane rootLayout;
    AnchorPane innerLayout;
    Stage primaryStage;

    TaskService serviceTasks;
    PositionService servicePositions;
    JobDescriptionRepository repoJobDecription;

    public void start(Stage stage) {
        //IRepository<Position, Integer> repoPositions = new FilePositionRepository(new PositionValidator(), "D:/faculta/semIII/2_map/tema/iteratia10/positions.txt");
        FileSerializableTaskRepository repoTasks = new FileSerializableTaskRepository(new TaskValidator(), "D:/faculta/semIII/2_map/tema/iteratia10/tasks.ser");
        SaxSchemaXMLPositionRepository repoPositions = new SaxSchemaXMLPositionRepository(new PositionValidator(), "Positions.xml");
        repoJobDecription = new JobDescriptionRepository();

        serviceTasks = new TaskService(repoTasks);
        servicePositions = new PositionService(repoPositions);

        this.primaryStage = stage;
        this.primaryStage.setTitle("My App");

        initRootLayout();
        initPositionsViewLayout();
        initTasksViewLayout();
        initJobDescriptionViewLayout();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            RootLayoutContoller rootController=loader.getController();
            rootController.setMainApp(this);
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void initPositionsViewLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/PositionView.fxml"));
            innerLayout = (AnchorPane) loader.load();
            rootLayout.setCenter(innerLayout);
            PositionContoller viewCtrl=loader.getController();
            viewCtrl.setService(servicePositions);
            servicePositions.addObserver(viewCtrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initTasksViewLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/TaskView.fxml"));
            innerLayout = (AnchorPane) loader.load();
            rootLayout.setCenter(innerLayout);
            TaskController viewCtrl=loader.getController();
            viewCtrl.setService(serviceTasks);
            serviceTasks.addObserver(viewCtrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initJobDescriptionViewLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/JobDescriptionView.fxml"));
            innerLayout = (AnchorPane) loader.load();
            rootLayout.setCenter(innerLayout);
            JobDescriptionController viewCtrl=loader.getController();
            viewCtrl.setService(servicePositions, serviceTasks, repoJobDecription);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        launch(args);
    }
}
