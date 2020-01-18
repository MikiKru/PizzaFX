package service;

import model.Role;
import model.User;
import utility.InMemoryDb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Collectors;

// logika biznesowa do obsługi plików
public class FileService {
    private static String pathToUsers = "C:\\Users\\PROXIMO\\Desktop\\TARR1\\PizzaPortal\\src\\main\\resources\\file\\users.csv";
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    // metoda pobierająca zawartość z pliku i wprawadzająca ją do listy users
    public static void selectUsers() throws FileNotFoundException {
        File file = new File(pathToUsers);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()){
            String user = scanner.nextLine();   // pobrana jedna linijka z pliku
            String [] userSplitBySeparator = user.split("; ");
            // wpisanie zawartości pobranej z pliku do listy podręcznej users
            InMemoryDb.users.add(new User(
                    userSplitBySeparator[0],                                        // login
                    userSplitBySeparator[1],                                        // password
                    new HashSet<>(                                                  // konwersja do Set<Role>
                            Arrays.stream(userSplitBySeparator[2].split(","))
                                    .map(Role::valueOf)
                                    .collect(Collectors.toSet())),
                    LocalDateTime.parse(userSplitBySeparator[3], dtf),              // parsowanie do LocalDateTime
                    Boolean.valueOf(userSplitBySeparator[4]),                       // konwersja do boolean
                    Integer.valueOf(userSplitBySeparator[5]))                       // konwersja do Integer
            );
        }


    }
    // metoda zapisująca zawartość z listy users do pliku
    public void insertUsers(){

    }
}
