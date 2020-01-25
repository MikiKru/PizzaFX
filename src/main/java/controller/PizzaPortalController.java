package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Pizza;
import model.PizzaList;
import service.LoginService;
import service.PizzaPortalService;
import service.WindowService;
import utility.InMemoryDb;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PizzaPortalController {
    // obiekty globalne
    private WindowService windowService;
    private PizzaPortalService pizzaPortalService;
    // przechowuje listę pizz
    private ObservableList pizzas = FXCollections.observableArrayList();
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

    private void clearOrder(){
        List<PizzaList> pizzaLists = pizzaPortalService.clearPizzaOrder();
        pizzas.clear();
        pizzas.addAll(pizzaLists);
        // wyczyszczenie tabelki
        tblPizza.setItems(pizzas);    // aktualizacja tabelki
        lblSum.setText("do zapłaty: 0.00 zł");
    }

    @FXML
    void addToBasketAction(ActionEvent event) throws IOException {
        if(pizzaPortalService.calculatePizzaOrder() > 0) {
            pizzaPortalService.addOrderToBasket(LoginService.loggedUser.getLogin()); // login zalogowanego użytkownika
            clearOrder();
            WindowService.getAlertWindow(
                    AlertType.INFORMATION,
                    "Dodawanie do koszyka",
                    "Złożono zamówienie",
                    "Dziękujemy za złożenie zamówienia. Możesz w zakładce koszyk śledzić jego status");
        } else {
            WindowService.getAlertWindow(
                    AlertType.WARNING,
                    "Dodawanie do koszyka",
                    "Nie wybrano żadnego produktu",
                    "Musisz wybrać jakiś produkt aby zrealizować zamówienie");
        }
    }
    @FXML
    void clearAction(ActionEvent event) {
        clearOrder();
    }
    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        windowService.createWindow("loginView", "panel logowania");    // otwarcie okna logowania
        windowService.closeWindow(lblLogin);                                        // zamknięcie aktualnie otwartego okna
        clearOrder();
    }

    @FXML
    void exitAction(ActionEvent event) {
        Platform.exit();
    }



    @FXML
    void selectPizzaAction(MouseEvent event) {
        List<PizzaList> pizzaLists = pizzaPortalService.selectPizza(tblPizza);
        pizzas.clear();
        pizzas.addAll(pizzaLists);
        // wyczyszczenie tabelki
        tblPizza.setItems(pizzas);    // aktualizacja tabelki
        // aktualizacja ceny do zapłaty
        lblSum.setText(String.format("do zapłaty: %.2f zł", pizzaPortalService.calculatePizzaOrder()));
    }

    private PizzaList pizzaOfDay;
    public void initialize(){
        // wypisanie loginu zalogowanego użytkownika na lbl
        lblLogin.setText("zalogowano: " + LoginService.loggedUser.getLogin());
        // ---------------------------------------------------------
        pizzaPortalService = new PizzaPortalService();  // nowa instancja klasy PPS
        // mapowanie enuma do PizzaList
        PizzaPortalService.mapPizzaToPizzaList();
        pizzas.addAll(InMemoryDb.pizzaLists);
        windowService = new WindowService();
        // generowanie pizzy dnia aktualizacja ceny i wypisanie na lbl
        pizzaOfDay = pizzaPortalService.generatePizzaOfDay();
        List<PizzaList> pizzaLists = pizzaPortalService.setDiscount(pizzaOfDay, 30);
        pizzas.clear();
        pizzas.addAll(pizzaLists);
        lblPizzaOfDay.setText("PIZZA DNIA TO " + pizzaOfDay.getName().toUpperCase() + "!");
        // konfiguracja wartości wprowadzanych do kolumn tblPizza
        tcName.setCellValueFactory(new PropertyValueFactory<PizzaList, String>("name"));
        tcIngredients.setCellValueFactory(new PropertyValueFactory<PizzaList,String>("ingredients"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<PizzaList, String>("description"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<PizzaList, Double>("price"));
        tcQuantity.setCellValueFactory(new PropertyValueFactory<PizzaList, Integer>("quantity"));
        // formatowanie do typu NumberFormat
        Locale locale = new Locale("pl", "PL");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        tcPrice.setCellFactory(tc -> new TableCell<PizzaList, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(price));
                }
            }
        });
        // wprowadzenie wartości do tbl
        tblPizza.setItems(pizzas);
    }

}
