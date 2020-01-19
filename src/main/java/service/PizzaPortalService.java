package service;

import model.Ingredient;
import model.Pizza;
import model.PizzaList;
import utility.InMemoryDb;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PizzaPortalService {
    // metoda mapująca enumy Pizza na listę PizzaList
    public static void mapPizzaToPizzaList(){
        List<PizzaList> pizzas = Arrays.stream(Pizza.values())              // tablica wartości z enum Pizza []
                .map(pizza -> new PizzaList(
                        pizza.getName(),                                            // pobranie nazwy
                        pizza.getIngredients().stream()
                                .map(Ingredient::getName)
                                .collect(Collectors.joining(",")),          // zwrócenie składnikow sep=','
                        (pizza.getIngredients().stream()
                                .anyMatch(Ingredient::isSpicy) ? "ostra " : "") +
                        (pizza.getIngredients().stream()
                                .noneMatch(Ingredient::isMeat) ? "wege" : ""),      // zwrócenie opisu dla ostrej i vege
                        pizza.getIngredients().stream()
                                .mapToDouble(Ingredient::getPrice)
                                .sum(),                                             // obliczenie ceny składników
                        0
                ))
                .collect(Collectors.toList());
        InMemoryDb.pizzaLists.addAll(pizzas);
    }
}
