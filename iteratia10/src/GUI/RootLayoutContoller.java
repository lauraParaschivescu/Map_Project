package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import Main.Main;

public class RootLayoutContoller {
    Main mainApp;
    @FXML
    MenuItem menuItemFilePositions;
    @FXML
    MenuItem menuItemFileTasks;
    @FXML
    MenuItem menuItemFileJobDescription;

    public RootLayoutContoller() { }

    public void setMainApp(Main app)
    {
        this.mainApp=app;
    }

    @FXML
    public void handlePositionsCRUD()
    {
        mainApp.initPositionsViewLayout();
    }

    @FXML
    public void handleTasksCRUD()
    {
        mainApp.initTasksViewLayout();
    }

    @FXML
    public void handleJobDescriptionCRUD()
    {
        mainApp.initJobDescriptionViewLayout();
    }
}
