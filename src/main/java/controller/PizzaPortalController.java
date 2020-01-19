package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Pizza;
import model.PizzaList;
import service.WindowService;
import utility.InMemoryDb;

import java.io.IOException;

public class PizzaPortalController {
    // obiekty globalne
    private WindowService windowService;
    // przechowuje listę pizz
    private ObservableList pizzas = FXCollections.observableArrayList(InMemoryDb.pizzaLists);
    @FXML
    private Label lblLogin;
    @FXML
    private Tab tabMenu;
// ----------------------- LIST PIZZ -----------------------------
    @FXML
    private TableView<PizzaList> tblPizza;
    @FXML
    private TableColumn<PizzaList, String> tcName;
    @FXML
    private TableColumn<PizzaList, String> tcIngredients;
    @FXML
    private TableColumn<PizzaList, String> tcDescription;
    @FXML
    private TableColumn<PizzaList, Double> tcPrice;
    @FXML
    private TableColumn<PizzaList, Integer> tcQuantity;
// ---------------------------------------------------------------
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
        Platform.exit();
    }

    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        windowService.createWindow("loginView", "panel logowania");    // otwarcie okna logowania
        windowService.closeWindow(lblLogin);                                        // zamknięcie aktualnie otwartego okna
    }


    public void initialize(){
        windowService = new WindowService();
        // konfiguracja wartości wprowadzanych do kolumn tblPizza
        tcName.setCellValueFactory(new PropertyValueFactory<PizzaList, String>("name"));
        tcIngredients.setCellValueFactory(new PropertyValueFactory<PizzaList,String>("ingredients"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<PizzaList, String>("description"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<PizzaList, Double>("price"));
        tcQuantity.setCellValueFactory(new PropertyValueFactory<PizzaList, Integer>("quantity"));
        // wprowadzenie wartości do tbl
        tblPizza.setItems(pizzas);
    }

}
