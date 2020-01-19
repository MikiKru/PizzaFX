package utility;

import model.PizzaList;
import model.Role;
import model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class InMemoryDb {
    public static List<User> users = new ArrayList<>();
    public static List<PizzaList> pizzaLists = new ArrayList<>(
            Arrays.asList(new PizzaList(
                    "Test", "Test1,Test2,Test3","nijaka",15.,0))
    );
}
