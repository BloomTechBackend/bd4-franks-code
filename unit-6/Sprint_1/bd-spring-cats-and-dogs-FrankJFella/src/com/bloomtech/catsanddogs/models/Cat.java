package com.bloomtech.catsanddogs.models;

// This POJO will be used to send data back/forth to the server
// Spring Boot will automatically convert to/from json and Java object

// Be sure:
//          1. Have a ctor (usually  default ctor)
//          2. Standard getters/setters for the member variables
//          3. Member variable name must match the json attribute names

public class Cat
{
    private final long id;
    private String name;

    public Cat(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getId() {
        return id;
    }

}
