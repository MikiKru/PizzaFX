package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.User;
import service.LoginService;
import service.WindowService;

import java.io.IOException;
import java.util.Optional;

// klasa obsługująca żądania użytkownika
// mapowanie żądań użytkownika na logikę aplikacji
public class LoginController {
    // obiekty globalne
    LoginService loginService;
    WindowService windowService;
    @FXML
    private TextField tfLogin;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private Label lblInfo;

    @FXML
    void keyLoginAction(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            loginService.login(tfLogin, pfPassword, lblInfo);
        }
    }
    @FXML
    void loginAction(ActionEvent event) {
        loginService.login(tfLogin, pfPassword, lblInfo);
    }

    @FXML
    void registerAction(ActionEvent event) throws IOException {
        // otwarcie nowego okna rejestracji
        windowService.createWindow("registrationView", "panel rejestracji");
        // zamknięcie okna logowania
        windowService.closeWindow(lblInfo); // podajemy dowolną kontrolkę znajdującą się na oknie aplikacji w argumencie
    }
    // metoda która zasowanie wykonana jako pierwsza
    // po wyświetlenu widoku loginView.fxml
    public void initialize(){
        // inicjalizacja logiki biznesowej
        loginService = new LoginService();
        windowService = new WindowService();
    }
}
