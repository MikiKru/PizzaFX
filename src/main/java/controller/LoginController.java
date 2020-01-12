package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.LoginService;

// klasa obsługująca żądania użytkownika
// mapowanie żądań użytkownika na logikę aplikacji
public class LoginController {
    // obiekty globalne
    LoginService loginService;

    @FXML
    private TextField tfLogin;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private Label lblInfo;

    @FXML
    void loginAction(ActionEvent event) {

    }

    @FXML
    void registerAction(ActionEvent event) {

    }
    // metoda która zasowanie wykonana jako pierwsza
    // po wyświetlenu widoku loginView.fxml
    public void initialize(){
        // inicjalizacja logiki biznesowej
        loginService = new LoginService();
    }

}
