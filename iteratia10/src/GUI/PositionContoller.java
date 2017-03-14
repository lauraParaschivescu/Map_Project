package GUI;

import Domain.Position;
import Service.PositionService;
import Utils.IObserver;
import Validator.ValidatorException;
import javafx.beans.value.ChangeListener;
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

public class PositionContoller implements IObserver<Position> {
    @FXML private TableView<Position> positionsTables;
    @FXML private TableColumn<Position, Integer> positionNameCol;
    @FXML private TableColumn<Position, Integer> positionTypeCol;
    @FXML private TextField txtID;
    @FXML private TextField txtName;
    @FXML private TextField txtType;

    ObservableList<Position> model;
    PositionService service;

    public PositionContoller() {
        //System.out.println("dada");
    }

    public void setService(PositionService service) {
        this.service = service;
        this.model = FXCollections.observableArrayList(service.getAll());
        positionsTables.setItems(model);
    }

    @FXML
    private void initialize() {
        positionNameCol.setCellValueFactory(new PropertyValueFactory<Position, Integer>("name"));
        positionTypeCol.setCellValueFactory(new PropertyValueFactory<Position, Integer>("type"));
    }

    private Position extractPosition() {
        Integer id = Integer.parseInt(txtID.getText());
        String name = txtName.getText();
        String type = txtType.getText();
        return new Position(id, name, type);
    }

    @FXML
    public void handleAdd() {
        try {
            Position position = extractPosition();
            service.add(position);
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
            Position position = extractPosition();
            service.delete(position.getId());
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
            Position position = extractPosition();
            service.update(position);
        } catch(ValidatorException exp) {
            showErrorMessage(exp.getMessage());
        }catch (CustomException exp) {
            showErrorMessage(exp.getMessage());
        }catch (Exception exp) {
            showErrorMessage(exp.getMessage());
        }
    }

    public void showPositionDetails(Position value) {
        if(value == null) {
            txtID.setText("");
            txtName.setText("");
            txtType.setText("");
        } else {
            txtID.setText(value.getId().toString());
            txtName.setText(value.getName());
            txtType.setText(value.getType());
        }
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

    // Filtarea position-urilor dupa campul nume care incepe cu un anumit string
    public List<Position> filterPositionByName(String name)
    {
        Predicate<Position> cond = x -> x.getName().startsWith(name);
        return filterGeneric(service.getAll(), cond);
    }

    // Filtarea position-urilor dupa campul type care este egal cu un anumit string
    public List<Position> filterPositionByType(String type)
    {
        Predicate<Position> cond = x -> x.getType().startsWith(type);
        return filterGeneric(service.getAll(), cond);
    }

    @FXML
    public void filterByName() {
        String name = txtName.getText();
        model.setAll(filterPositionByName(name));
    }

    @FXML
    public void filterByType() {
        String type = txtType.getText();
        model.setAll(filterPositionByType(type));
    }

    @FXML
    public void resetList() {
        model.setAll(service.getAll());
    }

    @FXML
    public void populateFields() {
        showPositionDetails(positionsTables.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void handleClearAll() {
        txtID.setEditable(true);
        showPositionDetails(null);
    }

    @Override
    public void update(List<Position> list) {
        model.setAll(list);
    }

    public void showErrorMessage(String text) {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error message");
        message.setContentText(text);
        message.showAndWait();
    }
}
