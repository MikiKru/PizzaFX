package service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

// klasa do obsługi okien aplikacji
public class WindowService {
    // metoda do otwerania nowego okna aplikacji
    public void createWindow(String viewName, String title) throws IOException {
        Stage stage = new Stage();                                                              // utworzenie obiektu okna
        Parent root = FXMLLoader.load(getClass().getResource("/view/"+viewName+".fxml")); // załadowanie widoku fxml
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.setResizable(false);           // blokowanie zmiany rozmiaru okna
        stage.show();
    }
    // metoda do zamykania aktualnie otwatrtego okna aplikacji
    public void closeWindow(Node node){
        // Node klasa po której dziedziczą wszystkie konstolki
        Stage stageToClose = (Stage) node.getScene().getWindow();
        stageToClose.close();
    }
    // uniwersalna metoda do generowania okien alertu typów: Information, Warning, Error, None
    public static void getAlertWindow(Alert.AlertType alertType, String title, String header, String content){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
}
