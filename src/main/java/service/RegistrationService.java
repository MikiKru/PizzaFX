package service;

import javafx.scene.control.Label;
import utility.InMemoryDb;

import java.util.Random;
import java.util.regex.Pattern;

// logika biznesowa rejestracji
public class RegistrationService {
    // metoda do generowania sprawdzenia czy jesteś robotem
    public int generateRandomEquation(Label lblEquation){
        Random random = new Random();
        int x1 = random.nextInt(10);
        int x2 = random.nextInt(10);
        lblEquation.setText(x1 + " + " + x2 + " = ");
        return x1 + x2;
    }
    public boolean isHuman(int userResult, int generatedResult){
        return userResult == generatedResult;
    }
    public boolean isLoginCorrect(String login){
        return login.length() >= 6;
    }
    public boolean isLoginUnique(String login){
        return InMemoryDb.users.stream().noneMatch(user -> user.getLogin().equals(login));
    }
    public boolean isPasswordCorrect(String password){
        return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[_!.,#-])[^\\s\\t]{4,}$");
    }
    public boolean isPasswordConfirmed(String password, String confirmedPassword){
        return password.equals(confirmedPassword);
    }
}
