package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PizzaPortalController {

    @FXML
    private Label lblLogin;

    @FXML
    private Tab tabMenu;

    @FXML
    private TableView<?> tblPizza;

    @FXML
    private TableColumn<?, ?> tcName;

    @FXML
    private TableColumn<?, ?> tcIngredients;

    @FXML
    private TableColumn<?, ?> tcDescription;

    @FXML
    private TableColumn<?, ?> tcPrice;

    @FXML
    private TableColumn<?, ?> tcQuantity;

    @FXML
    private Label lblSum;

    @FXML
    private Label lblPizzaOfDay;

    @FXML
    void addToBasketAction(ActionEvent event) {

    }

    @FXML
    void clearAction(ActionEvent event) {

    }

    @FXML
    void exitAction(ActionEvent event) {

    }

    @FXML
    void logoutAction(ActionEvent event) {

    }

}
