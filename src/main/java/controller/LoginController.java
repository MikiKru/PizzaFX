package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import service.LoginService;

import java.util.Optional;

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
        Optional<User> userOpt = loginService.loginUser(tfLogin.getText(), pfPassword.getText());
        if(userOpt.isPresent()){
            lblInfo.setText("zalogowano");
        } else{
            lblInfo.setText("błąd logowania");
        }
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
