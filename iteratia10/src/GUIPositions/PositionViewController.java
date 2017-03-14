package GUIPositions;

import Domain.Position;
import Service.PositionService;
import Utils.IObserver;
import Validator.ValidatorException;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import Exception.CustomException;
import java.util.List;

public class PositionViewController implements IObserver<Position> {
    private ObservableList<Position> model;
    private PositionView view;
    private PositionService service;

    public PositionViewController(PositionService service, PositionView view) {
        this.service = service;
        this.view = view;
        this.model = FXCollections.observableArrayList(service.getAll());
        view.positionsTable.setItems(model);
        service.addObserver(this);
    }

    public void update(List<Position> list) {
        model.setAll(list);
    }

    public ChangeListener<Position> changedTableItemListener() {
        ChangeListener<Position> changeListener = ((observable, oldValue, newValue) -> {
            showPositionDetails(newValue);
            view.txtID.setEditable(false);
        });

        return changeListener;
    }

    public void showPositionDetails(Position value) {
        if(value == null) {
            view.txtID.setText("");
            view.txtName.setText("");
            view.txtType.setText("");
        } else {
            view.txtID.setText(value.getId().toString());
            view.txtName.setText(value.getName());
            view.txtType.setText(value.getType());
        }
    }

    private Position extractPosition() {
        Integer id = Integer.parseInt(view.txtID.getText());
        String name = view.txtName.getText();
        String type = view.txtType.getText();
        return new Position(id, name, type);
    }

    public void handleAdd(ActionEvent e) {
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

    public void handleDelete(ActionEvent e) {
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

    public void handleUpdate(ActionEvent e) {
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

    public void handleClearAll(ActionEvent e) {
        view.txtID.setEditable(true);
        showPositionDetails(null);
    }

    public void showErrorMessage(String text) {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error message");
        message.setContentText(text);
        message.showAndWait();
    }
}
