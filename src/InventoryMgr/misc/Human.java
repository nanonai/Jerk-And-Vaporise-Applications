package InventoryMgr.misc;

import java.util.*;
import java.util.Arrays;
import java.util.List;

public class Human {

    private int Gender, Age;
    private String FirstName, LastName, MiddleName;

    public Human() {
        List<String> firstNames = Arrays.asList(
                "Alice", "Benjamin", "Clara", "David", "Emma",
                "Felix", "Grace", "Henry", "Isla", "Jack",
                "Luna", "Mason", "Nora", "Oscar", "Penelope"
        );

        List<String> lastNames = Arrays.asList(
                "Anderson", "Brown", "Clark", "Davis", "Evans",
                "Foster", "Garcia", "Harris", "Iverson", "Johnson",
                "Kim", "Lopez", "Miller", "Nelson", "Owens"
        );

        List<String> middleNames = Arrays.asList(
                "James", "Marie", "Lee", "Grace", "Michael",
                "Anne", "Ray", "Elizabeth", "Alan", "Rose",
                "Joseph", "Lynn", "Thomas", "Renee", "Charles",
                "Nicole", "Daniel", "Jane", "Edward", "Faith",
                "Alexander", "Brooke", "William", "Hope", "Christopher",
                "Paige", "Andrew", "Claire", "Scott", "Kate"
        );

        Random random = new Random();

        FirstName = firstNames.get(random.nextInt(firstNames.size()));
        LastName = lastNames.get(random.nextInt(lastNames.size()));
        MiddleName = middleNames.get(random.nextInt(middleNames.size()));

        Gender = random.nextInt(2);
        Age = random.nextInt(1, 101);
    }

    public String getGender() {
        if (Gender == 1) {
            return "Male";
        } else {
            return "Female";
        }
    }

    @Override
    public String toString(){
        return "[Human] " + FirstName + " \"" + MiddleName + "\" " + LastName + ", " + getGender() + ", " + Age + " years old";
    }
}
