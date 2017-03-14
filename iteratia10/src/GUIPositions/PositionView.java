package GUIPositions;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.Node;
import Domain.Position;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PositionView {
    BorderPane borderPane;

    TableView<Position> positionsTable = new TableView<>();
    TableColumn<Position, Integer> firstCol = new TableColumn<>("Descriere");
    TableColumn<Position, Integer> secondCol = new TableColumn<>("Tip");

    TextField txtID = new TextField();
    TextField txtName = new TextField();
    TextField txtType = new TextField();

    Button btnAdd = new Button("Add");
    Button btnDelete = new Button("Delete");
    Button btnUpdate = new Button("Update");
    Button btnClearAll = new Button("Clear All");

    PositionViewController ctrl;

    public void setController(PositionViewController ctrl) {
        this.ctrl = ctrl;
    }
    public void init() {
        initBorderPane();
    }

    public BorderPane getView() {
        return borderPane;
    }

    private void initBorderPane() {
        borderPane = new BorderPane();
        borderPane.setTop(initTop());
        borderPane.setCenter(initCenter());
        borderPane.setLeft(initLeft());
    }
    private Label createLabel(String s, int fontSize, Color c){
        Label l = new Label();
        l.setText(s);
        l.setFont(new Font(fontSize));
        l.setTextFill(c);
        return l;
    }

    private Node initTop() {
        AnchorPane anchorPane = new AnchorPane();
        Label title = new Label("Managementul posturilor");
        title.setFont(new Font(20));
        title.setStyle("-fx-font-weight: bold");
        AnchorPane.setTopAnchor(title, 20d);
        AnchorPane.setRightAnchor(title, 180d);
        anchorPane.getChildren().add(title);
        return anchorPane;
    }

    private Node initLeft() {
        AnchorPane anchorPane = new AnchorPane();
        AnchorPane.setLeftAnchor(positionsTable, 20d);
        anchorPane.setTopAnchor(positionsTable, 20d);

        positionsTable.setMinHeight(50d);
        positionsTable.setPrefHeight(300d);
        initTabelView();
        anchorPane.getChildren().add(positionsTable);
        return anchorPane;
    }

    private Node initCenter() {
        AnchorPane anchorPane = new AnchorPane();
        GridPane gridPositionDetails = new GridPane();
        gridPositionDetails.setHgap(5);
        gridPositionDetails.setVgap(5);
        AnchorPane.setLeftAnchor(gridPositionDetails, 20d);
        AnchorPane.setTopAnchor(gridPositionDetails, 20d);
        ColumnConstraints c=  new ColumnConstraints();
        c.setPrefWidth(100);
        gridPositionDetails.getColumnConstraints().add(c);

        Label labelId = createLabel("ID: ", 12, Color.BLACK);
        labelId.setStyle("-fx-font-weight: bold");
        Label labelName = createLabel("Descriere: ", 12, Color.BLACK);
        labelId.setStyle("-fx-font-weight: bold");
        Label labelType = createLabel("Tip: ", 12, Color.BLACK);
        labelId.setStyle("-fx-font-weight: bold");

        gridPositionDetails.add(labelId, 0, 0);
        gridPositionDetails.add(txtID, 1, 0);
        gridPositionDetails.add(labelName, 0, 1);
        gridPositionDetails.add(txtName, 1, 1);
        gridPositionDetails.add(labelType, 0, 2);
        gridPositionDetails.add(txtType, 1, 2);

        anchorPane.getChildren().add(gridPositionDetails);

        HBox hb = new HBox(5, btnAdd, btnDelete, btnUpdate, btnClearAll);
        btnAdd.setOnAction(ctrl::handleAdd);
        btnDelete.setOnAction(ctrl::handleDelete);
        btnUpdate.setOnAction(ctrl::handleUpdate);
        btnClearAll.setOnAction(ctrl::handleClearAll);

        AnchorPane.setBottomAnchor(hb, 100d);
        AnchorPane.setLeftAnchor(hb, 20d);
        anchorPane.getChildren().add(hb);

        return anchorPane;
    }

    public void initTabelView() {
        positionsTable.getColumns().add(firstCol);
        positionsTable.getColumns().add(secondCol);

        firstCol.setCellValueFactory(new PropertyValueFactory<Position, Integer>("name"));
        secondCol.setCellValueFactory(new PropertyValueFactory<Position, Integer>("type"));

        positionsTable.getSelectionModel().selectedItemProperty().addListener(ctrl.changedTableItemListener());
        positionsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ctrl.showPositionDetails(null);
    }
}
