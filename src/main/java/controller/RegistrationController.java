package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.RegistrationService;
import service.WindowService;

import java.io.IOException;

public class RegistrationController {
    // obiekty globalne - deklaracja!!!
    private WindowService windowService;
    private RegistrationService registrationService;

    @FXML
    private TextField tfLogin;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private PasswordField pfPasswordConfirmation;
    @FXML
    private Label lblEquation;
    @FXML
    private TextField lblResult;
    @FXML
    private Label lblInfo;

    @FXML
    void backAction(ActionEvent event) throws IOException {
        windowService.createWindow("loginView", "panel logowania");
        windowService.closeWindow(lblInfo);
    }

    @FXML
    void registerAction(ActionEvent event) {
        System.out.println("DANE REJESTRACJI");
        System.out.println(tfLogin.getText());
        System.out.println(pfPassword.getText());
        System.out.println(pfPasswordConfirmation.getText());
        System.out.println(lblResult.getText());
    }
    public void initialize(){
        // inicjalizacja zadeklarowanych obiektów
        windowService = new WindowService();
        registrationService = new RegistrationService();
    }

}
