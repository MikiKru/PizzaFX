package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor     // konstruktor z wszystkimi polami w argumentach
@Data                   // getters & setters & toString
public class PizzaList {
    private String name;
    private String ingredients;
    private String description;
    private Double price;
    private Integer quantity;
}
