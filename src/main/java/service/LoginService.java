package service;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import utility.InMemoryDb;

import java.util.Optional;

// klasa implementująca logikę biznesową aplikacji
public class LoginService {
    // metoda do logowania użytkownika
    public Optional<User> loginUser(String login, String password){
        return InMemoryDb.users.stream()                                                                // stream
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))   // logowanie
                .findAny();                                                                             // Optional
    }
    // metoda do blokowania statusu użytkownika
    public void decrementProbes(String login){
        // wyszukanie użytkownika po loginie
        Optional<User> userOpt = InMemoryDb.users.stream().filter(user -> user.getLogin().equals(login)).findAny();
        if(userOpt.isPresent()){
            userOpt.get().setProbes(userOpt.get().getProbes() - 1); // zmniejszam o 1 ilość prób logowania
        }
        if(userOpt.isPresent()) {
            if (userOpt.get().getProbes() == 0) {
                System.out.println("ZABLOKOWANO KONTO");
                userOpt.get().setStatus(false);
            }
        }
    }
    // metoda restartująca ilość prób logowania
    public void clearLoginProbes(User user){
        user.setProbes(3);
    }
    // metoda zwracająca pozostałą ilość prób logowania
    // lub nic w przypadku podanego login który nie istnieje
    // w users
    public String getLoginProbes(String login){
        Optional<User> userOpt = InMemoryDb.users.stream().filter(user -> user.getLogin().equals(login)).findAny();
        if(userOpt.isPresent()){
            if(userOpt.get().getProbes() > 0) {
                return "pozostało: " + userOpt.get().getProbes() + " prób logowania";
            } else {
                return "Twoje konto jest zablokowane";
            }
        }
        return "błędny login";
    }

    public void login(TextField tfLogin, PasswordField pfPassword, Label lblInfo){
        Optional<User> userOpt = loginUser(tfLogin.getText(), pfPassword.getText());
        if(userOpt.isPresent() ){
            if(userOpt.get().isStatus()) {
                lblInfo.setText("zalogowano");
                clearLoginProbes(userOpt.get());
            } else {
                lblInfo.setText("Twoje konto jest zabolokowane");
            }
        } else{
            decrementProbes(tfLogin.getText());
            lblInfo.setText(getLoginProbes(tfLogin.getText()));
        }
    }
}
