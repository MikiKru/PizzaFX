package service;

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
}
