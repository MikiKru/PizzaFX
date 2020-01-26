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
import model.Basket;
import model.Pizza;
import model.PizzaList;
import model.Status;
import service.LoginService;
import service.PizzaPortalService;
import service.WindowService;
import utility.InMemoryDb;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class PizzaPortalController {
    // TAB 1 - PIZZA --------------------------------------------------------------------
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

    private void clearOrder() {
        List<PizzaList> pizzaLists = pizzaPortalService.clearPizzaOrder();
        pizzas.clear();
        pizzas.addAll(pizzaLists);
        // wyczyszczenie tabelki
        tblPizza.setItems(pizzas);    // aktualizacja tabelki
        lblSum.setText("do zapłaty: 0.00 zł");
    }

    @FXML
    void addToBasketAction(ActionEvent event) throws IOException {
        if (pizzaPortalService.calculatePizzaOrder() > 0) {
            pizzaPortalService.addOrderToBasket(LoginService.loggedUser.getLogin()); // login zalogowanego użytkownika
            clearOrder();
            WindowService.getAlertWindow(
                    AlertType.INFORMATION,
                    "Dodawanie do koszyka",
                    "Złożono zamówienie",
                    "Dziękujemy za złożenie zamówienia. Możesz w zakładce koszyk śledzić jego status");
            // aktualizacja tablicy koszyków
            addDataToBasketsTable();
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

    // TAB 2 - BASKET --------------------------------------------------------------------
    @FXML
    private TableView<Basket> tblBasket;
    @FXML
    private TableColumn<Basket, Map> tcBasket;
    @FXML
    private TableColumn<Basket, Status> tcStatus;
    @FXML
    private ListView<String> lvBasket;
    @FXML
    private Label lblBasketAmount;
    @FXML
    void showDetailsAction(MouseEvent event) {
        // zaznaczamy rekord w tabelce i pobieramy z niego obiekt klasy Basket
        Basket basket = tblBasket.getSelectionModel().getSelectedItem();
        // wypisanie szczegółowych informacji dot zaznaczonego koszyka
        ObservableList<String> detailBasket = FXCollections.observableArrayList();
        detailBasket.add("STATUS: " + basket.getStatus().getStatusName());
        for(String name : basket.getOrder().keySet()){
            detailBasket.add("Pizza: " + name + " : " + basket.getOrder().get(name) + "szt.");
        }
        lvBasket.setItems(detailBasket);
        // aktualizacja kwoty do zapłaty
        lblBasketAmount.setText(String.format("SUMA: %.2f PLN", basket.getBasketAmount()));
    }
    // metoda wprowadzająca dane z pliku baskets.csv do koszyka,
    // ale dotyczące tylko zalogowanego użytkownika
    private List<Basket> getUserBasket(String login){
        // 1. z listy koszyków odfiltruj tylko dane dot. usera zidentyfikowanego po loginie
        List<Basket> userBaskets = InMemoryDb.baskets.stream()
                .filter(basket -> basket.getUserLogin().equals(login))
                .sorted(Comparator.comparing(Basket::getStatus))
                .collect(Collectors.toList());
        return userBaskets;
    }
    // metoda do konfiguracji kolumn w tblBasket i wprowadzenia danych dot. koszyków użytkownika
    private void addDataToBasketsTable(){
        // konfiguracja wartości przekazywanych do kolumn z modelu
        tcBasket.setCellValueFactory(new PropertyValueFactory<>("order"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        // formatowanie zawartości tabeli
        tcBasket.setCellFactory(order -> new TableCell<Basket, Map>() {
            @Override
            protected void updateItem(Map basket, boolean empty) {
                super.updateItem(basket, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(basket.toString()
                            .replace("{", "")
                            .replace("}", "")
                            .replace("=", " x ")
                    );
                }
            }
        });
        tcStatus.setCellFactory(order -> new TableCell<Basket, Status>() {
            @Override
            protected void updateItem(Status status, boolean empty) {
                super.updateItem(status, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(status.getStatusName());
                }
            }
        });
        // wprowadzenie danych do tabeli z listy baskets
        tblBasket.setItems(FXCollections.observableArrayList(
                getUserBasket(LoginService.loggedUser.getLogin())
        ));
    }

    // -----------------------------------------------------------------------------------
    private PizzaList pizzaOfDay;

    public void initialize() {
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
        tcIngredients.setCellValueFactory(new PropertyValueFactory<PizzaList, String>("ingredients"));
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
        // TAB2 - BASKET --------------------------------------------------
        addDataToBasketsTable();
        // ----------------------------------------------------------------
    }

}
