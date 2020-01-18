package service;

import javafx.scene.control.Label;

import java.util.Random;

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
}
