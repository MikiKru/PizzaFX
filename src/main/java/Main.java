import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.FileService;
import utility.InMemoryDb;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/loginView.fxml"));
        primaryStage.setTitle("panel logowania");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);           // blokowanie zmiany rozmiaru okna
        primaryStage.show();
        // aktualizacja listy users
        FileService.selectUsers();                              // pobranie zawartości pliku i dodanie do listy
        InMemoryDb.users.forEach(System.out::println); // wypisanie listy users
    }
    public static void main(String[] args) {

        launch(args);
    }
}
