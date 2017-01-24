package com.springdemo.contracts;

import java.util.List;

public class GreetingQuery {
    private String name;

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    private List titles;
    public void setTitles(List titles)
    {
        this.titles = titles;
    }

    public List getTitles()
    {
        return this.titles;
    }
}
