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

    // metoda "własna" - nie jest wywoływana z widoku FXML
    private void login(){
        Optional<User> userOpt = loginService.loginUser(tfLogin.getText(), pfPassword.getText());
        if(userOpt.isPresent() ){
            if(userOpt.get().isStatus()) {
                lblInfo.setText("zalogowano");
                loginService.clearLoginProbes(userOpt.get());
            } else {
                lblInfo.setText("Twoje konto jest zabolokowane");
            }
        } else{
            loginService.decrementProbes(tfLogin.getText());
            lblInfo.setText(loginService.getLoginProbes(tfLogin.getText()));
        }
    }

    @FXML
    void keyLoginAction(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            login();
        }
    }
    @FXML
    void loginAction(ActionEvent event) {
        login();
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
