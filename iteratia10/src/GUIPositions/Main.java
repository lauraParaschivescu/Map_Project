package GUIPositions;

import Domain.Position;
import Repository.FilePositionRepository;
import Repository.IRepository;
import Service.PositionService;
import Validator.PositionValidator;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage stage) {
        IRepository<Position, Integer> repo = new FilePositionRepository(new PositionValidator(), "D:/faculta/semIII/2_map/tema/iteratia9/positions.txt");
        PositionService service = new PositionService(repo);
        PositionView positionView = new PositionView();
        PositionViewController ctrl = new PositionViewController(service, positionView);
        positionView.setController(ctrl);
        positionView.init();

        Parent root = positionView.getView();
        Scene scene = new Scene(root, 600, 450);
        stage.setTitle("MyApp");
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}
