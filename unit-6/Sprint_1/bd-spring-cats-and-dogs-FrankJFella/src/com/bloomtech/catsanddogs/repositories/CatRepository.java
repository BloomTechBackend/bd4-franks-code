package com.bloomtech.catsanddogs.repositories;

import com.bloomtech.catsanddogs.models.Cat;

import java.util.List;

public class CatRepository
{
    public List<Cat> getCats() {
        return List.of(new Cat(1, "Tiger"));
    }
}
