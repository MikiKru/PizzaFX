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
    private int result;

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

    }
    public void initialize(){
        // inicjalizacja zadeklarowanych obiektów
        windowService = new WindowService();
        registrationService = new RegistrationService();
        // generowanie randomowego równania do sprawdzenia czy jestem robotem
        // 1. wypisanie równania na lblEquation
        // 2. zwrócenie oczekiwanej wartości do pola result
        result = registrationService.generateRandomEquation(lblEquation);
    }

}
