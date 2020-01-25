package service;

import model.*;
import utility.InMemoryDb;

import java.io.*;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

// logika biznesowa do obsługi plików
public class FileService {
    // ścieżka bazpośrednia do pliku users.csv
    // Paths.get("").toAbsolutePath().toString() - generowanie ścieżki bezpośredniej do katalogu
    // głównego projektu -> PizzaPortal/
    private static String pathToUsers =
            Paths.get("").toAbsolutePath().toString()+ "\\src\\main\\resources\\file\\users.csv";
    private static String pathToBaskets =
            Paths.get("").toAbsolutePath().toString()+ "\\src\\main\\resources\\file\\baskets.csv";

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
    public static void updateUsers() throws IOException {
        FileWriter fileWriter = new FileWriter(new File(pathToUsers));
        for (User u : InMemoryDb.users) {
            fileWriter.write(String.format("%s; %s; %s; %s; %s; %d\n",
                    u.getLogin(),
                    u.getPassword(),
                    u.getRoles().stream().map(Enum::name).collect(Collectors.joining(",")),
                    u.getRegistrationDateTime().format(dtf),
                    u.isStatus(),
                    u.getProbes())
            );
        }
        fileWriter.close();
    }
    // metoda aktualizująca zawartość koszyka w oparciu o plik baskets.csv
    public static void updateBasket() throws IOException {
        FileWriter fileWriter = new FileWriter(new File(pathToBaskets));    // obiekt do zapisu do pliku o adresie
                                                                            // URL jak w pathToBaskets
        Locale locale = Locale.ENGLISH;
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale); // wymuszenie kropki
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        for (Basket basket : InMemoryDb.baskets){
            fileWriter.write(
                    String.format("%s; %s; %s; %s\n",
                            basket.getUserLogin(),
                            basket.getOrder().toString()
                                    .replace("{", "")
                                    .replace("}",""),   // usunięcie nawiasów {}
                            numberFormat.format(basket.getBasketAmount()),
                            basket.getStatus().getStatusName()
                            ));      // przepisanie zamówień z listy baskets do pliku baskets.csv
        }
        fileWriter.close();
    }
    // metoda pobierająca zawartość z pliku i wpisująca do listy baskets
    public static void selectBaskets() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(pathToBaskets));
        // odczyt zawartości linijka po linijce z pliku basktes.csv
        while (scanner.hasNext()){
            String [] line = scanner.nextLine().split("; ");       // każda linijka pliku jest dzielona po "; "
                                                                        // co daje nam tablicę String[4]
            Map<String, Integer> order = new HashMap<>();               // mapa zamówień
            for (String element : line[1].split(", ")){            // każde zamówienie w mapie jest dzielone po ", "
                String[] key_value = element.split("=");           // pojedyncze zamówienie to key=value
                order.put(key_value[0], Integer.valueOf(key_value[1])); // dodanie zamówienia do mapy
            }
            // w pliku "do realizacji"
            // Enum -> NEW("nowe zamówienie")
            InMemoryDb.baskets.add(
                    new Basket(
                            line[0],
                            order,
                            Double.valueOf(line[2]),
                            Arrays.stream(Status.values())                                      // lista statusów
                                    .filter(status -> status.getStatusName().equals(line[3]))   // filtrujemy po nazwie
                                    .findAny().get()                                            // bierzemy pierwszy obiekt Statu
                    )
            );
        }
        scanner.close();
    }

}
