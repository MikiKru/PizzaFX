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
        try {
            if (registrationService.isHuman(Integer.valueOf(lblResult.getText()), result)) {
                System.out.println(tfLogin.getText());
                System.out.println(pfPassword.getText());
                System.out.println(pfPasswordConfirmation.getText());
                lblInfo.setText("");
            } else {
                lblInfo.setText("jesteś robotem!");
                // wygenerowanie nowego równania i aktualizacja wyniku
                result = registrationService.generateRandomEquation(lblEquation);
                lblResult.clear();
            }
        } catch (Exception e){
            lblInfo.setText("błąd rejestracji");
            result = registrationService.generateRandomEquation(lblEquation);
            lblResult.clear();
        }
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
