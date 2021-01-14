package Controller;

import Model.Location;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class LocationsViewController {

    @FXML
    private TableView<?> tblLocation;
    @FXML
    private TableColumn<?, ?> colLocationName;
    @FXML
    private TableColumn<?, ?> colCity;
    @FXML
    private TableColumn<?, ?> colCategory;
    @FXML
    private Button btnToMainMenu;
    @FXML
    private Button btnAddLocation;
    @FXML
    private Button btnEditLocation;
    @FXML
    private Button btnDeleteLocation;


    ObservableList<Location> locationList = FXCollections.observableArrayList();

    @FXML
    void btnAddLocationOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteLocationOnAction(ActionEvent event) {

    }

    @FXML
    void btnEditLocationOnAction(ActionEvent event) {

    }

    @FXML
    void btnToMainMenuOnAction(ActionEvent event) {

    }

    @FXML
    void tblLocationOnMouseClicked(MouseEvent event) {

    }

}
