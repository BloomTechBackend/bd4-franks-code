package com.bloomtech.catsanddogs.repositories;

import com.bloomtech.catsanddogs.models.Dog;

import java.util.List;

public class DogRepository
{
    public List<Dog> getDogs() {
        return List.of(
                new Dog(1,"Dottie"),
                new Dog(2,"Ginger"),
                new Dog(3,"Odie")
        );
    }
}
