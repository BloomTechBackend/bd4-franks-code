package com.bloomtech.catsanddogs.models;

public class Dog
{
    private final long id;
    private String name;

    public Dog(long id, String name) {
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
